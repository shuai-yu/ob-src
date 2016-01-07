package com.omnibounce.math;

import com.omnibounce.interfaces.ICircle;

public final class Line2D {
	public final Point2D p1;
	public final Point2D p2;

	// these are calculated by update()
	private float A, B, C; // Ax+By+C=0
	private Point2D pMiddle;
	private final Vector2D normal = new Vector2D(0, 0); // (A,B)
	private float normalLen;
	private Vector2D vec;
	private float dir;

	public Line2D(float theta1, float dist1, float theta2, float dist2) {
		this.p1 = Point2D.Origin.forwardTo(theta1, dist1);
		this.p2 = Point2D.Origin.forwardTo(theta2, dist2);
		update();
	}

	public Line2D(Point2D p1, Point2D p2) {
		assert (p1 != null && p2 != null);
		this.p1 = p1;
		this.p2 = p2;
		update();
	}
	
	public Line2D set(Point2D p1, Point2D p2) {
		this.p1.set(p1);
		this.p2.set(p2);
		update();
		return this;
	}

	public float length() {
		return p1.distTo(p2);
	}

	public void update() {
		assert (p1 != null && p2 != null);

		this.vec = p1.dispTo(p2);
		this.dir = p1.directionTo(p2);
		this.pMiddle = p1.middle(p2);
		this.A = p2.y - p1.y;
		this.B = p1.x - p2.x;
		this.C = -A * p1.x - B * p1.y;
		this.normal.set(A, B); // WARNING: DONT'T NORMALIZE it!
		this.normalLen = normal.length();

		assert (0 == sideTest(p2));
	}

	public int sideTest(Point2D p) {
		return RealOp.epsSign(A * p.x + B * p.y + C);
	}

	public float distTo(Point2D p) {
		return Math.abs(A * p.x + B * p.y + C) / normalLen;
	}

	public Vector2D getOneNormal() {
		return normal;
	}

	public Vector2D getOneVector() {
		return vec;
	}

	public float getOneDirection() {
		return dir;
	}

	public Point2D getMiddle() {
		return pMiddle;
	}

	public boolean isBetween(Point2D p) {
		return isBetween(p, this.p1, this.p2);
	}
	
	public boolean isBetween(Point2D p, Point2D p1, Point2D p2) {
		return RealOp.epsSign(p.dispTo(p1).crossProduct(normal)) != RealOp
				.epsSign(p.dispTo(p2).crossProduct(normal));
	}

	public boolean isBetween(ICircle c) {
		final float r = c.getRadius();
		Point2D np1, np2, p;

		np1 = p1.forwardTo(vec, -r);
		np2 = p2.forwardTo(vec, r);
		p = c.getCenter();

		return isBetween(p, np1, np2);
	}
}
