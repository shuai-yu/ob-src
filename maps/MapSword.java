package com.omnibounce.maps;

import com.omnibounce.maps.tokens.TContinuousWall;
import com.omnibounce.math.Point2D;

public class MapSword extends KMap {

	public MapSword() {
		super("sword", "jcx", 4);
	}

	@Override
	protected void build() {
		for (int i = 0; i < 12; i++) {
			add(utils.anyBrick(new Point2D(((i < 6) ? 1 : -1) * 15,
					(i % 6) * 30 - 15)));
		}
		for (int i = 0; i < 6; i++) {
			add(utils.anyBrick(new Point2D(-75 + i * 30, -45)));
		}
		for (int i = 0; i < 3; i++) {
			add(utils.anyBrick(new Point2D(0, -75 - i * 30)));
		}
		add(utils.anyBrick(new Point2D(0, 5 * 30 + 15)));
		add(new TContinuousWall(new Point2D(-90, -25)).con(new Point2D(-35, -25)).con(new Point2D(-35, 150)));

		add(new TContinuousWall(new Point2D(90, -25)).con(new Point2D(35, -25)).con(new Point2D(35, 150)));
	}

}
