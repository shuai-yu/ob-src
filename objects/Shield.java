package com.omnibounce.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omnibounce.drawobject.KCircleActor;
import com.omnibounce.interfaces.ICircle;
import com.omnibounce.interfaces.IHittable;
import com.omnibounce.math.Physics2D;
import com.omnibounce.math.Point2D;
import com.omnibounce.math.Vector2D;

public abstract class Shield extends KCircleActor implements ICircle, IHittable {

	private final float radius; // inner

	private final Vector2D ballPos = Vector2D.Null.copy();
	
	public Shield(TextureRegion reg, float radius) {
		super(reg);
		
		this.radius = radius;
	}
	
	@Override
	public final float getRadius() {
		return radius;
	}

	@Override
	public Point2D intersectTest(ICircle obj, Vector2D incidence) {
		
		ballPos.set(obj.getCenter());
		
		if (ballPos.length() + obj.getRadius() < radius) // still inside circle
			return null;

		if (ballPos.addMul(incidence, -1).length() + obj.getRadius() > radius) // far away from circle
			return null;

		if (isSafe(ballPos.set(obj.getCenter()))) {
			return Physics2D.adjustPositionOO(obj, incidence, this, obj.getRadius() - this.getRadius());
		}else
			return null;
	}
	
	protected abstract boolean isSafe(Vector2D OO1);
	
	@Override
	public Vector2D hitTest(ICircle obj, Point2D intersection, Vector2D incidence) {
		return onHit(obj, intersection, incidence, Physics2D.getReflectedVector(incidence, intersection.dispTo(Point2D.Origin)));
	}

	@Override
	public Vector2D onHit(ICircle obj, Point2D intersection, Vector2D incidence, Vector2D reflected) {

		return reflected;
	}

	@Override
	protected void onAct(float delta) {
		
	}
}
