package com.bcurry.rednet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

import com.bcurry.rednet.config.ConfigManager;
import com.bcurry.rednet.config.pojo.SpringConfig;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class })
@ComponentScan(basePackages = { "com.bcurry.rednet.*" })
@EntityScan(basePackages = "com.bcurry.rednet.model")
@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
public class NeuralServer {
	private static ConfigManager<SpringConfig> cfgMon = new ConfigManager("spring.json", SpringConfig.class);
	public static ScheduledExecutorService main = Executors.newSingleThreadScheduledExecutor();
	public static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		System.out.println("I excluded the autoconfiguration");
		main.execute(() -> {
			// changed some code
			cfgMon.init();
			SpringConfig cfg = cfgMon.getConfig();
			System.setProperty("webdriver.chrome.driver", cfg.getGeckoExecutableLocation());
			SpringApplication application = new SpringApplicationBuilder().headless(false).sources(NeuralServer.class)
					.build();
			application.setDefaultProperties(cfg.getProperties());
			ConfigurableEnvironment environment = new StandardEnvironment();
			System.out.println("Profiles size: " + cfg.getProfiles().size());
			cfg.getProfiles().forEach((profile) -> System.out.println("Profile: " + profile));
			environment.setActiveProfiles(cfg.getProfiles().toArray(new String[cfg.getProfiles().size()]));
			application.setEnvironment(environment);
			context = application.run();
		});
	}
}
