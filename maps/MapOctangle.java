package com.omnibounce.maps;

import com.badlogic.gdx.math.MathUtils;
import com.omnibounce.maps.tokens.BrickToken;
import com.omnibounce.maps.tokens.TContinuousWall;
import com.omnibounce.maps.tokens.TSingleWall;
import com.omnibounce.math.Point2D;

public final class MapOctangle extends KMap {

	public MapOctangle() {
		super("octangle", "JCX", 4);
	}

	@Override
	protected void build() {
		add(new TContinuousWall(new Point2D(120, 0)).con(new Point2D(90, 0)).con(new Point2D(90, 30)).con(new Point2D(30, 90)).con(new Point2D(
				-30, 90)).con(new Point2D(-90, 30)).con(new Point2D(
				-90, -30)).con(new Point2D(-30, -90)).con(new Point2D(0, -90)).con(new Point2D(
						0, -120)));
	
		for (int i = 0; i < 4; i++)
			add(new TSingleWall(Point2D.Origin.forwardTo(MathUtils.PI / 2 * i, 30), Point2D.Origin.forwardTo(MathUtils.PI / 2 * (i + 1), 30)));

		for (int i = 0; i < 4; i++) {
			add(utils.anyBrick(75 * ((i & 1) == 1 ? 1 : -1),
					75 * ((i >> 1) == 1 ? 1 : -1)));
		}
		for (int i = 0; i < 4; i++) {
			add(utils.anyBrick(15 * ((i & 1) == 1 ? 1 : -1) + 60,
					15 * ((i >> 1) == 1 ? 1 : -1) + 0));
		}
		for (int i = 0; i < 4; i++) {
			add(utils.anyBrick(15 * ((i & 1) == 1 ? 1 : -1) - 60,
					15 * ((i >> 1) == 1 ? 1 : -1) + 0));
		}
		BrickToken b[] = new BrickToken[20];
		for (int i = 0; i < 2; i++) {
			b[12+i] = utils.anyBrick(-15 + 30 * i, 75);
			add(b[12+i]);
		}
		for (int i = 0; i < 3; i++) {
			b[14+i]=utils.anyBrick(-30 + 30 * i, 45);
			add(b[14+i]);
		}
		for (int i = 0; i < 5; i++) {
			add(utils.anyBrick(b[12 + i].getX(),
					b[12 + i].getY() * -1));
		}
		add(utils.anyBrick(60, -45));
		add(utils.anyBrick(45, -75));
	}

}
