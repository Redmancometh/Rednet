package com.bcurry.rednet.neuralnet.simpleimpl;

import org.springframework.context.annotation.Profile;

import com.bcurry.rednet.neuralnet.abstraction.BiasProvider;
import com.bcurry.rednet.neuralnet.abstraction.InputLayer;
import com.bcurry.rednet.neuralnet.abstraction.InputNeuron;

@Profile("simple")
public class SimpleInputLayer extends InputLayer {
	public void passInput(double input) {
		layerNeurons.values().forEach((neuron) -> ((InputNeuron) neuron).passInput(input));
	}

	@Override
	public void populateLayer(int connAmt) {
		for (int x = 0; x < connAmt; x++) {
			InputNeuron n = neuronFactory.constructInputNeuronFor(this, (result) -> {

			});
			getData().addNeuron(n);
			System.out.println("Put neuron " + n.getId() + " in layer " + this.getId());
			layerNeurons.put(n.getId(), n);
		}
	}

	@Override
	public BiasProvider getBias() {
		return null;
	}

	@Override
	public void backPropogate(double target) {
		
	}
}
