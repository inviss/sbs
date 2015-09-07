package com.sbs.das.commons.utils;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SwappingFifoQueue<E> {

	private volatile Queue<E> instances = new ConcurrentLinkedQueue<E>();

	public void put(E o) {
		instances.add(o);
	}

	public E get() {
		synchronized(instances) {
			if(!instances.isEmpty())
				return instances.poll();
			else
				return null;
		}

	}

	public E peek() {
		return instances.peek();
	}

	public boolean isEmpty() {
		return instances.isEmpty();
	}

	public int size() {
		return instances.size();
	}

	public Iterator<E> iterator() {
		return instances.iterator();
	}

}
