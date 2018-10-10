package com.bcurry.rednet.neuralnet.abstraction;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcurry.rednet.mediator.DataManager;
import com.bcurry.rednet.neuralnet.functional.ActivationFunction;
import com.bcurry.rednet.neuralnet.functional.InputSummingFunction;

import lombok.Data;

@Data
public abstract class AbsNeuron implements Neuron {
	private UUID uuid;
	protected Consumer<Double> inputAcceptor;
	/**
	 * @key The key here is the UUID of the neuron the connection starts at.
	 */
	private Map<UUID, NeuronConnection> connectionsIn = new ConcurrentHashMap();
	/**
	 * @key The key here is the UUID of the neuron the connection ends at.
	 */
	private Map<UUID, NeuronConnection> connectionsOut = new ConcurrentHashMap();
	@Autowired
	private InputSummingFunction sumFunc;
	@Autowired
	private ActivationFunction actFunc;
	@Autowired
	private DataManager data;
	private Optional<Double> lastNet, lastOut;

	public AbsNeuron(UUID uuid, Consumer<Double> inputAcceptor) {
		this.uuid = uuid;
		this.inputAcceptor = inputAcceptor;
	}

	@Override
	public Optional<NeuronConnection> getConnectionIn(UUID uuid) {
		return Optional.ofNullable(connectionsIn.get(uuid));
	}

	@Override
	public Optional<NeuronConnection> getConnectionOut(UUID uuid) {
		return Optional.ofNullable(connectionsOut.get(uuid));
	}

	@Override
	public UUID getId() {
		return uuid;
	}

	@Override
	public Optional<Double> calculateOutput() {
		lastNet = sumFunc.collectOutput(connectionsIn.values());
		lastOut = actFunc.calculateOutput(lastNet.get(), 1);
		return lastOut;
	}

	@Override
	public void addFrom(Neuron from, NeuronConnection connection) {
		// System.out.println("Connection from " + from.getId() + " to " +
		// this.getId());
		this.connectionsIn.put(from.getId(), connection);
	}

	@Override
	public void addTo(Neuron to, NeuronConnection connection) {
		this.connectionsOut.put(to.getId(), connection);
	}

	public double calculateError(double result, double target) {
		double err = (Math.pow(target - result, 2) * .5);
		return err;
	}

}
