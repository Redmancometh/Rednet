package com.bcurry.rednet.mediator;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcurry.rednet.neuralnet.abstraction.Neuron;
import com.bcurry.rednet.neuralnet.abstraction.NeuronConnection;
import com.bcurry.rednet.neuralnet.abstraction.NeuronLayer;

import lombok.Getter;

@Component
public class DataManager {
	@Autowired
	private Map<UUID, Neuron> masterNeuronMap;

	@Autowired
	private Map<UUID, NeuronLayer> masterLayerMap;

	@Autowired
	@Getter
	private Map<UUID, NeuronConnection> masterConnMap;

	public void addNeuron(Neuron neuron) {
		masterNeuronMap.put(neuron.getId(), neuron);
	}

	public void addLayer(NeuronLayer layer) {
		masterLayerMap.put(layer.getId(), layer);
	}

	public Set<NeuronConnection> getConnectionsToNeuron(UUID neuron) {
		return masterConnMap.values().stream().filter((connection) -> connection.getTo().getId().equals(neuron))
				.collect(Collectors.toSet());
	}

	public Set<NeuronConnection> getConnectionsFromNeuron(UUID neuron) {
		return masterConnMap.values().stream().filter((connection) -> connection.getFrom().getId().equals(neuron))
				.collect(Collectors.toSet());

	}

	public void addConnection(NeuronConnection conn) {
		masterConnMap.put(conn.getId(), conn);
	}

	public Optional<NeuronConnection> getConnection(UUID uuid) {
		return Optional.ofNullable(masterConnMap.get(uuid));
	}

	public Optional<NeuronLayer> getLayer(UUID uuid) {
		return Optional.ofNullable(masterLayerMap.get(uuid));

	}

	public Optional<Neuron> getNeuron(UUID uuid) {
		return Optional.ofNullable(masterNeuronMap.get(uuid));

	}
}
