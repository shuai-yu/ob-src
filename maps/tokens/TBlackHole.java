package com.omnibounce.maps.tokens;

public final class TBlackHole extends THardBrick {

	private final float palX;
	private final float palY;
	
	public TBlackHole(float x1, float y1, float x2, float y2) {
		super(x1, y1);
		this.palX = x2;
		this.palY = y2;
	}
	
	public final float getPalX() {
		return palX;
	}
	
	public final float getPalY() {
		return palY;
	}
	
	
}
