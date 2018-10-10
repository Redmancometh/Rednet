package com.bcurry.rednet.neuralnet.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.bcurry.rednet.neuralnet.abstraction.NeuralNetwork;
import com.bcurry.rednet.neuralnet.abstraction.Neuron;
import com.bcurry.rednet.neuralnet.abstraction.NeuronLayer;

public class VisualizerPanel extends JPanel {
	private static final long serialVersionUID = -1063423463337894123L;
	@Autowired
	private NeuralNetwork network;
	private Map<UUID, Shape> neuronDimensions = new ConcurrentHashMap();
	private Set<UUID> redConns = new HashSet();

	public void addRedConnection(UUID connection) {
		redConns.add(connection);
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Font font = new Font("Serif", Font.PLAIN, 10);
		g2.setFont(font);
		AtomicInteger x = new AtomicInteger(15);
		AtomicInteger y = new AtomicInteger(200);
		drawLayer(g2, network.inputLayer(), x, y, "input");
		network.orderedLayers().forEach((id) -> drawLayer(g2, network.hiddenLayers().get(id), x, y, "hidden"));
		drawLayer(g2, network.outputLayer(), x, y, "output");
		drawConnections(g2);
	}

	public void drawConnections(Graphics2D g2) {
		g2.setColor(Color.CYAN);
		drawConnectionsForLayer(g2, network.inputLayer());
		network.orderedLayers().forEach(layerId -> {
			NeuronLayer layer = network.hiddenLayers().get(layerId);
			drawConnectionsForLayer(g2, layer);
		});
	}

	public void drawConnectionsForLayer(Graphics2D g2, NeuronLayer layer) {
		layer.getConnections().forEach((id, connection) -> {
			if (redConns.contains(id))
				g2.setColor(Color.RED);
			Shape fromCircle = neuronDimensions.get(connection.getFrom().getId());
			Shape toCircle = neuronDimensions.get(connection.getTo().getId());
			g2.drawLine((int) fromCircle.getBounds2D().getCenterX(), (int) fromCircle.getBounds2D().getCenterY(),
					(int) toCircle.getBounds2D().getCenterX(), (int) toCircle.getBounds2D().getCenterY());
			g2.setColor(Color.CYAN);
		});
	}

	public void drawLayer(Graphics2D g2, NeuronLayer layer, AtomicInteger x, AtomicInteger y, String layerName) {
		layer.getNeurons().forEach((neuron) -> drawNeuron(g2, neuron, x, y, layerName));
		x.set(x.get() + 350);
		y.set(200);
	}

	public void drawNeuron(Graphics2D g2, Neuron neuron, AtomicInteger x, AtomicInteger y, String layer) {
		Shape circle = new Ellipse2D.Double(x.get(), y.get(), 200, 200);
		g2.setColor(Color.WHITE);
		g2.fill(circle);
		g2.setColor(Color.BLACK);
		g2.drawString("ID: " + neuron.getId(), (int) circle.getBounds2D().getCenterX() - 100,
				(int) circle.getBounds2D().getCenterY());
		g2.drawString(layer, (int) circle.getBounds2D().getCenterX(), (int) circle.getBounds2D().getCenterY() + 20);
		y.set(y.get() + 200);
		g2.draw(circle);
		this.neuronDimensions.put(neuron.getId(), circle);
	}

	public void addPaintRedNeuron(UUID id) {
		network.hiddenLayers().forEach((layerId, layer) -> {
			layer.getConnections().forEach((connId, connection) -> {
				if (connection.getTo().getId().equals(id)) {
					addRedConnection(connId);
				}
			});
		});
	}

}
