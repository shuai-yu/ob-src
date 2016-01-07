package com.omnibounce.maps;

import com.omnibounce.math.Point2D;

public class MapShip extends KMap {

	public MapShip() {
		super("ship", "yty", 2);
	}

	@Override
	protected void build() {
		int i, j;
		for (i = 1; i <= 3; i++)
			for (j = 0; j < 10 - i * 2; j++) {
				add(utils.anyBrick(new Point2D((((j & 1) == 1) ? 1 : -1)
						* ((j >> 1) * 30 + 15), -i * 30 - 15)));
			}
		for (i = 0; i < 3; i++)
			for (j = 0; j < 5 - i * 2; j++) {
				add(utils.anyBrick(new Point2D(i * 30 - 15, -15 + 30
						* (i + j))));
			}
	}

}
