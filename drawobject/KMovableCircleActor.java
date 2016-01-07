package com.omnibounce.drawobject;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omnibounce.interfaces.IMovable;
import com.omnibounce.math.Point2D;
import com.omnibounce.math.Vector2D;

public abstract class KMovableCircleActor extends KCircleActor implements IMovable {

	// notes: In this class, getCenter is equivalent to getOffset.
	
	public KMovableCircleActor(TextureRegion centeredRegion) {
		super(centeredRegion);
	}

	private boolean moving = false;
	
	protected final Vector2D v = Vector2D.Null.copy();
	
	public final Point2D getPosition() {
		return offset;
	}
	
	public final Vector2D getVelocity() {
		return v;
	}

	public final void setVelocity(Vector2D rhs) {
		v.set(rhs);
	}
	
	// determine how to move
	protected void moveStep() {
		offset.x = offset.x + v.x;
		offset.y = offset.y + v.y;
	}
	
	// Move Control
	public final boolean isMoving() {
		return moving;
	}
	
	public void moveStart() {
		moveStart(null);
	}
	
	public void moveStart(Vector2D iv) {
		if (v != null) {
			this.v.set(iv);
		}
		this.moving = true;
	}

	public void movePause() {
		this.moving = false;
	}

	public void moveStop() {
		movePause();
		this.v.set(Vector2D.Null);
	}
	
	@Override
	public void onAct(float delta) {
		if (isPresent() && isMoving())
			moveStep();
	}
}
