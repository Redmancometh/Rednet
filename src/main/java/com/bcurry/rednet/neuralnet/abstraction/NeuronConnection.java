package com.bcurry.rednet.neuralnet.abstraction;

import java.util.Optional;
import java.util.UUID;

public interface NeuronConnection {
	Neuron getFrom();

	Neuron getTo();

	double getWeight();

	void setWeight(double weight);

	UUID getId();

	/**
	 * Shortcut method to get the output from the "from" neuron
	 * 
	 * @return
	 */
	Optional<Double> calculateOutput();

	/**
	 * 
	 * @return
	 */
	Optional<Double> getLastOutputUnweighted();

}
