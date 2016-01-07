package com.omnibounce.maps;

import com.omnibounce.maps.tokens.TBlackHole;
import com.omnibounce.maps.tokens.TContinuousWall;
import com.omnibounce.math.Point2D;

public class MapSuperman extends KMap {

	public MapSuperman() {
		super("superman", "JCX", 4);
	}

	@Override
	protected void build() {
		add(new TContinuousWall(new Point2D(0, 75)).con(new Point2D(
				30, 75)).con(new Point2D(
						60, 30)).con(new Point2D(
								0, -30)).con(new Point2D(-60, 30)).con(new Point2D(-30, 75)).con(new Point2D(0, 75)));

		for (int i = 0; i < 10; i++) {
			float x = 0;
			switch (i % 5) {
			case 0:
				x = -75;
				break;
			case 1:
				x = -105;
				break;
			case 2:
				x = -90;
				break;
			case 3:
				x = -60;
				break;
			case 4:
				x = -30;
				break;
			}
			add(utils.anyBrick(((i < 5) ? 1 : -1) * x, 75 - ((i % 5)) * 30));
		}
		for (int i = 0; i < 4; i++) {
			add(utils.anyBrick(-45 + i * 30, 105));
		}
		add(utils.anyBrick(0, -75));
		add(new TBlackHole(0, 30, 0, -45));
	}

}
