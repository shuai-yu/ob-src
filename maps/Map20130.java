package com.omnibounce.maps;

import com.omnibounce.math.Point2D;

public class Map20130 extends KMap {

	public Map20130() {
		super("20130", "unkown", 3);
	}

	@Override
	protected void build() {
		for (int i = 0; i < 32; i++) {
			add(utils.anyBrick(new Point2D((i & 7) * 30 - 105,
					(((i >> 3) & 1) * 30 + 75) * ((i < 16) ? 1 : -1))));
		}
		for (int i = 0; i < 6; i++) {
			add(utils.anyBrick(new Point2D((105 - ((i % 3) >> 1) * 30)
					* (((i & 1) == 1) ? 1 : -1), 45 * ((i < 3) ? 1 : -1))));
		}
	}

}
