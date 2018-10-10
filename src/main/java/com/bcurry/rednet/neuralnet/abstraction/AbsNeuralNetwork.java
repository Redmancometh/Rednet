package com.bcurry.rednet.neuralnet.abstraction;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcurry.rednet.mediator.DataManager;
import com.bcurry.rednet.neuralnet.factory.NeuronLayerFactory;

import lombok.Data;
import lombok.Getter;

@Data
public abstract class AbsNeuralNetwork implements NeuralNetwork {
	@Autowired
	@Getter
	protected NeuronLayerFactory layerFactory;
	@Autowired
	private DataManager data;
	/**
	 * 
	 * Neural network input layer
	 * 
	 */
	protected InputLayer inputLayer;
	/**
	 * 
	 * Neural network hidden layers
	 * 
	 */
	protected Map<UUID, NeuronLayer> hiddenLayers = new ConcurrentHashMap();

	protected List<NeuronLayer> orderedLayers = new CopyOnWriteArrayList();
	/**
	 * 
	 * Neural network output layer
	 * 
	 */
	protected OutputLayer outputLayer;

	public List<NeuronLayer> orderedLayers() {
		return orderedLayers;
	}

	public OutputLayer outputLayer() {
		return outputLayer;
	}

	public InputLayer inputLayer() {
		return inputLayer;
	}

	@Override
	public Map<UUID, NeuronLayer> hiddenLayers() {
		return hiddenLayers;
	}

	@Override
	public abstract void populate(int layers, int neuronsPerLayer);

	@Override
	public void makeConnections() {
		NeuronLayer layerOne = hiddenLayers.get(orderedLayers.get(0).getId());
		/*
		 * layerOne.getNeurons() .forEach((neuron) -> System.out.println("Connecting " +
		 * neuron.getId() + " to input neuron"));
		 */
		inputLayer.connectTo(layerOne);
		for (int x = 0; x < orderedLayers.size(); x++) {
			NeuronLayer hiddenLayerFirst = hiddenLayers.get(orderedLayers.get(x).getId());
			if (x == (orderedLayers.size() - 1)) {
				hiddenLayerFirst.connectTo(outputLayer);
				return;
			}
			NeuronLayer hiddenLayerNext = hiddenLayers.get(orderedLayers.get(x + 1).getId());
			hiddenLayerFirst.connectTo(hiddenLayerNext);
		}

	}

	@Override
	public void populateIOLayers(int connectionsPerLayer) {
		this.inputLayer = layerFactory.constructInput();
		this.outputLayer = layerFactory.constructOutput();
	}

}
