package com.omnibounce.maps;
import java.util.ArrayList;
import java.util.List;

import com.omnibounce.maps.tokens.TContinuousWall;
import com.omnibounce.maps.tokens.TSingleWall;
import com.omnibounce.math.Point2D;


public final class MapFish extends KMap {

	public MapFish() {
		super("fish", "YTY", 3);
	}

	@Override
	protected void build() {
		List<Point2D> p = new ArrayList<Point2D>();
		p.add(new Point2D(-165, -75));
		p.add(new Point2D(-165, 75));
		p.add(new Point2D(-60, 0));
		p.add(new Point2D(45, 75));
		p.add(new Point2D(150, 0));
		add(new TContinuousWall(p));
		add(new TSingleWall(new Point2D(-60, 0),
				new Point2D(45, -70)));
		
		for (int i = 0; i < 3; i++)
			for (int j = 0; j <= i; j++) {
				add(utils.anyBrick(-75 - i * 30, -15 + 15 * i - j * 30));
			}
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 3 - i; j++)
				for (int t = -1; t <= 1; t += 2) {
					add(utils.anyBrick((t == 1 ? 60 : 30) + t * i * 30, 30
							- i * 15 - j * 30));
				}
	}

}
