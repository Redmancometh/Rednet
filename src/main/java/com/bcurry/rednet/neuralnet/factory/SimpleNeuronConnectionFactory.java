package com.bcurry.rednet.neuralnet.factory;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Profile;

import com.bcurry.rednet.neuralnet.abstraction.Neuron;
import com.bcurry.rednet.neuralnet.abstraction.NeuronConnection;
import com.bcurry.rednet.neuralnet.simpleimpl.SimpleNeuronConnection;

@Profile("simple")
public class SimpleNeuronConnectionFactory extends AbsNeuronConnectionFactory {
	@Autowired
	private AutowireCapableBeanFactory factory;

	@Override
	public NeuronConnection makeConnection(Neuron from, Neuron to, Long weight) {
		throw new NotImplementedException("This is not implemented");
	}

	@Override
	public NeuronConnection makeConnection(Neuron from, Neuron to) {
		factory.autowireBean(from);
		factory.autowireBean(to);
		NeuronConnection con = new SimpleNeuronConnection(from, to);
		factory.autowireBean(con);
		return con;
	}

}
