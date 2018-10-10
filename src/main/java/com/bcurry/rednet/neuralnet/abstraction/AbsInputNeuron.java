package com.bcurry.rednet.neuralnet.abstraction;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class AbsInputNeuron extends AbsNeuron implements InputNeuron {
	private Optional<Double> input;

	public AbsInputNeuron(UUID uuid, Consumer<Double> inputAcceptor) {
		super(uuid, inputAcceptor);
	}

	@Override
	public void passInput(double d) {
		System.out.println("Passed input to absinputneuron");
		this.input = Optional.of(d);
	}

	@Override
	public Optional<Double> calculateOutput() {
		System.out.println("Calculating output from absinputneuron");
		return input;
	}

}
