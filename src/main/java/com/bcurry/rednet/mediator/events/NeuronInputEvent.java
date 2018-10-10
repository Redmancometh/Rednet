package com.bcurry.rednet.mediator.events;

import com.bcurry.rednet.neuralnet.abstraction.Neuron;
import com.bcurry.rednet.neuralnet.abstraction.NeuronConnection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NeuronInputEvent implements Event {
	private Neuron neuron;
	private NeuronConnection connection;
}
