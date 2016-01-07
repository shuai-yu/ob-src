package com.omnibounce.maps;

import java.util.ArrayList;
import java.util.List;

import com.omnibounce.maps.tokens.TContinuousWall;
import com.omnibounce.math.Point2D;

public final class MapSea extends KMap {

	public MapSea() {
		super("sea", "JCX", 3);
	}

	@Override
	protected void build() {
		List<Point2D> p = new ArrayList<Point2D>();
		p.add(new Point2D(-150, -120));
		p.add(new Point2D(-120, -60));
		p.add(new Point2D(-90, -120));
		p.add(new Point2D(-60, -60));
		p.add(new Point2D(-30, -120));
		p.add(new Point2D(0, -60));
		p.add(new Point2D(30, -120));
		p.add(new Point2D(60, -60));
		p.add(new Point2D(90, -120));
		p.add(new Point2D(120, -60));
		p.add(new Point2D(150, -120));
		add(new TContinuousWall(p));
		for (int i = 0; i < 5; i++) {
			int n;
			if (i < 2)
				n = 6;
			else if (i < 3)
				n = 3;
			else
				n = 2;
			for (int j = 0; j < n; j++) {
				add(utils.anyBrick(j * 30 - 75, 105 - i * 30));
			}
		}
	}

}
