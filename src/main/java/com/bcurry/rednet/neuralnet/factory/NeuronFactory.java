package com.bcurry.rednet.neuralnet.factory;

import java.util.function.Consumer;

import com.bcurry.rednet.neuralnet.abstraction.InputNeuron;
import com.bcurry.rednet.neuralnet.abstraction.Neuron;
import com.bcurry.rednet.neuralnet.abstraction.NeuronLayer;

public interface NeuronFactory {
	Neuron constructNeuronFor(NeuronLayer layer, Consumer<Double> outputCallback);

	InputNeuron constructInputNeuronFor(NeuronLayer layer, Consumer<Double> outputCallback);
}
