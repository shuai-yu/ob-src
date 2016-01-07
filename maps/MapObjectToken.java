package com.omnibounce.maps;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public abstract class MapObjectToken extends MapObjectAttribute {

	public final MapObjectToken justLike(MapObjectToken template) {
		return template.asTemplate(this);
	}
	
	// setters with method chaining
	public final MapObjectToken setOpacity(float alpha) {
		this.opacity = alpha;
		return this;
	}
	
	public final MapObjectToken setVisible(boolean visible) {
		this.visible = visible;
		return this;
	}
	
	public final MapObjectToken setColor(Color color) {
		this.color.mul(color);
		return this;
	}

	public final MapObjectToken setTexture(TextureRegion texture) {
		this.texture = texture;
		return this;
	}

	public final MapObjectToken setScale(float ratio) {
		this.zoom = ratio;
		return this;
	}
}
