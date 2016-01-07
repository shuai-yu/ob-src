package com.omnibounce.utils;

public class KEvent {

	private volatile boolean state;
	private final boolean autoReset;
	
	public KEvent() {
			this(false, true);
	}
	
	public KEvent(boolean state, boolean autoReset) {
		this.state = state;
		this.autoReset = autoReset;
	}
	
	public void set() {
		state = true;
	}
	
	public void clear() {
		state = false;
	}
	
	public void waitUntilSet() {
		while (!state) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
