package com.omnibounce.drawobject;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omnibounce.math.Point2D;

public final class KStillActor extends KActor {

	public KStillActor(TextureRegion region, Point2D center) {
		super();
		useTextureAndOriginCenter(region);
		if (center != null) {
			setCenterPosition(center);
		}
		// note: offset is zero
	}

	@Override
	protected final void onAct(float delta) {
		// nothing to do
	}

}
