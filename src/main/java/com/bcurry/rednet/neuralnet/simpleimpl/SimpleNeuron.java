package com.bcurry.rednet.neuralnet.simpleimpl;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import org.springframework.context.annotation.Profile;
import com.bcurry.rednet.neuralnet.abstraction.AbsNeuron;

@Profile("simple")
public class SimpleNeuron extends AbsNeuron {

	public SimpleNeuron(UUID uuid, Consumer<Double> inputAcceptor) {
		super(uuid, inputAcceptor);
	}

	@Override
	public Optional<Double> calculateOutput() {
		return super.calculateOutput();
	}

}
