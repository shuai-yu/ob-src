package com.omnibounce.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.omnibounce.assets.Assets;
import com.omnibounce.stages.BasicStage;
import com.omnibounce.transition.AbstractGameScreen;
import com.omnibounce.transition.DirectedGame;
import com.omnibounce.transition.ScreenTransition;
import com.omnibounce.transition.ScreenTransitionFade;

public class GameOverScreen extends AbstractGameScreen {
	public GameOverScreen(DirectedGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}


	private Camera camera;
	private Rectangle viewport;

	private Stage stage;

	private Image bg;

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return stage;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		// stage = new Stage();
		stage = new BasicStage();

		build();
		super.show();
	}

	private void translateScreen() {

		// ScreenTransition transition = ScreenTransitionSlide.init(1f,
		// ScreenTransitionSlide.UP, false, Interpolation.circleIn);

		ScreenTransition transition = ScreenTransitionFade.init(1);
		game.setScreen(new SelectScreen(game), transition);
	}

	private Image gameOver;

	private void build() {
		bg = new Image(Assets.getInstance().assetAtlas.bg);
		stage.addActor(bg);

		gameOver = new Image(Assets.getInstance().mateGame.get("screen_lose"));
		Color gameColor = gameOver.getColor();
		gameColor.set(gameColor.r, gameColor.g, gameColor.b, 0);

		gameOver.addAction(sequence(delay(0.2f),
				alpha(1f, 2, Interpolation.sineIn),delay(1), run(new Runnable() {
					public void run() {
						translateScreen();
					}
				})));
		stage.addActor(gameOver);

		stage.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				// TODO Auto-generated method stub
				if (keycode == Keys.BACK) {
					// translateScreen();
					return true;
				}

				return super.keyDown(event, keycode);
			}
		});
		Gdx.input.setCatchBackKey(true);

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
