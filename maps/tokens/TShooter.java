package com.omnibounce.maps.tokens;

import com.badlogic.gdx.math.MathUtils;

public final class TShooter extends THardBrick {

	public static final float DEFAULT_SHOOT_INTERVAL = 5f;
	
	private final float interval;
	
	public TShooter(float x, float y) {
		this(x, y, DEFAULT_SHOOT_INTERVAL + (MathUtils.random() - 0.5f) * 3);
	}
	
	public TShooter(float x, float y, float interval) {
		super(x, y);
		
		assert( interval >= 1f );
		this.interval = interval;
	}
	
	public final float getShootInterval() {
		return interval;
	}


}
