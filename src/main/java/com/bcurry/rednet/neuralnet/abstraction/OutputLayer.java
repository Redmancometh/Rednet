package com.bcurry.rednet.neuralnet.abstraction;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bcurry.rednet.mediator.Eventbus;
import com.bcurry.rednet.mediator.events.ForwardPropogationCompleteEvent;
import com.bcurry.rednet.neuralnet.functional.ActivationFunction;
import com.bcurry.rednet.neuralnet.functional.InputSummingFunction;
import com.bcurry.rednet.pojo.NeuronResult;
import com.google.common.util.concurrent.AtomicDouble;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class OutputLayer extends AbsNeuronLayer {
	@Autowired
	InputSummingFunction sumFunc;
	@Autowired
	ActivationFunction actFunc;
	@Autowired
	@Qualifier("output-bias")
	private BiasProvider bias;
	@Autowired
	private Eventbus events;

	public void subscribe() {

	}

	public double propogateTo(double target) {
		AtomicDouble error = new AtomicDouble(0);
		getLayerNeurons().values().forEach((neuron) -> {
			Optional<Double> calculation = neuron.calculateOutput();
			if (calculation.isPresent())
				System.out.println("Error: " + error.addAndGet(Math.pow(2, target - neuron.getLastOut().get()) * .5));
		});
		System.out.println("Total error: " + error);
		return 0;
	}

	public void backPropogate(double target) {
		double learningRate = .5;
		getLayerNeurons().values().forEach((neuron) -> {
			neuron.calculateOutput();
			// net(sub)Ox
			Optional<Double> lastNetOp = neuron.getLastNet();
			// out(sub)Oxv
			Optional<Double> lastOutOp = neuron.getLastOut();
			if (lastNetOp.isPresent() && lastOutOp.isPresent()) {
				double lastNet = lastNetOp.get();
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
					double netWeightDiv = connection.getFrom().getLastOut().get() / weightExp;
					double total = totalOutDiv * outNetDiv * netWeightDiv;
					double newWeight = connection.getWeight() - total * learningRate;
					connection.setWeight(newWeight);
					// System.out.println("New weight " + newWeight + " for connection " +
					// connection.getId());
				});
			}
		});
	}

	/**
	 * Populate this layer with unconnected neurons
	 * 
	 * @param connAmt This is how many neurons the layer will be populated with.
	 *                These neurons will NOT be connected to anything yet!
	 */
	@Override
	public void populateLayer(int connAmt) {
		for (int x = 0; x < connAmt; x++) {
			Neuron n = neuronFactory.constructNeuronFor(this, (result) -> {

			});
			layerNeurons.put(n.getId(), n);
		}
	}

}
