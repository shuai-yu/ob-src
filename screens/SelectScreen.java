package com.omnibounce.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.omnibounce.assets.Assets;
import com.omnibounce.assets.AudioManager;
import com.omnibounce.assets.GamePreferences;
import com.omnibounce.constants.Constants;
import com.omnibounce.levels.LevelUtils;
import com.omnibounce.transition.AbstractGameScreen;
import com.omnibounce.transition.DirectedGame;
import com.omnibounce.transition.ScreenTransition;
import com.omnibounce.transition.ScreenTransitionSlide;

public class SelectScreen extends AbstractGameScreen {

	private static final int VIRTUAL_WIDTH = 800;
	private static final int VIRTUAL_HEIGHT = 480;
	private static final float ASPECT_RATIO = (float) VIRTUAL_WIDTH
			/ (float) VIRTUAL_HEIGHT;

	private Camera camera;
	private Rectangle viewport;
	
	public Stage stage;

	private static boolean isGravityMode = true;

	private Array<Integer> scoresList;
	private Array<Boolean> isEnableList;
	private Array<Vector2> positions;
	private Array<Image> levelContainer;
	private boolean[] lineStage;

	private Image level1;
	private Image level2a;
	private Image level2b;
	private Image level3a;
	private Image level3b;
	private Image level3c;
	private Image level3d;
	private Image level4a;
	private Image level4b;
	private Image level4c;
	private Image level4d;
	private Image level4e;
	private Image level5a;
	private Image level5b;
	private Image level5c;
	private Image level5d;
	private Image level5e;

	private ImageButton gravityBtn;
	private ImageButton returnBtn;
	
	

	// private Image level5f;

	// private Table table1;
	// private Table table2a;
	// private Table table2b;
	// private Table table3a;
	// private Table table3b;
	// private Table table3c;
	// private Table table3d;
	// private Table table4a;
	// private Table table4b;
	// private Table table4c;
	// private Table table4d;
	// private Table table4e;
	// private Table table5a;
	// private Table table5b;
	// private Table table5c;
	// private Table table5d;
	// private Table table5e;
	// private Table table5f;

	public SelectScreen(DirectedGame game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return stage;
	}

	private void buildLevel() {

		level1 = new Image(Assets.getInstance().assetLevelAtlas.levelRegion1);
		level2a = new Image(Assets.getInstance().assetLevelAtlas.levelRegion2a);
		level2b = new Image(Assets.getInstance().assetLevelAtlas.levelRegion2b);
		level3a = new Image(Assets.getInstance().assetLevelAtlas.levelRegion3a);
		level3b = new Image(Assets.getInstance().assetLevelAtlas.levelRegion3b);
		level3c = new Image(Assets.getInstance().assetLevelAtlas.levelRegion3c);
		level3d = new Image(Assets.getInstance().assetLevelAtlas.levelRegion3d);
		level4a = new Image(Assets.getInstance().assetLevelAtlas.levelRegion4a);
		level4b = new Image(Assets.getInstance().assetLevelAtlas.levelRegion4b);
		level4c = new Image(Assets.getInstance().assetLevelAtlas.levelRegion4c);
		level4d = new Image(Assets.getInstance().assetLevelAtlas.levelRegion4d);
		level4e = new Image(Assets.getInstance().assetLevelAtlas.levelRegion4e);
		level5a = new Image(Assets.getInstance().assetLevelAtlas.levelRegion5a);
		level5b = new Image(Assets.getInstance().assetLevelAtlas.levelRegion5b);
		level5c = new Image(Assets.getInstance().assetLevelAtlas.levelRegion5c);
		level5d = new Image(Assets.getInstance().assetLevelAtlas.levelRegion5d);
		level5e = new Image(Assets.getInstance().assetLevelAtlas.levelRegion5e);
		// add into contaner
		levelContainer.add(level1);
		levelContainer.add(level2a);
		levelContainer.add(level2b);
		levelContainer.add(level3a);
		levelContainer.add(level3b);
		levelContainer.add(level3c);
		levelContainer.add(level3d);
		levelContainer.add(level4a);
		levelContainer.add(level4b);
		levelContainer.add(level4c);
		levelContainer.add(level4d);
		levelContainer.add(level4e);
		levelContainer.add(level5a);
		levelContainer.add(level5b);
		levelContainer.add(level5c);
		levelContainer.add(level5d);
		levelContainer.add(level5e);

		// set position!!
		positions = new Array<Vector2>();
		level1.setPosition(40, 210);
		positions.add(new Vector2(40, 210));
		level2a.setPosition(190, 300);
		positions.add(new Vector2(190, 300));
		level2b.setPosition(190, 120);
		positions.add(new Vector2(190, 120));
		level3a.setPosition(340, 355);
		positions.add(new Vector2(340, 355));
		level3b.setPosition(340, 265);
		positions.add(new Vector2(340, 265));
		level3c.setPosition(340, 175);
		positions.add(new Vector2(340, 175));
		level3d.setPosition(340, 85);
		positions.add(new Vector2(340, 85));
		level4a.setPosition(490, 400);
		positions.add(new Vector2(490, 400));
		level4b.setPosition(490, 310);
		positions.add(new Vector2(490, 310));
		level4c.setPosition(490, 220);
		positions.add(new Vector2(490, 220));
		level4d.setPosition(490, 130);
		positions.add(new Vector2(490, 130));
		level4e.setPosition(490, 40);
		positions.add(new Vector2(490, 40));
		level5a.setPosition(640, 400);
		positions.add(new Vector2(640, 400));
		level5b.setPosition(640, 310);
		positions.add(new Vector2(640, 310));
		level5c.setPosition(640, 220);
		positions.add(new Vector2(640, 220));
		level5d.setPosition(640, 130);
		positions.add(new Vector2(640, 130));
		level5e.setPosition(640, 40);
		positions.add(new Vector2(640, 40));

		// add into stage
		stage.addActor(level1);
		stage.addActor(level2a);
		stage.addActor(level2b);
		stage.addActor(level3a);
		stage.addActor(level3b);
		stage.addActor(level3c);
		stage.addActor(level3d);
		stage.addActor(level4a);
		stage.addActor(level4b);
		stage.addActor(level4c);
		stage.addActor(level4d);
		stage.addActor(level4e);
		stage.addActor(level5a);
		stage.addActor(level5b);
		stage.addActor(level5c);
		stage.addActor(level5d);
		stage.addActor(level5e);

	}

