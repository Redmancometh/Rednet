package com.bcurry.rednet.neuralnet.abstraction;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface NeuralNetwork {

	void populate(int layers, int connectionsPerLayer);

	void populateIOLayers(int connectionsPerLayer);

	NeuronLayer inputLayer();

	NeuronLayer outputLayer();

	Map<UUID, NeuronLayer> hiddenLayers();

	void makeConnections();

	void takeInput(double input, double target);

	public List<NeuronLayer> orderedLayers();

	void backPropogate(double target);

}
