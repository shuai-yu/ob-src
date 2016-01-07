package com.omnibounce.maps;

import com.badlogic.gdx.graphics.Color;
import com.omnibounce.maps.tokens.BrickToken;

public final class MapDemon extends KMap {

	public MapDemon() {
		super("demon", "JCX", 4);
	}

	@Override
	protected void build() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 7; j++) {
				BrickToken b = utils.anyBrick(-90 + j * 30, 15 - i * 30);
				if (i == 2 && j >= 2 && j <= 4)
					b.setColor(Color.RED);
				if (i == 0 && (j == 2 || j == 4))
					b.setColor(Color.RED);
				add(b);
			}
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 3 + 2 * i; j++)
				for (int t = -1; t <= 1; t += 2) {
					BrickToken b = utils.anyBrick(-30 - i * 30 + j * 30, -15 + t
							* (90 - i * 30));
					if (t == -1 && i == 1 && (j == 0 || j == 4))
						b.setColor(Color.RED);
					add(b);
				}
		for (int i = 0; i < 2; i++)
			for (int t = -1; t <= 1; t += 2) {
				add(utils.anyBrick(t * (-75 - i * 15), 75 + i * 30));
			}

	}

}
