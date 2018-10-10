package com.bcurry.rednet.neuralnet.simpleimpl;

import java.util.Optional;

import org.springframework.context.annotation.Profile;

import com.bcurry.rednet.neuralnet.abstraction.AbsNeuronLayer;

@Profile("simple")
public class SimpleNeuronLayer extends AbsNeuronLayer {

	public void backPropogate(double target) {
		double learningRate = .5;
		getLayerNeurons().values().forEach((neuron) -> {
			neuron.calculateOutput();
			// net(sub)Ox
			Optional<Double> lastNetOp = neuron.getLastNet();
			// out(sub)Oxv
			Optional<Double> lastOutOp = neuron.getLastOut();
			if (lastNetOp.isPresent() && lastOutOp.isPresent()) {
				// The net of the neurons behind the output layer multiplied by the squish
				// function
				double out = lastOutOp.get();
				// partial derivative of Etotal over outOx
				double totalOutDiv = out - target;
				// partdiv of outOx over netOx
				double outNetDiv = out * (1 - out);
				neuron.getConnectionsIn().values().forEach((connection) -> {
					// https://goo.gl/S3nJrB this is the w5 here. I don't know why it has the 1-1
					// exponent
					double weightExp = Math.pow(connection.getWeight(), (1 - 1));
					double netWeightDiv = connection.getFrom().getLastOut().get() * weightExp;
					double total = totalOutDiv * outNetDiv * netWeightDiv;
					double newWeight = connection.getWeight() - total * learningRate;
					connection.setWeight(newWeight);
					// System.out.println("New weight " + newWeight + " for connection " +
					// connection.getId());
				});
			}
		});
	}

}
