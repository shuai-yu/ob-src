package com.omnibounce.math;

import com.badlogic.gdx.math.MathUtils;

public final class Vector2D {

	public float x;
	public float y;
	
	public static final Vector2D tmp = new Vector2D(0, 0);

	public Vector2D(float x, float y) {
		set(x, y);
	}

	@Deprecated
	public Vector2D get() {
		return this;
	}

	public Vector2D set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2D set(Vector2D rhs) {
		this.x = rhs.x;
		this.y = rhs.y;
		return this;
	}
	
	public Vector2D set(Point2D rhs) {
		this.x = rhs.x;
		this.y = rhs.y;
		return this;
	}

	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
	public Vector2D copyt() {
		tmp.x = x;
		tmp.y = y;
		return tmp;
	}

	public Vector2D scalarMul(float multiplier) {
		this.x *= multiplier;
		this.y *= multiplier;
		return this;
	}

	public Vector2D add(Vector2D rhs) {
		this.x += rhs.x;
		this.y += rhs.y;
		return this;
	}

	public Vector2D addMul(Vector2D rhs, float multiplier) {
		this.x += rhs.x * multiplier;
		this.y += rhs.y * multiplier;
		return this;
	}

	public Vector2D addMul(Vector2D rhs, Vector2D multiplier) {
		this.x += rhs.x * multiplier.x;
		this.y += rhs.y * multiplier.y;
		return this;
	}

	public Vector2D sub(Vector2D rhs) {
		this.x -= rhs.x;
		this.y -= rhs.y;
		return this;
	}

	public float length2() {
		return x * x + y * y;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float radians() {
		return MathUtils.atan2(y, x);
	}

	public float degrees() {
		return RealOp.rad2Deg(radians());
	}

	public Vector2D normalize() {
		float len = length();
		if (RealOp.epsIsPositive(len)) {
			scalarMul(1 / len);
		}
		return this;
	}

	public Vector2D setLength(float dist) {
		float len = length();
		if (RealOp.epsIsPositive(len)) {
			scalarMul(dist / len);
		}
		return this;
	}

	public Vector2D limitLength(float maxDist) {
		if (length2() > maxDist * maxDist) {
			setLength(maxDist);
		}
		return this;
	}

	public Vector2D clampLength(float min, float max) {
		final float len2 = length2();
		assert (min < max);
		if (len2 < min * min)
			setLength(min);
		else if (len2 > max * max)
			setLength(max);
		return this;
	}

	public float dotProduct(Vector2D rhs) {
		return this.x * rhs.x + this.y * rhs.y;
	}

	public float cosineBetween(Vector2D rhs) {
		return dotProduct(rhs) / (length() * rhs.length());
	}

	public float crossProduct(Vector2D rhs) {
		return this.x * rhs.y - this.y * rhs.x;
	}

	public float sineBetween(Vector2D rhs) {
		return crossProduct(rhs) / (length() * rhs.length());
	}

	public Vector2D projectOn(Vector2D n) {
		return n.copy().scalarMul(dotProduct(n) / n.length2());
	}

	public float projectLen(Vector2D n) {
		return dotProduct(n) / n.length();
	}

	public Vector2D getOneNormal() {
		return new Vector2D(-y, x);
	}

	public Vector2D reverse() {
		this.x = -x;
		this.y = -y;
		return this;
	}

	public static Vector2D makeUnitVector(float theta) {
		return new Vector2D(MathUtils.cos(theta), MathUtils.sin(theta));
	}

	public static Vector2D makeRandomUnitVector() {
		return makeUnitVector(MathUtils.random() * MathUtils.PI2);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public final static Vector2D Null = new Vector2D(0, 0);
	public final static Vector2D PosY = new Vector2D(1, 0);

}
