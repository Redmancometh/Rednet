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
	private Map<UUID, NeuronResult> results = new ConcurrentHashMap();

	public void subscribe() {

	}

	// TODO: Add learning rate multiplier
	@Override
	public double propogateTo(double target) {
		double learningRate = .5;
		getLayerNeurons().values().forEach((neuron) -> {
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
					double netWeightDiv = connection.getFrom().getLastOut().get() * weightExp;
					double total = totalOutDiv * outNetDiv * netWeightDiv;
					double newWeight = connection.getWeight() - total * learningRate;
					connection.setWeight(newWeight);
					System.out.println("New weight " + newWeight + " for connection " + connection.getId());
				});
			}
		});
		return 0;
	}

	public double calculateTotalError() {
		if (results.size() < 1)
			throw new IllegalStateException(
					"Calculate total error called with results map empty. This likely means the output layer hasn't had propogateTo called on it yet.");
		double totalError = results.values().stream().mapToDouble((result) -> {
			double error = result.getError();
			System.out.println("Neuron got " + result.getResult() + " but was looking for " + result.getTarget()
					+ " error factor is " + result.getError());
			return error;
		}).sum();
		events.throwEvent(new ForwardPropogationCompleteEvent(results, this, totalError));
		System.out.println("Total error: " + totalError);
		return totalError;
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
