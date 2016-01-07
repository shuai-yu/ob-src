package com.omnibounce.drawobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.omnibounce.math.Point2D;

public abstract class KActor implements Disposable {
	
	private Actor actor;
	private Sprite sprite;
	
	// sprite attributes
	protected final Point2D offset = new Point2D(Point2D.Origin);
	protected final Point2D center = new Point2D(Point2D.Origin);
	private float timer = 0;
	
	public final float getTimer() {
		return timer;
	}
	
	// Template method
	protected abstract void onAct(float delta);
	
	protected void onDraw(Batch batch, float timer) {
		// to be implemented
	}
	
	protected void afterDraw() {
		// to be implemented
	}
	
	protected void onStage() {
		// to be implemented
	}
	
	public KActor() {
		actor = new Actor() {
			@Override
			public final void act(float delta) {
				super.act(delta);
				
				if ( isPresent() ) {
					timer += delta;
					onAct(delta);
				}
			}
			
			@Override
			public final void draw(Batch batch, float parentAlpha) {
				// if not visible, this method won't be called by Actor class.
				super.draw(batch, parentAlpha);
				
				if ( isPresent() /* && actor.isVisible() */) {
					sprite.setCenter(getActualX(), getActualY());
					onDraw(batch, timer);
					sprite.draw(batch);
					afterDraw();
				}
			}
		};
	}
	
	protected boolean checkTexture(TextureRegion region) {
		return (region != null);
	}
	
	protected final void useTexture(TextureRegion region) {
		if (!checkTexture(region)) {
			Gdx.app.error("KACTOR", "Invalid texture");
			Gdx.app.error("KACTOR", region.getTexture().toString());
		}else {
			assert sprite == null;
			
			this.sprite = new Sprite(region);
		}
	}
	
	protected final void useTextureAndOriginCenter(TextureRegion texture) {
		useTexture(texture);
		this.sprite.setOriginCenter();
	}
	
	protected final void changeTexture(TextureRegion region) {
		if (checkTexture(region)) {
			this.sprite.setRegion(region);
		}
	}

	@Deprecated
	protected final Actor getActor() {
		return actor;
	}
	
	@Deprecated
	protected final Sprite getSprite() {
		return sprite;
	}
	
	public final boolean addToStage(Stage stage) {
		if (stage != null && actor != null) {
			stage.addActor(actor);
			onStage();
			return true;
		}else {
			Gdx.app.error("KACTOR", "Invalid arguments");
			return false;
		}
	}
	
	public final boolean removeFromStage() {
		return actor.remove();
	}
	
	protected final TextureRegion getTextureRegion() {
		return sprite;
	}

	protected final float getOrgWidth() {
		return sprite.getRegionWidth();
	}
	
	protected final float getOrgHeight() {
		return sprite.getRegionHeight();
	}
	
	public final float getActualWidth() {
		return getOrgWidth() * sprite.getScaleX();
	}
	
	public final float getActualHeight() {
		return getOrgHeight() * sprite.getScaleY();
	}

	protected final Point2D getOffset() {
		return offset;
	}
	
	public final float getOffsetX() {
		return offset.x;
	}
	
	public final float getOffsetY() {
		return offset.y;
	}
	
	public final float getActualX() {
		return center.x + offset.x;
	}
	
	public final float getActualY() {
		return center.y + offset.y;
	}
	
	protected final void setOffset(Point2D off) {
		this.offset.set(off);
	}
	
	protected final void setOffset(float x, float y) {
		this.offset.set(x, y);
	}
	
	protected final void setCenterPosition(Point2D center) {
		this.center.set(center);
	}
	
	protected final void setRotation(float degrees) {
		sprite.setRotation(degrees);
	}
	
	protected final void setScale(float scaleX, float scaleY) {
		sprite.setScale(scaleX, scaleY);
	}
	
	public void setScale(float scaleXY) {
		sprite.setScale(scaleXY);
	}
	
	public final float getScaleY() {
		return sprite.getScaleY();
	}
	
	public final void setColor(Color tint) {
		sprite.setColor(tint);
	}
	
	protected final void setFlip(boolean flipX, boolean flipY) {
		sprite.setFlip(flipX, flipY);
	}

	public final void setAlpha(float a) {
		sprite.setAlpha(a);
	}
	
	public final void show() {
		actor.setVisible(true);
	}
	
	public final void hide() {
		actor.setVisible(false);
	}
	
	public final void setVisible(boolean vis) {
		actor.setVisible(vis);
	}

	public final boolean isVisible() {
		return actor.isVisible();
	}
	
	public final boolean isPresent() {
		return (sprite != null);
	}
	
	public void dispose() {
		if (sprite != null /* && actor != null*/) {
			actor.remove();
			
			sprite = null;
			// actor = null;
		}
	}
}
