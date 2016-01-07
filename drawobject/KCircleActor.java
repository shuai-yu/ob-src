package com.omnibounce.drawobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omnibounce.interfaces.ICircle;
import com.omnibounce.math.Point2D;

public abstract class KCircleActor extends KActor implements ICircle {

	public KCircleActor(TextureRegion centeredRegion) {
		super();
		useTextureAndOriginCenter(centeredRegion);
	}

	private float radius;
	
	@Override
	protected boolean checkTexture(TextureRegion region) {
		if (super.checkTexture(region)) {
			if (region.getRegionWidth()==region.getRegionHeight()) {
				radius = region.getRegionWidth() / 2;
				return true;
			}else {
				Gdx.app.error("KACTOR", "Not a circle texture");
			}
		}
		return false;
	}
	
	public float getRadius() {
		return radius * getScaleY();
	}

	public final Point2D getCenter() {
		return getOffset();
	}
	
	public final void setCenter(Point2D rhs) {
		setOffset(rhs);
	}
}
