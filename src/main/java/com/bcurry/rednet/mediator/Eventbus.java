package com.bcurry.rednet.mediator;

import org.springframework.stereotype.Component;

import com.bcurry.rednet.mediator.events.Event;
import com.bcurry.rednet.mediator.events.EventSubscriber;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

@Component
public class Eventbus {
	private Multimap<Class, EventSubscriber> eventMap = HashMultimap.create();

	public <T extends Event> void subscribe(Class subscribeTo, EventSubscriber<T> subscriber) {
		eventMap.put(subscribeTo, subscriber);
	}

	public void throwEvent(Event e) {
		eventMap.get(e.getClass()).forEach((subscriber) -> {
			subscriber.eventCalled(e);
		});
	}
}
