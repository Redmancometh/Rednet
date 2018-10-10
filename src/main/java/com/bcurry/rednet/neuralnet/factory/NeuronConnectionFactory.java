package com.bcurry.rednet.neuralnet.factory;

import com.bcurry.rednet.neuralnet.abstraction.Neuron;
import com.bcurry.rednet.neuralnet.abstraction.NeuronConnection;

public interface NeuronConnectionFactory {
	/**
	 * Construct a NeuronConnection from the 2 given neurons and the given weight
	 * 
	 * @param from
	 * @param to
	 * @param weight
	 * @return
	 */
	NeuronConnection makeConnection(Neuron from, Neuron to, Long weight);

	/**
	 * Construct a NeuronConnection from the 2 given neurons
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	NeuronConnection makeConnection(Neuron from, Neuron to);

}
