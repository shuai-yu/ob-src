package com.omnibounce.maps;

import com.badlogic.gdx.graphics.Color;

public class MapUchiha extends KMap {

	public MapUchiha() {
		super("uchiha", "JCX", 3);
	}

	@Override
	protected void build() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 5; j++)
				for (int t = 0; t < 2; t++) {
					add(utils.anyBrick(((t & 1) == 1 ? 1 : -1)
							* (i * 30 + 15), 30 - i * 15 + j * 30).setColor(Color.RED));
				}
		for (int i = 0; i < 4; i++) {
			add(utils.anyBrick(105 * ((i & 1) == 1 ? 1 : -1),
					(i >> 1) * 30 + 45).setColor(Color.RED));
		}
		for (int i = 0; i < 4; i++) {
			add(utils.anyBrick(15 * ((i & 1) == 1 ? 1 : -1), -15 - (i >> 1)
					* 30).setColor(Color.WHITE));
		}
		for (int i = 0; i < 3; i++) {
			add(utils.anyBrick(0, -75 - i * 30).setColor(Color.WHITE));
		}
		for (int i = 0; i < 2; i++) {
			add(utils.anyBrick(45 * ((i & 1) == 1 ? 1 : -1), -30).setColor(Color.WHITE));
		}
	}

}
