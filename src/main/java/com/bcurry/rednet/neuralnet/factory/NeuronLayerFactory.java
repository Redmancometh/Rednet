package com.bcurry.rednet.neuralnet.factory;

import com.bcurry.rednet.neuralnet.abstraction.InputLayer;
import com.bcurry.rednet.neuralnet.abstraction.NeuronLayer;
import com.bcurry.rednet.neuralnet.abstraction.OutputLayer;

public interface NeuronLayerFactory {
	/**
	 * Construct a NeuronLayer with the given amount of connections
	 * 
	 * @param connections How many connections the layer should have
	 * @return
	 */
	public NeuronLayer constructLayer(int connections);

	public OutputLayer constructOutput();

	public InputLayer constructInput();
}
