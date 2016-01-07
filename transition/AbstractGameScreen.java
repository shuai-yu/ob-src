package com.omnibounce.transition;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.omnibounce.constants.Constants;

public abstract class AbstractGameScreen implements Screen {

	protected DirectedGame game;
	protected static final float VIRTUAL_WIDTH = Constants.VIEW_WIDTH;
	protected static final float VIRTUAL_HEIGHT = Constants.VIEW_HEIGHT;
	protected static final float ASPECT_RATIO = VIRTUAL_WIDTH / VIRTUAL_HEIGHT;
	
	public AbstractGameScreen(DirectedGame game) {
		this.game = game;
	}

	public abstract InputProcessor getInputProcessor();

	@Override
	public void render(float delta) {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
