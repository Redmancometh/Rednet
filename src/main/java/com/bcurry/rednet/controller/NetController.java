package com.bcurry.rednet.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bcurry.rednet.neuralnet.gui.NNVisualizer;

@Controller
public class NetController {
	@Autowired
	private NNVisualizer visualizer;

	@PostConstruct
	public void show() {
		visualizer.open();
	}
}
