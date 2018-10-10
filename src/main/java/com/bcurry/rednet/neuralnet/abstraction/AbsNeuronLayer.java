package com.bcurry.rednet.neuralnet.abstraction;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bcurry.rednet.mediator.DataManager;
import com.bcurry.rednet.neuralnet.factory.NeuronConnectionFactory;
import com.bcurry.rednet.neuralnet.factory.NeuronFactory;
import com.bcurry.rednet.neuralnet.functional.ActivationFunction;

import lombok.Data;

@Data
public abstract class AbsNeuronLayer implements NeuronLayer {
	@Autowired
	protected NeuronFactory neuronFactory;
	@Autowired
	private NeuronConnectionFactory connFactory;
	@Autowired
	private DataManager data;
	@Autowired
	private ActivationFunction actFunc;
	@Autowired
	@Qualifier("hidden-bias")
	private BiasProvider bias;
	private UUID id = UUID.randomUUID();
	/**
	 * We use a map of the UUID, so that once it's accessed it can be rapidly saved
	 * or re-retrieved from the layer easily. Also far-removed components that only
	 * have the UUID can grab the entire object much easier.
	 */
	protected Map<UUID, Neuron> layerNeurons = new ConcurrentHashMap();
	protected Map<UUID, NeuronConnection> connections = new ConcurrentHashMap();

	public Collection<Neuron> getNeurons() {
		return layerNeurons.values();
	}

	/**
	 * Populate this layer with unconnected neurons
	 * 
	 * @param connAmt This is how many neurons the layer will be populated with.
	 *                These neurons will NOT be connected to anything yet!
	 */
	@Override
	public void populateLayer(int connAmt) {
		for (int x = 0; x < connAmt; x++) {
			Neuron n = neuronFactory.constructNeuronFor(this, (result) -> {

			});
			data.addNeuron(n);
			// System.out.println("Put neuron " + n.getId() + " in layer " + this.getId());
			layerNeurons.put(n.getId(), n);
		}
	}

	/**
	 * @Override public double propogateTo(double target) { // TODO: This is
	 *           probably going to change in case I want to change my summing //
	 *           functions getLayerNeurons().values().stream().forEach((neuron) -> {
	 *           neuron.calculateOutput(); }); double sum =
	 *           getLayerNeurons().values().stream().mapToDouble((neuron) ->
	 *           neuron.calculateOutput().get()).sum(); Optional<Double> output =
	 *           actFunc.calculateOutput(sum, getBias().getBias()); //
	 *           System.out.println("Final output in OL: " + output); return
	 *           output.get(); }
	 **/

	@Override
	public void connectTo(NeuronLayer layer) {
		getNeurons().forEach((neuron) -> {
			layer.getNeurons().forEach((otherNeuron) -> {
				NeuronConnection connection = connFactory.makeConnection(neuron, otherNeuron);
				connections.put(connection.getId(), connection);
				neuron.addTo(otherNeuron, connection);
				otherNeuron.addFrom(neuron, connection);
				data.addConnection(connection);
			});
		});
	}
}
