package com.omnibounce.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.omnibounce.assets.Assets;
import com.omnibounce.share.ScreenShot;
import com.omnibounce.share.ShareContent;
import com.omnibounce.stages.BasicStage;
import com.omnibounce.transition.AbstractGameScreen;
import com.omnibounce.transition.DirectedGame;
import com.omnibounce.transition.ScreenTransition;
import com.omnibounce.transition.ScreenTransitionFade;
import com.omnibounce.transition.ScreenTransitionSlide;

public class PassScreen extends AbstractGameScreen {

	private Stage stage;

	private Image bg;

	private int currentTime = 0;
	private int bestTime = 0;
	private char level = 'A';
	private int levelNumber = 1;
	private int usedpaddle;
	
	public PassScreen(DirectedGame game, int curTime, int bstTime, int levelWorld, char levelScene, int usedpaddle) {
		super(game);
		// TODO Auto-generated constructor stub
		this.currentTime = curTime;
		this.bestTime = bstTime;
		this.level = levelScene;
		this.levelNumber = levelWorld;
		this.usedpaddle = usedpaddle;
	}

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		stage = new BasicStage();

		build();
		super.show();
	}

	private Image stageClear;
	private Image timeScore;
	private ImageButton mainMenuButton;
	private ImageButton nextStageButton;
	private ImageButton showOffButton;

	private boolean fontFlag = false;
	private TextureRegion[][] regDigits;
	private TextureRegion[][] regLetters;

	private void displayNumber(Batch batch, int startX, int startY, int x,
			int digits) {
		while ((digits--) > 0 || x > 0) {
			int tmp = x % 10;
			x /= 10;
			TextureRegion r = regDigits[0][(tmp >= 1) ? (tmp - 1) : 9];
			startX -= r.getRegionWidth();
			batch.draw(r, startX, startY);
		}
	}

	private float alpha = 0;
	private float devAlpha = 0.0166f;

	private void drawNumbers(SpriteBatch batch) {

		if (alpha < 1)
			alpha += devAlpha;
		else
			alpha = 1;

		// batch.setColor(r, g, b, a)
		batch.setColor(0.725490f, 0, 0, alpha);
		batch.begin();

		// currentTime
		displayNumber(batch, 400, 201, currentTime, 3);
		// bestTime
		displayNumber(batch, 400, 169, bestTime, 3);
		// levelNumber
		displayNumber(batch, 300, 292, levelNumber, 1);
		
		// 
		displayNumber(batch, 400, 480 - 355, usedpaddle, 1);

		
		// 0 - > A, 1 -> B, 2 -> C, 4 -> D, 5 -> E
		batch.draw(regLetters[0][level-'a'], 370, 292);

		batch.end();
	}

	private float stageClearX = 75;
	private float stageClearY = 380;
	private float timeScoreX = 50;
	private float timeScoreY = 40;
	private float mainMenuButtonX = 500;
	private float mainMenuButtonY = 285;
	private float nextStageButtonX = 500;
	private float nextStageButtonY = 210;
	private float showOffButtonX = 500;
	private float showOffButtonY = 40;

	private void build() {

		regDigits = Assets.getInstance().mateGame.get("digit").split(18, 15);
		regLetters = Assets.getInstance().assetAtlas.alphafont.split(18, 15);

		bg = new Image(Assets.getInstance().assetAtlas.bg);
		stage.addActor(bg);

		stageClear = new Image(
				Assets.getInstance().assetAtlas.stageclear);
		stageClear.setPosition(stageClearX, stageClearY + 100);
		timeScore = new Image(
				Assets.getInstance().assetAtlas.timescore);
		timeScore.setPosition(timeScoreX - 450, timeScoreY);
		mainMenuButton = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetAtlas.mainmenubutton),new TextureRegionDrawable(Assets.getInstance().assetAtlas.mainmenubuttondown));
		mainMenuButton.setPosition(mainMenuButtonX + 300, mainMenuButtonY);
		nextStageButton = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetAtlas.nextstagebutton),new TextureRegionDrawable(Assets.getInstance().assetAtlas.nextstagebuttondown));
		nextStageButton.setPosition(nextStageButtonX + 300, nextStageButtonY);
		showOffButton = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetAtlas.showoffbutton),new TextureRegionDrawable(Assets.getInstance().assetAtlas.showoffbuttondown));
		showOffButton.setPosition(showOffButtonX + 300, showOffButtonY);

		// action
		stageClear.addAction(moveTo(stageClearX, stageClearY, 1,
				Interpolation.swing));
		timeScore.addAction(sequence(delay(0.7f),
				moveTo(timeScoreX, timeScoreY, 1, Interpolation.swing),run(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						fontFlag = true;
					}})));
		mainMenuButton
				.addAction(sequence(
						delay(0.7f),
						moveTo(mainMenuButtonX, mainMenuButtonY, 1,
								Interpolation.swing)));
		nextStageButton.addAction(sequence(
				delay(1f),
				moveTo(nextStageButtonX, nextStageButtonY, 1,
						Interpolation.swing)));
		showOffButton.addAction(sequence(delay(1.3f),
				moveTo(showOffButtonX, showOffButtonY, 1, Interpolation.swing),
				run(new Runnable() {
					public void run() {
						
						Gdx.input.setInputProcessor(stage);
					}
				})));

		stage.addActor(stageClear);
		stage.addActor(timeScore);
		stage.addActor(mainMenuButton);
		stage.addActor(nextStageButton);
		stage.addActor(showOffButton);

		mainMenuButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("back to main menu");
				ScreenTransition transition = ScreenTransitionSlide
						.init(1f, ScreenTransitionSlide.UP, true,
								Interpolation.sine);
				game.setScreen(new MenuScreen(game), transition);
			}
		});

		nextStageButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("back to select memu");
				ScreenTransition transition = ScreenTransitionFade.init(1);
				game.setScreen(new SelectScreen(game), transition);
			}
		});

		showOffButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("pop the share menu");
				ShareContent.getContent().share("我在OmniBounce游戏中在"+currentTime+"秒内通过了"+levelNumber+"-"+level+"关啦，只用了"+usedpaddle+"条命，想挑战吗？", ScreenShot.saveScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
				
			}
		});

		stage.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if (keycode == Keys.BACK) {
					ScreenTransition transition = ScreenTransitionFade.init(1);
					game.setScreen(new SelectScreen(game), transition);
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

		if (fontFlag) {
			SpriteBatch batch = (SpriteBatch) stage.getBatch();
			drawNumbers(batch);
		}

		super.render(delta);
	}
}
