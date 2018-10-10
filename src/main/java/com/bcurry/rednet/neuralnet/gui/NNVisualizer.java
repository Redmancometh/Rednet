package com.bcurry.rednet.neuralnet.gui;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.swing.JButton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcurry.rednet.mediator.Eventbus;
import com.bcurry.rednet.mediator.events.ConnectionUsedEvent;
import com.bcurry.rednet.neuralnet.abstraction.NeuralNetwork;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
@Data
public class NNVisualizer {
	@Autowired
	private NeuralNetwork network;
	@Autowired
	private VisualizerFrame frame;
	@Autowired
	private VisualizerPanel panel;
	@Autowired
	private Eventbus events;

	@PostConstruct
	public void subscribe() {
		events.<ConnectionUsedEvent>subscribe(ConnectionUsedEvent.class, (e) -> {
			addPaintRedConnection(e.getConnection().getId());
			panel.repaint();
		});
	}

	public void open() {
		JButton button = new JButton("Send input");
		frame.add(button);
		button.addActionListener((e) -> {
			network.takeInput(.5, .2);
		});
		frame.setVisible(true);
	}

	public void addPaintRedConnection(UUID connection) {
		this.panel.addRedConnection(connection);
	}

}