	ShapeRenderer shapeRenderer;
	private float lightAlpha = 1;
	private float darkAlpha = 0.4f;

	float lineMatrix[][] = { { 146, 240, 190, 330 }, // 0
			{ 146, 240, 190, 150 }, // 1
			{ 296, 330, 340, 385 },// 2
			{ 296, 330, 340, 295 },// 3
			{ 296, 150, 340, 205 },// 4
			{ 296, 150, 340, 115 },// 5
			{ 446, 385, 490, 430 },// 6
			{ 446, 385, 490, 340 },// 7
			{ 446, 295, 490, 340 },// 8
			{ 446, 295, 490, 250 },// 9
			{ 446, 205, 490, 250 },// 10
			{ 446, 205, 490, 160 },// 11
			{ 446, 115, 490, 160 },// 12
			{ 446, 115, 490, 70 },// 13
			{ 596, 430, 640, 430 },// 14
			{ 596, 340, 640, 340 },// 15
			{ 596, 250, 640, 250 },// 16
			{ 596, 160, 640, 160 },// 17
			{ 596, 70, 640, 70 } // 18
	};
	
	private void drawLightLine() {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(lightAlpha, lightAlpha, lightAlpha, lightAlpha);
		for (int i = 0; i < lineStage.length; i++) {
			if (lineStage[i])
				shapeRenderer.rectLine(lineMatrix[i][0], lineMatrix[i][1],
						lineMatrix[i][2], lineMatrix[i][3], 4);
		}
		shapeRenderer.end();
	}

	private void drawDarkLine() {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(darkAlpha, darkAlpha, darkAlpha, darkAlpha);
		for (int i = 0; i < lineStage.length; i++) {
			if (!lineStage[i])
				shapeRenderer.rectLine(lineMatrix[i][0], lineMatrix[i][1],
						lineMatrix[i][2], lineMatrix[i][3], 4);
		}
		shapeRenderer.end();
	}

	private void drawLine() {
		
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.begin(ShapeType.Filled);
		// TODO: make some lines gray
		
		// 1 -> 2
		shapeRenderer.rectLine(146, 240, 190, 330, 4);
		shapeRenderer.rectLine(146, 240, 190, 150, 4);

		// 2 -> 3
		shapeRenderer.rectLine(296, 330, 340, 385, 4);
		shapeRenderer.rectLine(296, 330, 340, 295, 4);
		shapeRenderer.rectLine(296, 150, 340, 205, 4);
		shapeRenderer.rectLine(296, 150, 340, 115, 4);
		
		// 3 -> 4
		shapeRenderer.rectLine(446, 385, 490, 430, 4);
		shapeRenderer.rectLine(446, 385, 490, 340, 4);
		shapeRenderer.rectLine(446, 295, 490, 340, 4);
		shapeRenderer.rectLine(446, 295, 490, 250, 4);
		shapeRenderer.rectLine(446, 205, 490, 250, 4);
		shapeRenderer.rectLine(446, 205, 490, 160, 4);
		shapeRenderer.rectLine(446, 115, 490, 160, 4);
		shapeRenderer.rectLine(446, 115, 490, 70, 4);

		// 3 -> 5
		shapeRenderer.rectLine(596, 430, 640, 430, 4);
		shapeRenderer.rectLine(596, 340, 640, 340, 4);
		shapeRenderer.rectLine(596, 250, 640, 250, 4);
		shapeRenderer.rectLine(596, 160, 640, 160, 4);
		shapeRenderer.rectLine(596, 70, 640, 70, 4);

		shapeRenderer.end();

	}

