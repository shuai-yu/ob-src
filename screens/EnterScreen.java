package com.omnibounce.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.omnibounce.assets.Assets;
import com.omnibounce.constants.Constants;
import com.omnibounce.objects.BackgroundBall;
import com.omnibounce.transition.AbstractGameScreen;
import com.omnibounce.transition.DirectedGame;
import com.omnibounce.transition.ScreenTransition;
import com.omnibounce.transition.ScreenTransitionFade;

public class EnterScreen extends AbstractGameScreen {

	private Stage stage;
	private BackgroundBall backgroundBall;
	Label skipLabel;

	private MenuScreen menuScreen;

	public EnterScreen(DirectedGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return stage;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage(new ScalingViewport(Scaling.fill, 800,
				480, new OrthographicCamera()));
		menuScreen = new MenuScreen(game);

		// Table layerBG = buildBackground();
		// stage.addActor(layerBG);

		Image image = new Image(Assets.getInstance().assetAtlas.bg);
		stage.addActor(image);

		buildBackgroundBall();
		bulidSkipLabel();
		bulidLoge();

		super.show();
	}

	private void buildBackgroundBall() {
		backgroundBall = new BackgroundBall(
				Assets.getInstance().mateGame.get("gear"));
		backgroundBall.setPosition(0 - backgroundBall.getWidth(),
				Constants.VIEW_HEIGHT / 2 - backgroundBall.getHeight() / 2);
		backgroundBall.setOrigin(backgroundBall.getWidth() / 2,
				backgroundBall.getHeight() / 2);

		backgroundBall.addAction(parallel(
				sequence(
						moveTo(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT / 2
								- backgroundBall.getHeight() / 2, 6,
								Interpolation.linear), run(new Runnable() {
							public void run() {
								game.setScreen(menuScreen);
							}
						})),
				forever(sequence(rotateTo(-360, 5), run(new Runnable() {
					public void run() {
						backgroundBall.setRotation(0);
					}
				})))));

		stage.addActor(backgroundBall);
	}
	
	private void bulidLoge() {
		Image loge;
		loge = new Image(Assets.getInstance().assetAtlas.logo);
		stage.addActor(loge);
		loge.setPosition(0 - loge.getWidth(),
				Constants.VIEW_HEIGHT / 2 - loge.getHeight() / 2);

		loge.addAction(sequence(
				moveTo(Constants.VIEW_WIDTH / 2 - loge.getWidth() / 2,
						Constants.VIEW_HEIGHT / 2 - loge.getHeight() / 2, 3),
				moveTo(30, 380 - loge.getHeight(), 3, Interpolation.swingOut)));

	}

	private void bulidSkipLabel() {
		Skin skin = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),
				new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));

		skipLabel = new Label("Skip...", skin);
		skipLabel.setPosition(670, 420);
		skipLabel.addAction(forever(sequence(fadeIn(0.5f), fadeOut(0.5f))));
//		skipLabel.addListener(new ChangeListener() {
//
//			@Override
//			public void changed(ChangeEvent event, Actor actor) {
//				System.out.println("11111111111111111111111111");
//			}
//		});
		skipLabel.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				ScreenTransition transition = ScreenTransitionFade.init(0.75f);
				game.setScreen(menuScreen, transition);
				return super.touchDown(event, x, y, pointer, button);
			}
		});

		skipLabel.setFontScale(2);

		stage.addActor(skipLabel);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setScreenSize((int) Constants.VIEW_WIDTH,
				(int) Constants.VIEW_HEIGHT);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
		if (Gdx.input.justTouched()) {
			ScreenTransition transition = ScreenTransitionFade.init(0.75f);
			game.setScreen(menuScreen, transition);
		}
		super.render(delta);
	}

}
