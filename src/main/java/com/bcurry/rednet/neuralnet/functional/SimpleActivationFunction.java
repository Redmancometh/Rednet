package com.bcurry.rednet.neuralnet.functional;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("simple")
@Component
public class SimpleActivationFunction implements ActivationFunction {

	// Etotal .298371109
	// Etotal outO1 derivative = .74136507
	//

	@PostConstruct
	public void testActFunc() {
		System.out.println("Calculating test input");
		Optional<Double> output = calculateOutput(1.1059095967, 1);
		System.out.println("Got " + output);
		System.out.println("Expected .75136507");
	}

	public void runNumbers() {

	}

	@PostConstruct
	public void testFunc2() {
		double target = .01;
		double net = 1.105905967;
		double outO1 = .75136507;
		double outH1 = .593269992;
		double weight = .40;
		double totalOutDiv = outO1 - target;
		System.out.println("TotalOut " + totalOutDiv);
		double outNetDiv = outO1 * (1 - outO1);
		System.out.println("outNetDiv " + outNetDiv);
		System.out.println("netWeightDiv: " + outH1);
		System.out.println("TOTALED: " + (totalOutDiv * outNetDiv * outH1));
	}

	@Override
	public Optional<Double> calculateOutput(double summedInput, double bias) {
		return Optional.of(1 / (1 + Math.exp(-summedInput)));
	}

}
