package com.omnibounce.math;

import com.badlogic.gdx.math.MathUtils;

public final class RealOp {

	// CONSTANT: epsilon
	public static final float eps = (float)1e-3;
	public static final float neps = (float)-(1e-3);

	public static float squareSum(float value1, float value2) {
		return value1 * value1 + value2 * value2;
	}
	
	public static float sumSquare(float value1, float value2) {
		return (value1 + value2) * (value1 + value2);
	}

	public static int epsSign(float value) {
		if (value > eps)
			return 1;
		else if (value < neps)
			return -1;
		else
			return 0;
	}
	
	public static int epsSignByDiff(float a, float b) {
		return epsSign(a-b);
	}
	
	public static boolean epsIsPositive(float value) {
		return value > eps;
	}
	
	public static boolean epsIsZero(float value) {
		return Math.abs(value) <= eps;
	}
	
	public static boolean epsIsEqual(float a, float b) {
		return Math.abs(a-b) <= eps;
	}
	
	public static boolean epsIsNegative(float value) {
		return value < neps;
	}

	public static float angleDiff(float rad1, float rad2) {
		
		float tmp = Math.abs(rad1-rad2);

		if (tmp > MathUtils.PI2 - tmp)
			return MathUtils.PI2 - tmp;
		else
			return tmp;
	}
	
	public static float rad2Deg(float rad) {
		return rad * MathUtils.radDeg;
	}

	public static float cos2(float y, float x) {
		return x / (float) Math.sqrt(x*x+y*y);
	}
}
