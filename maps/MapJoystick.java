package com.omnibounce.maps;

import com.badlogic.gdx.math.MathUtils;
import com.omnibounce.maps.tokens.TContinuousWall;
import com.omnibounce.maps.tokens.TSingleWall;
import com.omnibounce.math.Point2D;

public class MapJoystick extends KMap {

	public MapJoystick() {
		super("Joystick", "YTY", 1);
	}

	@Override
	protected void build() {
		add(new TContinuousWall(new Point2D(-165, 90)).con(new Point2D(-165, -60)).con(new Point2D(165, -60)).con(new Point2D(165, 90)));
		add(new TSingleWall(new Point2D(-45, 45), new Point2D(
				-15, 45)));
		add(new TSingleWall(new Point2D(45, 45), new Point2D(
				15, 45)));
		for (int i = 0; i < 4; i++) {
			add(utils.anyBrick(new Point2D(-105, 15).forwardTo(MathUtils.PI / 2 * i, 30) ));
		}
		for (int i = 0; i < 2; i++) {
			add(utils.anyBrick(new Point2D(0, 15).forwardTo(MathUtils.PI * i,
					15)));
		}
		for (int i = 0; i < 4; i++) {
			add(utils.anyBrick(new Point2D(105, 15).forwardTo(MathUtils.PI / 2
					* i + MathUtils.PI / 4, 30)));
		}
	}

}

