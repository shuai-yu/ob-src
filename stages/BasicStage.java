package com.omnibounce.stages;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.omnibounce.constants.Constants;

public class BasicStage extends Stage {
	
	private boolean paused = false;
	private boolean visible = true;

	public BasicStage() {
		super(new ScalingViewport(Scaling.fill, Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, new OrthographicCamera(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT)));
	}

	public final void render(float delta) {
		onRender(delta, getBatch());
		// act & draw
		if (!paused)
			act(delta);
		if (visible)
			draw();
	}
	
	// Don't override act() or draw() in subclasses.
	@Override
	public final void act(float delta) {
		super.act(delta);
	}
	
	@Override
	public final void draw() {
		super.draw();
	}
	
	protected void onRender(float delta, Batch batch) {
		// You can tick timers here.
		
		// If you want to draw something, remember to begin the batch and end it. 
	}
	
	public final void pause() {
		paused = true;
	}
	
	public final void resume() {
		paused = false;
	}
	
	public final boolean isPaused() {
		return paused;
	}
	
	public final void hide() {
		visible = false;
	}
	
	public final void show() {
		visible = true;
	}
	
	public final boolean isVisible() {
		return visible;
	}
}
