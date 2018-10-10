package com.bcurry.rednet.neuralnet.abstraction;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface Neuron {
	UUID getId();

	/**
	 * Calculate the output from the connections from the previous layer, and return
	 * the result
	 * 
	 * @return The result of the calculation for the given connection.
	 */
	Optional<Double> calculateOutput();

	/**
	 * Get the last calculated net(sub)layerX
	 * 
	 * e.g. output neuron 1 is net(sub)O1
	 * 
	 * @return
	 */
	Optional<Double> getLastOut();

	/**
	 * Get the last calculated out(sub)layerX
	 * 
	 * e.g. output neuron 1 is out(sub)O1
	 * 
	 * @return
	 */
	Optional<Double> getLastNet();

	/**
	 * 
	 * Adds a connection from the incoming neuron to the current neuron.
	 * 
	 * @param from
	 * @param connection
	 * 
	 */
	void addFrom(Neuron from, NeuronConnection connection);

	/**
	 * {@key} The key here is the UUID of the neuron the connection starts at. *
	 * {@value} The value here is the neuron connection itself.
	 */
	Map<UUID, NeuronConnection> getConnectionsIn();

	/**
	 * {@key} The key here is the UUID of the neuron the connection ends at.
	 * {@value} The value here is the neuron connection itself.
	 */
	Map<UUID, NeuronConnection> getConnectionsOut();

	/**
	 * Get an outbound (respect to the given neuron neuron) neuron connection with
	 * 
	 * @param uuid The UUID that belongs to the neuron we're getting a connection to
	 * @return returns an optional representing the found (or not found) connection
	 */
	public Optional<NeuronConnection> getConnectionOut(UUID uuid);

	/**
	 * Get an outbound (respect to the given neuron neuron) neuron connection with
	 * 
	 * @param uuid The UUID that belongs to the neuron we're getting a connection to
	 * @return returns an optional representing the found (or not found) connection
	 */
	public Optional<NeuronConnection> getConnectionIn(UUID uuid);

	/**
	 * 
	 * Adds a connection to the incoming neuron from the current neuron.
	 * 
	 * @param to
	 * @param connection
	 * 
	 */
	void addTo(Neuron to, NeuronConnection connection);

	public default double calculateError(double result, double target) {
		double err = (Math.pow(target - result, 2) * .5);
		return err;
	}

}
