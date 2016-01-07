package com.omnibounce.maps;

import com.badlogic.gdx.math.MathUtils;
import com.omnibounce.maps.tokens.TShooter;
import com.omnibounce.math.Point2D;
import com.omnibounce.math.Vector2D;

public final class MapFascist extends KMap {

	public MapFascist() {
		super("fascist", "jcx", 3);
	}

	@Override
	protected void build() {
		add(new TShooter(0, 0, 5f));
		Point2D pos = null;
		float angle = 0;
		for (int i = 1; i <= 32; i++) {
			switch ((i - 1) & 7) {
			case 0:
				angle = ((i - 1) >> 3) * MathUtils.PI / 2;
				pos = Point2D.Origin;
			case 1:
			case 2:
			case 3:
				break;
			case 4:
				angle += Math.PI / 2;
			case 5:
			case 6:
			case 7:
				break;
			}
			pos = pos.forwardToN(Vector2D.makeUnitVector(angle), 30);
			add(utils.anyBrick(pos));
		}

	}

}
