package com.omnibounce.utils;

public abstract class KTimer {
	
	private static boolean frozen;

	private float totalTime;
	private float timer;
	private boolean looping;
	
	public KTimer(float duration) {
		this.totalTime = duration;
		this.timer = 0;
		this.looping = false;
		start();
	}

	public KTimer(float interval, boolean looping, boolean autoStart) {
		this.totalTime = interval;
		this.timer = 0;
		this.looping = looping;
		assert( isTicking() );
		
		if (autoStart) {
			// start right now
			start();
		}
	}
	
	public final void onAct(float delta) {
		if (isTicking() && !frozen) {
			onTick(timer);
			timer -= delta;
			if (!isTicking()) {
				timer = 0;
				onOver();
				if (looping) {
					// play again
					start();
				}
			}
		}
	}
	
	public final boolean isLooping() {
		return looping;
	}
	
	public final void start() {
		this.timer = totalTime;
		onStart();
	}
	
	public final boolean isTicking() {
		return timer > 0;
	}
	
	public final float getTicks() {
		return timer;
	}
	
	public final float getPercent() {
		return (float) 1.0 - timer / totalTime;
	}
	
	public final void kill() {
		if (isTicking()) {
			timer = 0;
			onOver();
		}
		// notes: Looping timer is always ticking by default.
		// This method will stop looping timers from ticking as well.
	}
	
	public static final boolean isTicking(KTimer timer) {
		return (timer != null) && timer.isTicking();
	}
	
	public static final void tick(KTimer timer, float delta) {
		if (timer != null) timer.onAct(delta);
	}
	
	public static final void kill(KTimer timer) {
		if (timer != null) timer.kill();
	}
	
	protected void onStart() {
	}
	
	protected void onTick(float timer) {
	}
	
	protected abstract void onOver();
	
	public static final void freezeAllTimers(boolean enabled) {
		frozen = enabled;
	}
}
