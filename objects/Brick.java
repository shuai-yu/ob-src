package com.omnibounce.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omnibounce.drawobject.KCircleActor;
import com.omnibounce.interfaces.ICircle;
import com.omnibounce.interfaces.IHittable;
import com.omnibounce.math.Physics2D;
import com.omnibounce.math.Point2D;
import com.omnibounce.math.Vector2D;

public abstract class Brick extends KCircleActor implements ICircle, IHittable {
	
	public Brick(TextureRegion reg) {
		super(reg);
	}
	
	public final void moveTo(Point2D target) {
		setOffset(target);
	}
	
	public final void moveTo(float x, float y) {
		setOffset(x, y);
	}
	
	@Override
	public Point2D intersectTest(ICircle obj, Vector2D incidence) {
		if (Physics2D.overlapCircleCircle(obj, this))
			return Physics2D.adjustPositionOO(obj, incidence, this, obj.getRadius() + this.getRadius());
		else
			return null;
	}

	@Override
	public Vector2D hitTest(ICircle obj, Point2D intersection, Vector2D incidence) {
		return onHit(obj, intersection, incidence, Physics2D.getReflectedVector(incidence, this.getCenter()
					.dispTo(intersection)));
	}
	
	@Override
	protected void onAct(float delta) {
		// nothing to do in most cases
	}
}
