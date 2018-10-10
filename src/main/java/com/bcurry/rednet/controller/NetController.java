package com.bcurry.rednet.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bcurry.rednet.config.pojo.TrainingConfig;
import com.bcurry.rednet.neuralnet.abstraction.NeuralNetwork;

@Controller
public class NetController {
	@Autowired
	private NeuralNetwork network;
	@Autowired
	private TrainingConfig training;

	@Autowired
	@PostConstruct
	public void show() {
		// visualizer.open();
		training.getTrainingSets().forEach((set) -> {
			for (int x = 0; x < set.getIterations(); x++) {
				set.getInputOutput().forEach((input, target) -> {
					network.takeInput(input, target);
				});
			}
		});
	}
}
