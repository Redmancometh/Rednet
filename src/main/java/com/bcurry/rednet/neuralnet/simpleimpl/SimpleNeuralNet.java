package com.bcurry.rednet.neuralnet.simpleimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import com.bcurry.rednet.mediator.Eventbus;
import com.bcurry.rednet.mediator.events.ForwardPropogationCompleteEvent;
import com.bcurry.rednet.neuralnet.abstraction.AbsNeuralNetwork;
import com.bcurry.rednet.neuralnet.abstraction.Neuron;
import com.bcurry.rednet.neuralnet.abstraction.NeuronLayer;

@Profile("simple")
public class SimpleNeuralNet extends AbsNeuralNetwork {

	@Autowired
	private Eventbus events;

	@Override
	public void populate(int layers, int neuronsPerLayer) {
		populateIOLayers(neuronsPerLayer);
		System.out.println("Populating with " + layers + " layers with " + neuronsPerLayer + " neurons per layer");
		for (int x = 0; x < layers; x++) {
			NeuronLayer layer = layerFactory.constructLayer(neuronsPerLayer);
			System.out.println("Layer created " + x + " " + layer.getId());
			getHiddenLayers().put(layer.getId(), layer);
			getOrderedLayers().add(layer.getId());
			getData().addLayer(layer);
		}
		makeConnections();
		events.<ForwardPropogationCompleteEvent>subscribe(ForwardPropogationCompleteEvent.class, (e) -> {
			if (e.getFrom().getId().equals(this.outputLayer.getId())) {

			}
		});
		System.out.println("ID for hidden layer: " + getInputLayer().getId());
	}

	@Override
	public void takeInput(double input, double target) {
		System.out.println("Taking in input: " + input);
		inputLayer.passInput(input);
		System.out.println("Passed input to input layer, running hidden layers");
		double output = outputLayer.propogateTo(target);
		System.out.println("Final output is: " + output);
		System.out.println("Target is: " + target);
		System.out.println("Total error is " + outputLayer.calculateTotalError());
	}

	@SuppressWarnings("unused")
	@Override
	public void backPropogate(ForwardPropogationCompleteEvent event) {
		double learningWeight = .5;
		event.getResults().forEach((id, neuronResult) -> {
			Neuron neuron = neuronResult.getNeuronUUID();
			double target = neuronResult.getTarget();
			double outO = neuronResult.getResult();
			double outNetDiv = outO * (1 - outO);
			neuron.getConnectionsOut().values().forEach((connection) -> {
				double weight = connection.getWeight();
				connection.getLastOutputUnweighted();
			});
		});
	}

}
