package com.bcurry.rednet.neuralnet.factory;

import org.springframework.context.annotation.Profile;

import com.bcurry.rednet.neuralnet.abstraction.NeuralNetwork;
import com.bcurry.rednet.neuralnet.simpleimpl.SimpleNeuralNet;

@Profile("simple")
public class SimpleNeuralNetfactory extends AbsNeuralNetFactory {

	@Override
	public NeuralNetwork buildNet(int layers, int neuronsPerLayer) {
		SimpleNeuralNet net = new SimpleNeuralNet();
		populateNet(net, layers, neuronsPerLayer);
		return net;
	}

}
