package com.bcurry.rednet.neuralnet.factory;

import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Profile;

import com.bcurry.rednet.neuralnet.abstraction.InputNeuron;
import com.bcurry.rednet.neuralnet.abstraction.Neuron;
import com.bcurry.rednet.neuralnet.abstraction.NeuronLayer;
import com.bcurry.rednet.neuralnet.simpleimpl.SimpleInputNeuron;
import com.bcurry.rednet.neuralnet.simpleimpl.SimpleNeuron;

@Profile("simple")
public class SimpleNeuronFactory extends AbsNeuronFactory {
	@Autowired
	private AutowireCapableBeanFactory factory;

	@Override
	/**
	 * Create a Neuron with a random id via UUID.randomUUID()
	 */
	public Neuron constructNeuronFor(NeuronLayer layer, Consumer<Double> outputCallback) {
		Neuron n = new SimpleNeuron(UUID.randomUUID(), outputCallback);
		factory.autowireBean(n);
		return n;
	}

	public InputNeuron constructInputNeuronFor(NeuronLayer layer, Consumer<Double> outputCallback) {
		InputNeuron n = new SimpleInputNeuron(UUID.randomUUID(), outputCallback);
		factory.autowireBean(n);
		return n;
	}

}
