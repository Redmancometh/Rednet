package com.bcurry.rednet.neuralnet.abstraction;

import java.util.Optional;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class AbsNeuronConnection implements NeuronConnection {
	protected Neuron from, to;
	@Setter
	protected double weight = Math.random();
	private Optional<Double> lastOutputUnweighted;
	private UUID uuid = UUID.randomUUID();

	public AbsNeuronConnection(Neuron from, Neuron to, double d) {
		super();
		this.from = from;
		this.to = to;
		this.weight = d;
	}

	@Override
	public Optional<Double> calculateOutput() {
		this.lastOutputUnweighted = from.calculateOutput();
		// TODO: isPresent logic for lastOutputUnweighted
		return Optional.of(lastOutputUnweighted.get() * weight);
	}

	@Override
	public UUID getId() {
		return uuid;
	}

	@Override
	public Neuron getFrom() {
		return from;
	}

	@Override
	public Neuron getTo() {
		return to;
	}

	@Override
	public double getWeight() {
		return weight;
	}

}
