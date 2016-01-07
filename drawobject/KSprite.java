package com.omnibounce.drawobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.omnibounce.math.Point2D;

@Deprecated
public abstract class KSprite {
	
	private Sprite spr = null;
	private boolean visible = true;
	private Point2D pos = new Point2D(Point2D.Origin);
	private float timer = 0;

	protected void useSprite(Sprite spr) {
		assert (spr != null);
		this.spr = spr;
	}

	public void useSpriteAndCenter(Sprite spr) {
		useSprite(spr);
		spr.setOriginCenter();
	}

	protected Sprite getSprite() {
		return spr;
	}

	protected Point2D getPosition() {
		return pos;
	}

	protected float getX() {
		return pos.x;
	}

	protected float getY() {
		return pos.y;
	}
	
	protected float getRealWidth() {
		return spr.getWidth()*spr.getScaleX();
	}
	
	protected float getRealHeight() {
		return spr.getHeight()*spr.getScaleY();
	}

	protected void setPosition(Point2D target) {
		pos.set(target);
	}

	protected void setPosition(float x, float y) {
		pos.x = x;
		pos.y = y;
	}

	protected void setVisible(boolean vis) {
		this.visible = vis;
	}

	protected boolean isVisible() {
		return visible;
	}
	
	public boolean isPresent() {
		return (spr != null);
	}

	protected void draw(SpriteBatch batch) {
		timer += Gdx.graphics.getDeltaTime();
		if (spr!=null && visible) {
			onDraw(timer);
			spr.setCenter((float) pos.x, (float) pos.y);
			spr.draw(batch);
		}
	}
	
	public void destroy() {
		visible = false;
		spr = null;
	}
	
	protected boolean onDraw(float timer) {
		return true;
	}
}
