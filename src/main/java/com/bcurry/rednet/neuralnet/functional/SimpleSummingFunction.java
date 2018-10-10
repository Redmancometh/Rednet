package com.bcurry.rednet.neuralnet.functional;

import java.util.Collection;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.bcurry.rednet.neuralnet.abstraction.NeuronConnection;

@Profile("simple")
@Component
public class SimpleSummingFunction implements InputSummingFunction {

	@Override
	public Optional<Double> collectOutput(Collection<NeuronConnection> collection) {
		// TODO: isPresent logic for calculateOutput
		return Optional.ofNullable(collection.stream().mapToDouble((conn) -> conn.calculateOutput().get()).sum());
	}

}
