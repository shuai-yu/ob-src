package com.omnibounce.interfaces;

import com.omnibounce.math.Vector2D;

public interface IMovable {
	public Vector2D getVelocity();

	public void setVelocity(Vector2D v);
	
	// public void setNewVelocity(Vector2D nv);
	
	// public void setNewPosition(Point2D np);

	public boolean isMoving();

	public void moveStart(Vector2D iv);
	
	public void movePause();
	
	public void moveStop();
	
//	public Point2D getLastPosition();
}