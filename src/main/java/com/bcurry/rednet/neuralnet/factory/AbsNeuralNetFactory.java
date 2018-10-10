package com.bcurry.rednet.neuralnet.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import com.bcurry.rednet.neuralnet.abstraction.NeuralNetwork;

public abstract class AbsNeuralNetFactory implements NeuralNetFactory {
	@Autowired
	private AutowireCapableBeanFactory factory;

	protected void populateNet(NeuralNetwork network, int layers, int neuronsPerLayer) {
		factory.autowireBean(network);
		network.populate(layers, neuronsPerLayer);
	}

}
