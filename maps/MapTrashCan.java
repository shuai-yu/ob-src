package com.omnibounce.maps;

import com.omnibounce.maps.tokens.TContinuousWall;
import com.omnibounce.math.Point2D;

public class MapTrashCan extends KMap {

	public MapTrashCan() {
		super("Trashcan", "JCX", 2);
	}

	@Override
	protected void build() {
		
		add(new TContinuousWall(new Point2D(-90, 30)).con(new Point2D(
				-60, -150)).con(new Point2D(
						60, -150)).con(new Point2D(90, 30)));
		for (int i = 0; i < 8; i++) {
			add(utils.anyBrick(new Point2D((((i & 1) == 1) ? 1 : -1)
					* (15 * ((i >> 1) + 1)), 75 - (i >> 1) * 30)));
		}
		for (int i = 0; i < 4; i++) {
			add(utils.anyBrick(new Point2D(-45 + 30 * i, -45)));
		}
	}

}
