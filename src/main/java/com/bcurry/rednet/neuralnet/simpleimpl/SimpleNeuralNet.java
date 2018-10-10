package com.bcurry.rednet.neuralnet.simpleimpl;

import org.springframework.context.annotation.Profile;

import com.bcurry.rednet.neuralnet.abstraction.AbsNeuralNetwork;
import com.bcurry.rednet.neuralnet.abstraction.NeuronLayer;

@Profile("simple")
public class SimpleNeuralNet extends AbsNeuralNetwork {

	@Override
	public void populate(int layers, int neuronsPerLayer) {
		populateIOLayers(neuronsPerLayer);
		// System.out.println("Populating with " + layers + " layers with " +
		// neuronsPerLayer + " neurons per layer");
		for (int x = 0; x < layers; x++) {
			NeuronLayer layer = layerFactory.constructLayer(neuronsPerLayer);
			// System.out.println("Layer created " + x + " " + layer.getId());
			getHiddenLayers().put(layer.getId(), layer);
			getOrderedLayers().add(layer);
			getData().addLayer(layer);
		}
		makeConnections();
		// System.out.println("ID for hidden layer: " + getInputLayer().getId());
	}

	@Override
	public void takeInput(double input, double target) {
		// System.out.println("Taking in input: " + input);
		inputLayer.passInput(input);
		// System.out.println("Passed input to input layer, running hidden layers");
		outputLayer.propogateTo(target);
		backPropogate(target);

		// System.out.println("Final output is: " + output);
		// System.out.println("Target is: " + target);
	}

	@Override
	public void backPropogate(double target) {
		/*
		 * double learningWeight = .5; event.getResults().forEach((id, neuronResult) ->
		 * { Neuron neuron = neuronResult.getNeuronUUID(); double target =
		 * neuronResult.getTarget(); double outO = neuronResult.getResult(); double
		 * outNetDiv = outO * (1 - outO);
		 * neuron.getConnectionsOut().values().forEach((connection) -> { double weight =
		 * connection.getWeight(); connection.getLastOutputUnweighted(); }); });
		 */
		outputLayer.backPropogate(target);
		for (int x = (orderedLayers.size() - 1); x > 0; x--) {
			NeuronLayer layer = orderedLayers.get(x);
			layer.backPropogate(target);
		}
		inputLayer.backPropogate(target);
	}

}
