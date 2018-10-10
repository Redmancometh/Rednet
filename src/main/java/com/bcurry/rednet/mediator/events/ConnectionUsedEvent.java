package com.bcurry.rednet.mediator.events;

import com.bcurry.rednet.neuralnet.abstraction.NeuronConnection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectionUsedEvent implements Event {
	private NeuronConnection connection;
}
