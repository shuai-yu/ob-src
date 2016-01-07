package com.omnibounce.maps;

import com.badlogic.gdx.math.MathUtils;
import com.omnibounce.maps.tokens.ItemType;
import com.omnibounce.maps.tokens.TBlackHole;
import com.omnibounce.maps.tokens.TContainerBrick;
import com.omnibounce.maps.tokens.TContinuousWall;
import com.omnibounce.maps.tokens.THardBrick;
import com.omnibounce.maps.tokens.TNormalBrick;
import com.omnibounce.maps.tokens.TShooter;
import com.omnibounce.math.Point2D;

public final class MapTest extends KMap {

	public MapTest() {
		super("Test only", "Daryl", 1);
	}

	@Override
	protected void build() {
//		add(new TNormalBrick(100, 100).setColor(Color.YELLOW)).add(new TNormalBrick(-100, 50));
//		add(new TCounterBrick(-200,-100, 3));
		
		for (int i = 0; i < 6; i++) {
			Point2D p = Point2D.Origin.forwardTo(MathUtils.PI2 * i/6, 100);
			switch (i) {
			case 1:
				add(new TBlackHole(p.x, p.y, -p.x, -p.y));
			case 4:
				break;
			case 2:
				add(new TShooter(p.x, p.y, 3).setScale(2));
				break;
			case 3:
//				add(new TContainerBrick(p.x, p.y, 1, 0.8f, ItemType.MANGET));
				break;
			case 0:
				add(new THardBrick(p.x, p.y));
				break;
			default:
				add(new TNormalBrick(p.x, p.y));
				break;
			}
		}
		// Create dividers
		add(new TContinuousWall(Point2D.Origin.forwardTo(MathUtils.PI / 6, 150)).con(Point2D.Origin.forwardTo(-MathUtils.PI / 6,
				150)).con(Point2D.Origin.forwardTo(-MathUtils.PI / 6 * 5, 150)));
		
	}

}
