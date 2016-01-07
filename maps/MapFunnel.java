package com.omnibounce.maps;

import com.omnibounce.maps.tokens.TContinuousWall;
import com.omnibounce.math.Point2D;

public class MapFunnel extends KMap {

	public MapFunnel() {
		super("funnel", "JCX", 3);
	}

	@Override
	protected void build() {
		for (int i = 0; i < 6; i++) {
			add(utils.anyBrick(0, 15 - i * 30));
		}
		for (int i = 0; i < 6; i++) {
			add(utils.anyBrick((15 + (i % 3) * 15) * ((i < 3) ? 1 : -1),
					45 + (i % 3) * 30));
		}
		add(utils.anyBrick(0, 75));
		add(new TContinuousWall(new Point2D(-90, 150)).con(new Point2D(
				-15, 0)).con(new Point2D(-90, -150)));
		add(new TContinuousWall(new Point2D(90, 150)).con(new Point2D(
				15, 0)).con(new Point2D(90, -150)));

	}

}
