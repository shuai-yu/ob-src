package com.omnibounce.maps;

public final class MapWifi extends KMap {

	public MapWifi() {
		super("wifi", "JCX", 4);
	}

	@Override
	protected void build() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 3; j++)
				for (int t = 0; t < 2; t++) {
					add(utils.anyBrick(((t & 1) == 1 ? 1 : -1)
					* (i * 30 + 15), 45 - i * 15 + j * 30));
				}
		for (int i = 0; i < 4; i++) {
			add(utils.anyBrick(135 * ((i & 1) == 1 ? 1 : -1),
					(i >> 1) * 30 + 15));
		}

		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 2; j++)
				for (int t = 0; t < 2; t++) {
					add(utils.anyBrick(((t & 1) == 1 ? 1 : -1)
							* (i * 30 + 15), -15 - i * 15 - j * 30));
				}

		for (int i = 0; i < 2; i++) {
			add(utils.anyBrick(75 * ((i & 1) == 1 ? 1 : -1), -45));
		}
		for (int i = 0; i < 4; i++) {
			add(utils.anyBrick(15 * ((i & 1) == 1 ? 1 : -1), -105
					- (i >> 1) * 30));
		}
	}

}
