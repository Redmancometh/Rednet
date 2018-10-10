package com.bcurry.rednet.mediator.events;

@FunctionalInterface
public interface EventSubscriber<T extends Event> {
	public void eventCalled(T e);
}
