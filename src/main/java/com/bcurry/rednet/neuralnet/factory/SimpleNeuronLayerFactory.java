package com.bcurry.rednet.neuralnet.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Profile;

import com.bcurry.rednet.neuralnet.abstraction.InputLayer;
import com.bcurry.rednet.neuralnet.abstraction.NeuronLayer;
import com.bcurry.rednet.neuralnet.abstraction.OutputLayer;
import com.bcurry.rednet.neuralnet.simpleimpl.SimpleInputLayer;
import com.bcurry.rednet.neuralnet.simpleimpl.SimpleNeuronLayer;
import com.bcurry.rednet.neuralnet.simpleimpl.SimpleOutputLayer;

@Profile("simple")
public class SimpleNeuronLayerFactory implements NeuronLayerFactory {
	@Autowired
	private AutowireCapableBeanFactory factory;

	@Override
	public NeuronLayer constructLayer(int connections) {
		SimpleNeuronLayer layer = new SimpleNeuronLayer();
		factory.autowireBean(layer);
		layer.populateLayer(connections);
		return layer;

	}

	@Override
	public OutputLayer constructOutput() {
		SimpleOutputLayer layer = new SimpleOutputLayer();
		factory.autowireBean(layer);
		layer.populateLayer(2);
		return layer;
	}

	@Override
	public InputLayer constructInput() {
		SimpleInputLayer layer = new SimpleInputLayer();
		factory.autowireBean(layer);
		layer.populateLayer(2);
		return layer;
	}

}
