package com.omnibounce.interfaces;

import com.omnibounce.math.Point2D;
import com.omnibounce.math.Vector2D;

public interface IHittable {
	
	public Point2D intersectTest(ICircle obj, Vector2D incidence);
	public Vector2D hitTest(ICircle obj, Point2D intersection, Vector2D incidence);
	
	public Vector2D onHit(ICircle obj, Point2D intersection, Vector2D incidence, Vector2D reflected);
}