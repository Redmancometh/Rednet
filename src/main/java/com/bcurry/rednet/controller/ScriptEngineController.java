package com.bcurry.rednet.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.bcurry.rednet.config.DirectoryWatcher;
import com.bcurry.rednet.model.Person;
import com.bcurry.rednet.util.ExportSystem;
import com.bcurry.rednet.util.QuadFunction;
import com.bcurry.rednet.util.SpecialFuture;
import com.bcurry.rednet.util.TriFunction;

import jdk.dynalink.beans.StaticClass;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

@Controller
/**
 * This is the automated watched scripting system. There will be an entirely
 * distinct WebScript system for loading by authenticated URL.
 * 
 * @author Brendan T Curry
 *
 */
public class ScriptEngineController {
	private ScheduledExecutorService scriptScheduler = Executors.newScheduledThreadPool(4);
	private ScheduledExecutorService asyncPool = Executors.newScheduledThreadPool(8);
	ScriptEngine nashhorn = new NashornScriptEngineFactory().getScriptEngine("--language=es6");
	@Autowired
	protected ApplicationContext context;
	@Autowired
	private AutowireCapableBeanFactory factory;
	private Consumer<File> onChange = (file) -> {
		try {
			System.out.println("FILE CHANGED: " + file);
			runScript(file.getName(), new HashMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	private TriFunction<Runnable, Integer, TimeUnit, ScheduledFuture> schedule = (runnable, time, units) -> {
		return scriptScheduler.schedule(runnable, time, units);
	};

	private QuadFunction<Runnable, Integer, Integer, TimeUnit, ScheduledFuture> timer = (runnable, initialDelay,
			repeatDelay, units) -> {
		return scriptScheduler.scheduleAtFixedRate(runnable, initialDelay, repeatDelay, units);
	};

	private DirectoryWatcher dirWatcher = new DirectoryWatcher(onChange, "scripts", "js");
	private ExportSystem exports = new ExportSystem();

	public AtomicInteger newAtomicInt() {
		return new AtomicInteger();
	}

	@PostConstruct
	public void addGlobals() {
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.javascript.StrictErrorReporter")
				.setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.javascript.host.WindowProxy")
				.setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.javascript.host.ActiveXObject")
				.setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDocument")
				.setLevel(Level.OFF);
		nashhorn.put("HashMap", StaticClass.forClass(HashMap.class));
		nashhorn.put("HashSet", StaticClass.forClass(HashSet.class));
		nashhorn.put("async", asyncPool);
		nashhorn.put("Instant", Instant.class);
		nashhorn.put("Throwable", StaticClass.forClass(Throwable.class));
		nashhorn.put("Date", Date.class);
		nashhorn.put("SpecialFuture", SpecialFuture.class);
		nashhorn.put("newAtomicInt", (Supplier) this::newAtomicInt);
		nashhorn.put("AList", StaticClass.forClass(java.util.ArrayList.class));
		nashhorn.put("Person", StaticClass.forClass(Person.class));
		nashhorn.put("Instant", StaticClass.forClass(Instant.class));
		nashhorn.put("Runnable", StaticClass.forClass(Runnable.class));
		nashhorn.put("Date", StaticClass.forClass(Date.class));
		nashhorn.put("dateFormat", new SimpleDateFormat("MM/dd/yy"));
		nashhorn.put("beans", factory);
		nashhorn.put("TimeUnit", StaticClass.forClass(TimeUnit.class));
		nashhorn.put("ExpectedConditions", StaticClass.forClass(ExpectedConditions.class));
		nashhorn.put("By", StaticClass.forClass(By.class));
		nashhorn.put("WebDriverWait", StaticClass.forClass(WebDriverWait.class));
		nashhorn.put("wireByClass", (Function<Class, Object>) context::getBean);
		nashhorn.put("wireByName", (Function<String, Object>) context::getBean);
		nashhorn.put("log", (Consumer<Object>) this::logInfo);
		nashhorn.put("error", (Consumer<Object>) this::logError);
		nashhorn.put("error", (BiConsumer<Object, Throwable>) this::logError);
		nashhorn.put("list", (Supplier<List>) this::list);
		nashhorn.put("map", (Supplier<Map>) this::map);
		nashhorn.put("runTimer", timer);
		nashhorn.put("schedule", schedule);
		nashhorn.put("context", context);
		nashhorn.put("exports", exports);
		dirWatcher.start();
	}

	public ArrayList list() {
		return new ArrayList();
	}

	public Map map() {
		return new ConcurrentHashMap();
	}

	public void logInfo(Object message) {
		Logger.getLogger("scripting").info("\n" + message.toString());
	}

	public void logError(Object message) {
		Logger.getLogger("scripting").severe("\n" + message.toString());
	}

	public void logError(Object message, Throwable t) {
		Logger.getLogger("scripting").severe("\n" + message.toString());
		t.printStackTrace();
	}

	/**
	 * This will run a script in the
	 * 
	 * @param scriptName
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String runScript(String scriptName, Map args) {
		// Don't use absolute paths so we prevent any kind of RFI attacks
		File scriptDir = new File("scripts" + File.separator + scriptName);
		System.out.println("Script dir: " + scriptDir + " exists: " + scriptDir.exists());
		if (!scriptDir.exists())
			return "File does not exist in scripts directory. Check for the script, and try again\n\tPath Tried: "
					+ scriptDir.getAbsolutePath();
		try {
			System.out.println("EVAL");
			nashhorn.eval(FileUtils.readFileToString(scriptDir));
			System.out.println("UNDER RESULT");
			System.out.println("SUCCESSFULLY RAN");
			return "Successfully ran script: " + scriptName;
		} catch (ScriptException | IOException e) {
			System.out.println("MISC EXCEPTION");
			e.printStackTrace();
			return e.toString();
		}
	}

	/**
	 * Might need this might not. For now using it as a filler for a preprocessor.
	 * 
	 * @param output
	 * @return
	 */
	public String embedInPage(String output) {
		return output;
	}
}
