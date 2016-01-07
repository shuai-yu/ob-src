package com.omnibounce.maps;

import com.omnibounce.maps.tokens.TContinuousWall;
import com.omnibounce.math.Point2D;

public class MapFaze extends KMap {

	public MapFaze() {
		super("Faze", "YTY", 2);
	}

	@Override
	protected void build() {

		add(new TContinuousWall(new Point2D(-45, 45)).con(new Point2D(
				-90, -70)).con(new Point2D(
						90, -70)));
		
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3 + i; j++) {
				add(utils.anyBrick(-30 - i * 15 + j * 30, 15 - i * 30));
			}
		for (int i = 0; i < 3; i++) {
			add(utils.anyBrick(-75 - i * 30, 45 - i * 15));
		}
	}

}
