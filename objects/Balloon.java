package com.omnibounce.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omnibounce.drawobject.KMovableCircleActor;
import com.omnibounce.math.Vector2D;

public abstract class Balloon extends KMovableCircleActor {

	public static final float INITIAL_SPEED = (float) 2.0;
	public static final float ROTATIONAL_SPEED = (float) 3.0;
	
	private float degrees = 0;
	private boolean rotating = false;
	
	public Balloon(TextureRegion reg) {
		super(reg);
		hide();
	}
	
	@Override
	public void moveStart(Vector2D dir) {
		this.rotating = true;
		super.moveStart(dir.copy().setLength(INITIAL_SPEED));
		show();
	}
	
	public final void dontRotate() {
		this.rotating = false;
	}
	
	@Override
	public void onAct(float delta) {
		super.onAct(delta);
		if (isPresent() && rotating) {
			setRotation(degrees);
			degrees += ROTATIONAL_SPEED;
		}
	}

}
