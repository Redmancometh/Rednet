package com.bcurry.rednet.neuralnet.simpleimpl;

import java.util.UUID;
import java.util.function.Consumer;

import com.bcurry.rednet.neuralnet.abstraction.AbsInputNeuron;

public class SimpleInputNeuron extends AbsInputNeuron {

	public SimpleInputNeuron(UUID uuid, Consumer<Double> inputAcceptor) {
		super(uuid, inputAcceptor);
		System.out.println("MADE A SIMPLE INPUT NEURON");
	}

}
