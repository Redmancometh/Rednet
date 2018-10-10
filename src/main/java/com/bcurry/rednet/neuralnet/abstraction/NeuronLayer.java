package com.bcurry.rednet.neuralnet.abstraction;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface NeuronLayer {

	/**
	 * Populate this layer with unconnected neurons
	 * 
	 * @param connAmt This is how many neurons the layer will be populated with.
	 *                These neurons will NOT be connected to anything yet!
	 */
	void populateLayer(int connections);

	/**
	 * Connects every neuron in this layer to the given NeuronLayer
	 * 
	 * @param layer This is the layer whos neurons will be connected to
	 * @return
	 */
	void connectTo(NeuronLayer layer);

	/**
	 * Gets all the neurons for this layer. This is unfiltered and will return both
	 * connected and unconnected neurons.
	 * 
	 * @return
	 */
	Collection<Neuron> getNeurons();

	/**
	 * Have every neuron in the NeuronLayer calculate their outputs, so the next
	 * layer can get them.
	 * 
	 * @return
	 */
	public double propogateTo(double target);

	public BiasProvider getBias();

	Map<UUID, NeuronConnection> getConnections();

	UUID getId();

}
