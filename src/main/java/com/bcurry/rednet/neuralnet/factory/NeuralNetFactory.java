package com.bcurry.rednet.neuralnet.factory;

import com.bcurry.rednet.neuralnet.abstraction.NeuralNetwork;

public interface NeuralNetFactory {
	NeuralNetwork buildNet(int layers, int neuronsPerLayer);
}
