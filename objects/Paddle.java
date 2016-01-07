package com.omnibounce.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.omnibounce.drawobject.KActor;
import com.omnibounce.interfaces.ICircle;
import com.omnibounce.interfaces.IHittable;
import com.omnibounce.math.Physics2D;
import com.omnibounce.math.Point2D;
import com.omnibounce.math.RealOp;
import com.omnibounce.math.Vector2D;

public abstract class Paddle extends KActor implements IHittable {

	public static final float MAX_LENGTH = 2.0f;
	public static final float DEF_LENGTH = 1.0f;
	public static final float MIN_LENGTH = 0.5f;
	public static final float LENGTHEN_PERCENT = 0.5f;
	public static final float SHORTEN_PERCENT = 0.2f;
	public static final float TOLERANCE_DISTANCE = 5.0f;

	private final Vector2D angle = new Vector2D(1, 0);
	private final Vector2D normal = new Vector2D(0 , -1);
	private final Vector2D angleRev = new Vector2D(-1, 0);
	private float cosCentralAngle;
	private float maxStretch2;
	private final float endSize;
	private final float deskRadius; // outer
	private final float width;
	private float validLength;
	
	public Paddle(TextureRegion reg, float deskRadius, float endSize) {
		useTextureAndOriginCenter(reg);
		
		// this.endSize = endSize;
		this.endSize = 0;
		this.deskRadius = deskRadius;
		this.width = getActualWidth();
	}

	// Angle
	public final Vector2D getAngle() {
		return angle;
	}

	public void setAngle(Vector2D v) {
		normal.set(-v.y, v.x);
		angleRev.set(-v.x, -v.y);
		angle.set(v).normalize();
		
		setOffset(Point2D.Origin.forwardToN(angle, deskRadius));
		setRotation(angle.degrees());
	}
	
	// Length
	public final float getValidLength() {
		return validLength;
	}

	private final void update() {
		validLength = getActualHeight() - 2 * endSize;
		maxStretch2 = RealOp.squareSum(validLength / 2,deskRadius + width / 2);
		cosCentralAngle = (deskRadius + width / 2) / (float)Math.sqrt(maxStretch2);
	}
	
	private final void setNewScale(float ratio) {
		setScale(1, MathUtils.clamp(ratio, MIN_LENGTH, MAX_LENGTH));
		update();
	}
	
	public void lengthen() {
		setNewScale(getScaleY() * ( 1 + LENGTHEN_PERCENT ));
	}
	
	public void shorten() {
		setNewScale(getScaleY() * ( 1 - SHORTEN_PERCENT ));
	}
	
	protected void restoreLength() {
		setNewScale(DEF_LENGTH);
	}
	
	public void resetAll() {
		restoreLength();
	}
	
	public final boolean isOutOfReach(ICircle obj) {
		return obj.getCenter().distTo(Point2D.Origin) > maxStretch2 + obj.getRadius() + TOLERANCE_DISTANCE;
	}

	@Override
	public Point2D intersectTest(ICircle obj, Vector2D incidence) {
		Point2D O1 = obj.getCenter();
		Vector2D OO1 = Point2D.Origin.dispTo(O1);
		float cosTheta = OO1.cosineBetween(getAngle());
		if (RealOp.epsSignByDiff(cosTheta, cosCentralAngle) >= 0) {
			// in the bouncing sector

			// projection test
			float plen = OO1.projectLen(angle);
			if (plen < deskRadius - obj.getRadius() || plen > deskRadius + obj.getRadius() + TOLERANCE_DISTANCE)
				return null;
			
			// direction test
			float cosAlpha = incidence.cosineBetween(getAngle());
			switch (RealOp.epsSign(cosAlpha)) {
			case 1:
				// fix position
				float offset = OO1.length() * cosTheta
						- (deskRadius - obj.getRadius());
				return O1.forwardTo(incidence, -offset / cosAlpha);
			case 0:
				// perpendicular, no need to fix
				return O1;
			case -1:
				// wrong direction
				return null;
			}
			
			return O1;
		}
		
		return null;
	}
	
	@Override
	public Vector2D hitTest(ICircle obj, Point2D intersection, Vector2D incidence) {
		Vector2D OO1 = Point2D.Origin.dispTo(intersection);
		// projection test 2
		if (RealOp.epsSignByDiff(Math.abs(OO1.projectLen(getAngle().getOneNormal())), getValidLength() / 2) <= 0)
			return onHit(obj, intersection, incidence, Physics2D.getReflectedVector(incidence, angleRev));
		else
			return null;
	}
	
	@Override
	public Vector2D onHit(ICircle obj, Point2D intersection, Vector2D incidence,
			Vector2D reflected) {
		
		return reflected;
	}
}