package com.bcurry.rednet.neuralnet.simpleimpl;

import org.springframework.context.annotation.Profile;

import com.bcurry.rednet.neuralnet.abstraction.AbsNeuronConnection;
import com.bcurry.rednet.neuralnet.abstraction.Neuron;

@Profile("simple")
public class SimpleNeuronConnection extends AbsNeuronConnection {

	public SimpleNeuronConnection(Neuron from, Neuron to, Long weight) {
		super(from, to, weight);
	}

	public SimpleNeuronConnection(Neuron from, Neuron to) {
		super(from, to, Math.random());
	}

}
