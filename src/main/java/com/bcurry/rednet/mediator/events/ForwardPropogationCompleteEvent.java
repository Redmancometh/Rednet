package com.bcurry.rednet.mediator.events;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.bcurry.rednet.neuralnet.abstraction.OutputLayer;
import com.bcurry.rednet.pojo.NeuronResult;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForwardPropogationCompleteEvent implements Event {
	private Map<UUID, NeuronResult> results = new ConcurrentHashMap();
	private OutputLayer from;
	private double errorTotal;
}