	private void setLevelListener() {
		for (int i = 0; i < levelContainer.size; i++) {
			final int ii = i+1;
			levelContainer.get(i).addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					System.out.println("index: " + this.getClass().getName()
							+ " clicked");
					
					ScreenTransition transition = ScreenTransitionSlide.init(0.75f,
			                ScreenTransitionSlide.DOWN, false, Interpolation.bounceOut);
					
					System.out.println("mode: "+(isGravityMode?"Gravity":"Touch"));
					
					// TODO: setScreen to GameScreen
			         game.setScreen(new NewGameScreen(game, LevelUtils.levelNumToName(ii), isGravityMode), transition);

					return super.touchDown(event, x, y, pointer, button);
				}
			});
		}
	}

	private TextureRegion[][] regDigits;

	protected void displayNumber(Batch batch, int startX, int startY, int x,
			int digits) {
		while ((digits--) > 0 || x > 0) {
			int tmp = x % 10;
			x /= 10;
			TextureRegion r = regDigits[0][(tmp >= 1) ? (tmp - 1) : 9];
			startX -= r.getRegionWidth();
			batch.draw(r, startX, startY);
		}
	}

	private void setLevelEnable() {
		for (int i = 0; i < levelContainer.size; i++) {
			if (isEnableList.get(i)) {
				levelContainer.get(i).setTouchable(Touchable.enabled);
				levelContainer.get(i).setColor(1, 1, 1, 1f);
			} else {
				levelContainer.get(i).setTouchable(Touchable.disabled);
				levelContainer.get(i).setColor(1, 1, 1, 0.4f);
			}
		}
		levelContainer.get(0).setTouchable(Touchable.enabled);
		levelContainer.get(0).setColor(1, 1, 1, 1f);
	}
	
	private void setLineEnable() {
		lineStage = new boolean[Constants.LINES_SUM_NUMBER];

		// level 1-2
		if (isEnableList.get(1)) {
			lineStage[0] = true;
			// level 2-3
			if (isEnableList.get(3)) {
				lineStage[2] = true;
				if (isEnableList.get(7)) {
					lineStage[6] = true;
					if (isEnableList.get(12)) {
						lineStage[14] = true;
					}
				}
				if (isEnableList.get(8)) {
					lineStage[7] = true;
					if (isEnableList.get(13)) {
						lineStage[15] = true;
					}
				}
			}
			// level 2-3
			if (isEnableList.get(4)) {
				lineStage[3] = true;
				if (isEnableList.get(8)) {
					lineStage[8] = true;
					if (isEnableList.get(13)) {
						lineStage[15] = true;
					}
				}
				if (isEnableList.get(9)) {
					lineStage[9] = true;
					if (isEnableList.get(14)) {
						lineStage[16] = true;
					}
				}
			}
		}
		// level 1-2
		if (isEnableList.get(2)) {
			lineStage[1] = true;
			// level 2-3
			if (isEnableList.get(5)) {
				lineStage[4] = true;
				if (isEnableList.get(9)) {
					lineStage[10] = true;
					if (isEnableList.get(14)) {
						lineStage[16] = true;
					}
				}
				if (isEnableList.get(10)) {
					lineStage[11] = true;
					if (isEnableList.get(15)) {
						lineStage[17] = true;
					}
				}
			}
			// level 2-3
			if (isEnableList.get(6)) {
				lineStage[5] = true;
				if (isEnableList.get(10)) {
					lineStage[12] = true;
					if (isEnableList.get(15)) {
						lineStage[17] = true;
					}
				}
				if (isEnableList.get(11)) {
					lineStage[13] = true;
					if (isEnableList.get(16)) {
						lineStage[18] = true;
					}
				}
			}
		}
	}

	SpriteBatch batch;

	private void drawNumbers() {

		batch.begin();

		for (int i = 0; i < positions.size; i++) {
			Vector2 v = positions.get(i);
			int s = scoresList.get(i);
			displayNumber(batch, (int) v.x + 6 + 5 * 18, (int) v.y - 20,
					(int) scoresList.get(i), 5);
		}

		batch.end();
	}

	private void regetScoresList() {
		scoresList = GamePreferences.getInstance()
				.getScoresList(isGravityMode);

		isEnableList = GamePreferences.getInstance().getStageOpenStageList();
	}

	private void setGrivateBtn() {
		isGravityMode = !isGravityMode;
		if (isGravityMode) {
			gravityBtn.getStyle().imageUp = new TextureRegionDrawable(Assets.getInstance().assetLevelAtlas.gravitybtnRegion);
			gravityBtn.getStyle().imageDown = new TextureRegionDrawable(Assets.getInstance().assetLevelAtlas.graDownRegion);
		} else {
			gravityBtn.getStyle().imageUp = new TextureRegionDrawable(Assets.getInstance().assetLevelAtlas.touchbtnRegion);
			gravityBtn.getStyle().imageDown = new TextureRegionDrawable(Assets.getInstance().assetLevelAtlas.touchDownRegion);
		}
		regetScoresList();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

		
		
		regDigits = Assets.getInstance().mateGame.get("digit").split(18, 15);

		regetScoresList();

		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
		stage = new Stage(new ScalingViewport(Scaling.fill, 800, 480, camera));
		
		shapeRenderer = new ShapeRenderer();
		// batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		stage.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.BACK) {

					MenuScreen menuScreen = new MenuScreen(game);
					ScreenTransition transition = ScreenTransitionSlide.init(1.25f,
							ScreenTransitionSlide.UP, true,
							Interpolation.sine);
					game.setScreen(menuScreen, transition);
					return true;
				}
				return super.keyDown(event, keycode);
			}
		});
		
		Gdx.input.setCatchBackKey(true);
		
		returnBtn = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetAtlas.mainmenubutton),new TextureRegionDrawable(Assets.getInstance().assetAtlas.mainmenubuttondown));
		returnBtn.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				MenuScreen menuScreen = new MenuScreen(game);
				ScreenTransition transition = ScreenTransitionSlide.init(1.25f,
						ScreenTransitionSlide.UP, true,
						Interpolation.sine);
				game.setScreen(menuScreen, transition);
			}
		});
		returnBtn.setPosition(50, 40);
		stage.addActor(returnBtn);

		if (isGravityMode) {
			gravityBtn = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetLevelAtlas.gravitybtnRegion),new TextureRegionDrawable(Assets.getInstance().assetLevelAtlas.graDownRegion));
		}else {
			gravityBtn = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetLevelAtlas.touchbtnRegion),new TextureRegionDrawable(Assets.getInstance().assetLevelAtlas.touchDownRegion));
		}
		gravityBtn.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				setGrivateBtn();
			}
		});
		gravityBtn.setPosition(50, 400);
		stage.addActor(gravityBtn);

		levelContainer = new Array<Image>();
		
		// 1
		buildLevel();
		// 2
		setLevelListener();
		setLevelEnable();
		setLineEnable();

		AudioManager.getInstance().play(
				Assets.getInstance().music.bgm_levels);
		
		super.show();
	}

	private void resetAllObject() {
		regetScoresList();
		buildLevel();
		// 2
		setLevelListener();
		setLevelEnable();
		setLineEnable();
	}

	private Table buildBackGroundLayer() {
		Table layer = new Table();
		Image image = new Image(Assets.getInstance().assetAtlas.bg);
		layer.add(image);
		return layer;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

		Gdx.gl.glViewport((int) viewport.x, (int) viewport.y,
				(int) viewport.width, (int) viewport.height);
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
		batch = (SpriteBatch)stage.getBatch();
		
		// line
//		drawLine();
		drawLightLine();
		drawDarkLine();
		
		//number
		drawNumbers();

		super.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		float aspectRatio = (float) width / (float) height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);

		if (aspectRatio > ASPECT_RATIO) {
			scale = (float) height / (float) VIRTUAL_HEIGHT;
			crop.x = (width - VIRTUAL_WIDTH * scale) / 2f;
		} else if (aspectRatio < ASPECT_RATIO) {
			scale = (float) width / (float) VIRTUAL_WIDTH;
			crop.y = (height - VIRTUAL_HEIGHT * scale) / 2f;
		} else {
			scale = (float) width / (float) VIRTUAL_WIDTH;
		}

		float w = (float) VIRTUAL_WIDTH * scale;
		float h = (float) VIRTUAL_HEIGHT * scale;
		viewport = new Rectangle(crop.x, crop.y, w, h);
		
		super.resize(width, height);
	}

	private void setEveryLevelListener() {
		level1.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level2a.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level2b.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level3a.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level3b.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level3c.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level3d.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level4a.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level4b.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level4c.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level4d.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level4e.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level5a.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level5b.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level5c.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level5d.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		level5e.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}

}
