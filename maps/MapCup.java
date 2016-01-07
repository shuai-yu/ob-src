package com.omnibounce.maps;

import com.omnibounce.maps.tokens.TBlackHole;
import com.omnibounce.maps.tokens.THardBrick;

public final class MapCup extends KMap {

	public MapCup() {
		super("cup", "JCX", 5);
	}

	@Override
	protected void build() {
		for (int i = 0; i < 5; i++)
			for (int t = -1; t <= 1; t += 2) {
				add(new THardBrick(-60 + i * 30, 60 + 45 * t));
			}
		for (int i = 0; i < 4; i++)
			for (int t = -1; t <= 1; t += 2) {
				add(utils.anyBrick(t * (105 - (i >> 1) * 15), 60
						+ (15 + 30 * ((i >> 1) == 1 ? 1 : -1))
						* ((i & 1) == 1 ? 1 : -1)));
			}
		for (int i = 0; i < 4; i++)
			for (int t = -1; t <= 1; t += 2) {
				add(utils.anyBrick(t * (30 + (i >> 1) * 15), -60
						+ (15 + 30 * ((i >> 1) == 1 ? 1 : -1))
						* ((i & 1) == 1 ? 1 : -1)));
			}
		for (int i = 0; i < 2; i++) {
			add(new THardBrick(-15 + i * 30, -105));
		}
		for (int t = -1; t <= 1; t += 2) {
				add(new TBlackHole(t * (30 + (0 & 1) * 15), 60 + (0 & 1) * 75, t * (30 + (1 & 1) * 15), 60 + (1 & 1) * 75).setScale(0.75f));

		}
	}

}
