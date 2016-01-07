package com.omnibounce.maps.tokens;

import java.util.ArrayList;
import java.util.List;

import com.omnibounce.math.Point2D;

public class TContinuousWall extends WallToken {

	private List<Point2D> ptArray = new ArrayList<Point2D>();
	
	public TContinuousWall(Point2D first) {
		ptArray.add(first);
	}
	
	public TContinuousWall(float x, float y) {
		ptArray.add(new Point2D(x, y));
	}
	
	public TContinuousWall(List<Point2D> ptList) {
		ptArray.addAll(ptList);
	}
	
	public TContinuousWall(float x1, float y1, float x2, float y2) {
		ptArray.add(new Point2D(x1, y1));
		ptArray.add(new Point2D(x2, y2));
	}
	
	public final TContinuousWall con(Point2D p) {
		ptArray.add(p);
		return this;
	}
	
	public final TContinuousWall con(float x, float y) {
		ptArray.add(new Point2D(x, y));
		return this;
	}
	
	public final TContinuousWall cons(List<Point2D> p) {
		ptArray.addAll(ptArray);
		return this;
	}
	
	public final int getPointCount() {
		return ptArray.size();
	}
	
	public final Point2D getPoint(int index) {
		return ptArray.get(index);
	}
	
	public final int getWallCount() {
		return ptArray.size()-1;
	}
}
