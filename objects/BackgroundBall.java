package com.omnibounce.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BackgroundBall extends Image {

	public BackgroundBall(TextureRegion region) {
		super(region);
	}

	public BackgroundBall(TextureRegion region, int width, int height) {
		super(region);
		super.setWidth(width);
		super.setHeight(height);
	}

}
