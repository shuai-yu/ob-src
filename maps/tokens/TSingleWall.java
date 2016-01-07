package com.omnibounce.maps.tokens;

import com.omnibounce.math.Point2D;

public final class TSingleWall extends TContinuousWall {

	// just a copy
	private final float x1;
	private final float y1;
	private final float x2;
	private final float y2;

	public TSingleWall(Point2D p1, Point2D p2) {
		this(p1.x, p1.y, p2.x, p2.y);
	}
	
	public TSingleWall(float x1, float y1, float x2, float y2) {
		super(x1, y1, x2, y2);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public final float getX1() {
		return x1;
	}
	
	public final float getX2() {
		return x2;
	}
	
	public final float getY1() {
		return y1;
	}
	
	public final float getY2() {
		return y2;
	}

}
