package com.omnibounce.maps;

import java.util.ArrayList;
import java.util.List;

import com.omnibounce.maps.tokens.TBlackHole;
import com.omnibounce.maps.tokens.TContinuousWall;
import com.omnibounce.math.Point2D;

public final class MapWave extends KMap {

	public MapWave() {
		super("Wave", "JCX", 5);
	}

	@Override
	protected void build() {
		List<Point2D> p = new ArrayList<Point2D>();
		p.add(new Point2D(-165, 0));
		p.add(new Point2D(-150, 60));
		p.add(new Point2D(-90, -90));
		p.add(new Point2D(-75, -30));
		p.add(new Point2D(-60, -60));
		p.add(new Point2D(-30, 90));
		p.add(new Point2D(-15, 30));
		p.add(new Point2D(0, 60));
		p.add(new Point2D(30, -30));
		p.add(new Point2D(45, 0));
		p.add(new Point2D(60, -30));
		p.add(new Point2D(75, 30));
		p.add(new Point2D(90, -60));
		p.add(new Point2D(105, 0));
		p.add(new Point2D(120, -30));
		p.add(new Point2D(135, 30));
		add(new TContinuousWall(p));
		p.clear();
		
		p.add(new Point2D(-120, -60));
		p.add(new Point2D(-105, 75));
		p.add(new Point2D(-90, 0));
		p.add(new Point2D(-75, 45));
		p.add(new Point2D(-60, 75));
		p.add(new Point2D(-60, 105));
		p.add(new Point2D(-30, -15));
		p.add(new Point2D(-30, -75));
		p.add(new Point2D(0, 0));
		p.add(new Point2D(0, -45));
		p.add(new Point2D(0, -105));
		p.add(new Point2D(30, -120));
		p.add(new Point2D(45, 75));
		p.add(new Point2D(75, 60));
		p.add(new Point2D(75, 90));
		p.add(new Point2D(75, -90));
		p.add(new Point2D(105, 30));
		p.add(new Point2D(105, 105));
		for (int i = 0; i < p.size(); i++) {
			add(utils.anyBrick(p.get(i)));
		}
		add(new TBlackHole(0, 90, 45, -60));
	}

}
