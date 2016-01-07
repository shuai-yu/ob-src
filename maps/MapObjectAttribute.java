package com.omnibounce.maps;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omnibounce.drawobject.KActor;

public class MapObjectAttribute {

	// basic attributes
	protected float opacity = 1;
	protected boolean visible = true;
	protected final Color color = Color.WHITE.cpy();
	protected TextureRegion texture = null;
	protected float zoom = 0f; // no zoom, default size
	
	public MapObjectAttribute() {
	}

	// getters
	public final float getOpacity() {
		return opacity;
	}
	
	public final boolean isVisible() {
		return visible;
	}
	
	public final Color getColor() {
		return color;
	}

	public final TextureRegion getTexture() {
		return texture;
	}
	
	public final float getScale() {
		return zoom;
	}
	
	// apply
	public final <T extends MapObjectToken> T asTemplate(T rhs) {
		assert(this != rhs);
		rhs.setColor(this.color);
		rhs.setOpacity(this.opacity);
		rhs.setVisible(this.visible);
		rhs.setTexture(this.texture);
		rhs.setScale(this.zoom);
		return rhs;
	}
	
	public final <T extends KActor> T applyTo(T actor) {
		actor.setColor(this.color);
		actor.setVisible(this.visible);
		actor.setAlpha(this.opacity);
		if (this.zoom > 0) actor.setScale(this.zoom);
		// can't set actor's texture directly
		return actor;
	}
}
