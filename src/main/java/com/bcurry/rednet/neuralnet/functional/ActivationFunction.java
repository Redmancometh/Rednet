package com.bcurry.rednet.neuralnet.functional;

import java.util.Optional;

public interface ActivationFunction {
	Optional<Double> calculateOutput(double sum, double bias);
}
