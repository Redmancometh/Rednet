package com.bcurry.rednet.pojo;

import com.bcurry.rednet.neuralnet.abstraction.Neuron;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NeuronResult {
	private Neuron neuronUUID;
	/**
	 * Target probably isn't needed here but oh well just in case
	 */
	private double result, error, target;
}
