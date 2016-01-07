package com.omnibounce.maps.tokens;

import com.omnibounce.maps.MapObjectToken;

public abstract class BrickToken extends MapObjectToken {

	private final float x;
	private final float y;
	
	public BrickToken(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public final float getX() {
		return x;
	}
	
	public final float getY() {
		return y;
	}
	
}
