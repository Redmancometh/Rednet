package com.bcurry.rednet.neuralnet.simpleimpl;

import com.bcurry.rednet.neuralnet.abstraction.AbsBiasProvider;

public class SimpleBiasProvider extends AbsBiasProvider {
	private double bias;

	public SimpleBiasProvider(double bias) {
		this.bias = bias;
	}

	@Override
	public double getBias() {
		return bias;
	}
}
