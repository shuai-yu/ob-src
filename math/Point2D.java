package com.omnibounce.math;

import com.badlogic.gdx.math.MathUtils;

public final class Point2D {

	public float x;
	public float y;

	public static final Point2D tmp = new Point2D(0, 0);
	public static final Vector2D tmpv = new Vector2D(0, 0);
	
	// Setters
	public Point2D(float x, float y) {
		set(x, y);
	}

	public Point2D(Point2D rhs) {
		set(rhs);
	}
	
	public void set(Point2D rhs) {
		this.x = rhs.x;
		this.y = rhs.y;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Point2D copyt() {
		tmp.x = x;
		tmp.y = y;
		return tmp;
	}

	public int distCmp(Point2D target, float reference) {
		return RealOp.epsSign(distTo2(target) - reference*reference);
	}

	// displacement to
	public Vector2D dispTo(Point2D target) {
		return new Vector2D(target.x - this.x, target.y - this.y);
	}
	
	public Vector2D dispTo_flipY(Point2D target) {
		return new Vector2D(target.x - this.x, this.y - target.y);
	}

	// distance to
	public float distTo(Point2D target) {
		return (float)Math.sqrt(distTo2(target));
	}

	public float distTo2(Point2D target) {
		final float dx = target.x - this.x;
		final float dy = target.y - this.y;
		return dx*dx+dy*dy;
	}

	// direction to
	public float directionTo(Point2D target) {
		return MathUtils.atan2(target.y - this.y, target.x - this.x);
	}

	public float directionTo_flipY(Point2D target) {
		return MathUtils.atan2(this.y - target.y, target.x - this.x);
	}

	// forward to
	public Point2D forwardTo(float theta, float dist) {
		return new Point2D(this.x + dist * MathUtils.cos(theta), this.y
				+ dist * MathUtils.sin(theta));
	}
	
	public Point2D forwardTo(Vector2D v, float dist) {
		return forwardToN(tmpv.set(v).normalize(), dist);
	}
	
	public Point2D forwardTo(Point2D p, float dist) {
		return forwardToN(tmpv.set(p.x - this.x, p.y - this.y).normalize(), dist);
	}
	
	public Point2D forwardToN(Vector2D v_n, float dist) {
		return new Point2D(this.x + v_n.x * dist, this.y + v_n.y *dist);
	}
	
	// mirroring
	public Point2D mirror(Point2D center) {
		return new Point2D(2*center.x-this.x, 2*center.y-this.y);
	}
	
	// middle
	public Point2D middle(Point2D rhs) {
		return new Point2D((this.x+rhs.x)/2,(this.y+rhs.y)/2);
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public final static Point2D Origin = new Point2D(0, 0);
}
