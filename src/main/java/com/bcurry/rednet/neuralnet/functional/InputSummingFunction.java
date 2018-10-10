package com.bcurry.rednet.neuralnet.functional;

import java.util.Collection;
import java.util.Optional;

import com.bcurry.rednet.neuralnet.abstraction.NeuronConnection;

public interface InputSummingFunction {
	Optional<Double> collectOutput(Collection<NeuronConnection> collection);
}
