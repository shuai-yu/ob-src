package com.omnibounce.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.omnibounce.assets.Assets;
import com.omnibounce.constants.Constants;
import com.omnibounce.stages.BasicStage;
import com.omnibounce.transition.AbstractGameScreen;
import com.omnibounce.transition.DirectedGame;
import com.omnibounce.transition.ScreenTransition;
import com.omnibounce.transition.ScreenTransitionSlide;

public class CreditsScreen extends AbstractGameScreen {

	private Stage stage;

	private Image bg;
	private Image foreground;
	private ImageButton back;

	public CreditsScreen(DirectedGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return stage;
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new BasicStage();
		build();
		super.show();
	}

	private void translateScreen() {

		ScreenTransition transition = ScreenTransitionSlide.init(1f,
				ScreenTransitionSlide.RIGHT, true, Interpolation.sine);
		game.setScreen(new MenuScreen(game), transition);
	}

	private void build() {
		bg = new Image(Assets.getInstance().assetAtlas.bg);
		stage.addActor(bg);

		stage.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				// TODO Auto-generated method stub
				if (keycode == Keys.BACK) {

					translateScreen();

					return true;
				}

				return super.keyDown(event, keycode);
			}
		});
		Gdx.input.setCatchBackKey(true);

		foreground = new Image(
				Assets.getInstance().assetLevelAtlas.creditsRegion);
		foreground.setPosition(Constants.VIEW_WIDTH / 2 - foreground.getWidth()
				/ 2, Constants.VIEW_HEIGHT / 2 - foreground.getHeight() / 2);
		stage.addActor(foreground);

		back = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetAtlas.exit),new TextureRegionDrawable(Assets.getInstance().assetAtlas.exitdown));
		back.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				translateScreen();
			}
		});
		back.setPosition(650, 40);
		stage.addActor(back);

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		super.render(delta);
	}
}
