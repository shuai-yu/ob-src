package com.omnibounce.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.rotateTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.touchable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.omnibounce.assets.Assets;
import com.omnibounce.assets.AudioManager;
import com.omnibounce.assets.GamePreferences;
import com.omnibounce.constants.Constants;
import com.omnibounce.objects.BackgroundBall;
import com.omnibounce.objects.EffectActor;
import com.omnibounce.objects.ElectronicActors;
import com.omnibounce.stages.BasicStage;
import com.omnibounce.transition.AbstractGameScreen;
import com.omnibounce.transition.DirectedGame;
import com.omnibounce.transition.ScreenTransition;
import com.omnibounce.transition.ScreenTransitionSlide;

public class MenuScreen extends AbstractGameScreen {

	private Stage stage;

	private boolean debugEnabled = false;

	private BackgroundBall backgroundBall;

	private TextButton btnWinOptSave;
	private TextButton btnWinOptCancel;
	private CheckBox chkSound;
	private Slider sldSound;
	private CheckBox chkMusic;
	private Slider sldMusic;

	private Window winOptions;
	private Skin skinLibgdx;
//	private Skin skinCanyonBunny;

	// private Button btnMenuPlay;
	// private Button btnMenuOptions;
	// private Button btnMenuExit;
	// private Button btnMenuAbout;

	private ImageButton btnMenuExit;
	private ImageButton btnMenuPlay;
	private ImageButton btnMenuOptions;
	private ImageButton btnMenuAbout;
	private ImageButton btnMenuHelp;

	public MenuScreen(DirectedGame game) {
		super(game);
		// TODO Auto-generated constructor stub
		Gdx.input.setCatchBackKey(false);
	}

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return stage;
	}

	private Stack stack;
	private Table layerOptionsWindow;

	private void buildStage() {
		// addbackground

		skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),
				new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));
//		skinCanyonBunny = new Skin(
//				Gdx.files.internal(Constants.SKIN_CANYONBUNNY_UI),
//				new TextureAtlas(Constants.TEXTURE_ATLAS_UI));

		stage.clear();
		stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT);

		Table layerBackground = buildBackgroundLayer();
		
		
		
		
		Table layerObjects = buildObjectsLayer();
		Table layerLogos = buildLogosLayer();

		Table layerControls = buildControlsLayer();
		Table layerLeft = bulidLeftLayer();
		layerOptionsWindow = buildOptionsWindowLayer();

		layerOptionsWindow.setVisible(false);

		hideMenuButtons();

		// add into stage

		stack.add(layerBackground);

		stack.add(layerObjects);
		// stack.add(buildBackgroundSprites());
		stack.add(layerLogos);
		stack.add(layerControls);
		stack.add(layerLeft);

		// stage.addActor(buildBackgroundSprites());
		stage.addActor(layerOptionsWindow);
	}

	private Table buildOptionsWindowLayer2() {
		winOptions = new Window("opration", skinLibgdx);
		winOptions.sizeBy(350, 200);
		winOptions.setPosition(Constants.VIEW_WIDTH / 2 - winOptions.getWidth()
				/ 2, Constants.VIEW_HEIGHT / 2 - winOptions.getHeight() / 2);

		Label audioLabel = new Label("Audio", skinLibgdx, "default-font",
				Color.ORANGE);
		winOptions.add(audioLabel);
		audioLabel.setPosition(1, 1);
		if (debugEnabled)
			winOptions.debug();

		return winOptions;
	}

	private Table buildOptionsWindowLayer() {

		winOptions = new Window("Options", skinLibgdx);

		winOptions.row().size(400, 250);
		// + Audio Settings: Sound/Music CheckBox and Volume Slider
		winOptions.add(buildOptWinAudioSettings()).row();
		// + Separator and Buttons (Save, Cancel)
		winOptions.add(buildOptWinButtons()).pad(10, 0, 10, 0);
		// Make options window slightly transparent
		winOptions.setColor(1, 1, 1, 0.8f);
		// Hide options window by default
		// winOptions.setVisible(false);
		showOptionsWindow(false, false);

		if (debugEnabled)
			winOptions.debug();
		// Let TableLayout recalculate widget sizes and positions
		winOptions.pack();
		// Move options window to bottom right corner
		winOptions.setPosition(Constants.VIEW_WIDTH / 2 - winOptions.getWidth()
				/ 2, Constants.VIEW_HEIGHT / 2 - winOptions.getHeight() / 2);
		return winOptions;
	}

	private Table buildOptWinAudioSettings() {
		Table tbl = new Table();

		// + Title: "Audio"
		tbl.pad(10, 10, 0, 10).top();
		tbl.add(new Label("Audio", skinLibgdx, "default-font", Color.ORANGE))
				.colspan(3);
		tbl.row().pad(50, 10, 20, 10);
		tbl.columnDefaults(0).padRight(10);
		tbl.columnDefaults(1).padRight(10);
		// + Checkbox, "Sound" label, sound volume slider
		chkSound = new CheckBox("", skinLibgdx);
		tbl.add(chkSound);
		tbl.add(new Label("Sound", skinLibgdx));
		sldSound = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
		tbl.add(sldSound);
		tbl.row().pad(30, 10, 10, 10);
		// + Checkbox, "Music" label, music volume slider
		chkMusic = new CheckBox("", skinLibgdx);
		tbl.add(chkMusic);
		tbl.add(new Label("Music", skinLibgdx));
		sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
		tbl.add(sldMusic);
		tbl.row();
		return tbl;
	}

	private void showOptionsWindow(boolean visible, boolean animated) {
		float alphaTo = visible ? 0.8f : 0.0f;
		float duration = animated ? 1.0f : 0.0f;
		Touchable touchEnabled = visible ? Touchable.enabled
				: Touchable.disabled;
		winOptions.addAction(sequence(touchable(touchEnabled),
				alpha(alphaTo, duration)));
	}

	private void hideMenuButtons() {
		btnMenuAbout.setVisible(false);
		btnMenuHelp.setVisible(false);
		btnMenuPlay.setVisible(false);
		btnMenuOptions.setVisible(false);
		btnMenuExit.setVisible(false);
		btnMenuAbout.addAction(moveBy(-300, 0, 0.001f));
		btnMenuHelp.addAction(moveBy(-300, 0, 0.001f));
		btnMenuPlay.addAction(moveBy(300, 0, 0.001f));
		btnMenuOptions.addAction(moveBy(300, 0, 0.001f));
		btnMenuExit.addAction(moveBy(300, 0, 0.001f));
	}

	private void preShowMenuButtons() {
		btnMenuAbout.setVisible(true);
		btnMenuHelp.setVisible(true);
		btnMenuPlay.setVisible(true);
		btnMenuOptions.setVisible(true);
		btnMenuExit.setVisible(true);
	}

	private void showMenuButtons(boolean visible) {
		float moveDuration = 1.0f;
		Interpolation moveEasing = Interpolation.swing;
		float delayOptionsButton = 0.25f;
		float moveX = 300 * (visible ? -1 : 1);
		float moveY = 0 * (visible ? -1 : 1);
		final Touchable touchEnabled = visible ? Touchable.enabled
				: Touchable.disabled;
		btnMenuPlay.addAction(moveBy(moveX, moveY, moveDuration, moveEasing));
		btnMenuOptions.addAction(sequence(delay(delayOptionsButton),
				moveBy(moveX, moveY, moveDuration, moveEasing)));
		btnMenuExit.addAction(sequence(delay(delayOptionsButton * 2),
				moveBy(moveX, moveY, moveDuration, moveEasing)));
		btnMenuAbout.addAction(sequence(delay(delayOptionsButton * 2),
				moveBy(-moveX, moveY, moveDuration, moveEasing)));
		btnMenuHelp.addAction(sequence(delay(delayOptionsButton),
				moveBy(-moveX, moveY, moveDuration, moveEasing)));

		SequenceAction seq = sequence();
		if (visible)
			seq.addAction(delay(delayOptionsButton + moveDuration));
		seq.addAction(run(new Runnable() {
			public void run() {
				btnMenuPlay.setTouchable(touchEnabled);
				btnMenuOptions.setTouchable(touchEnabled);
				btnMenuExit.setTouchable(touchEnabled);
			}
		}));
		stage.addAction(seq);
	}

	private Table buildOptWinButtons() {
		Table tbl = new Table();
		// + Separator
		Label lbl = null;
		lbl = new Label("", skinLibgdx);
		lbl.setColor(0.75f, 0.75f, 0.75f, 1);
		lbl.setStyle(new LabelStyle(lbl.getStyle()));
		lbl.getStyle().background = skinLibgdx.newDrawable("white");
		tbl.add(lbl).colspan(2).height(1).width(400).pad(0, 0, 0, 1);
		tbl.row();
		lbl = new Label("", skinLibgdx);
		lbl.setColor(0.5f, 0.5f, 0.5f, 1);
		lbl.setStyle(new LabelStyle(lbl.getStyle()));
		lbl.getStyle().background = skinLibgdx.newDrawable("white");
		tbl.add(lbl).colspan(2).height(1).width(400).pad(0, 1, 5, 0);
		tbl.row();
		// + Save Button with event handler
		btnWinOptSave = new TextButton("Save", skinLibgdx);
		tbl.add(btnWinOptSave).padRight(50);

		btnWinOptSave.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onSaveClicked();
			}
		});
		// + Cancel Button with event handler
		btnWinOptCancel = new TextButton("Cancel", skinLibgdx);
		tbl.add(btnWinOptCancel);
		btnWinOptCancel.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onCancelClicked();
			}
		});
		return tbl;
	}

	private void onSaveClicked() {
		saveSettings();
		onCancelClicked();
		AudioManager.getInstance().onSettingsUpdated();
	}

	private void onCancelClicked() {
		showMenuButtons(true);
		showOptionsWindow(false, true);
		AudioManager.getInstance().onSettingsUpdated();
	}

	private void saveSettings() {
		GamePreferences prefs = GamePreferences.getInstance();
		prefs.soundOn = chkSound.isChecked();
		prefs.soundVol = sldSound.getValue();
		prefs.musicOn = chkMusic.isChecked();
		prefs.musicVol = sldMusic.getValue();
		prefs.save();
	}

	private Image imgBackground;

	private Table buildBackgroundLayer() {
		Table layer = new Table();
		layer.setWidth(800);
		layer.setHeight(480);
		// + Background
		// imgBackground = new Image(skinCanyonBunny, "background");
		// layer.add(imgBackground);
//		imgBackground = new Image(Assets.getInstance().assetAtlas.bg);
//		layer.add(imgBackground);
		layer.setBackground(new TextureRegionDrawable(Assets.getInstance().assetAtlas.bg));
		
		
		if (debugEnabled)
			layer.debug();

		return layer;
	}

	private Image logoImage;

	private Table buildLogosLayer() {
		Table layer = new Table();
		layer.left().top().pad(100, 30, 0, 0);
		// + Game Logo
		logoImage = new Image(Assets.getInstance().assetAtlas.logo);
		layer.add(logoImage);
		if (debugEnabled)
			layer.debug();
		return layer;
	}

	private Table buildControlsLayer() {
		Table layer = new Table();
		layer.right().bottom().pad(20, 20, 20, 20);
		// + Play Button
		// btnMenuPlay = new Button(new draw, down)
		// checked);
		btnMenuPlay = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetAtlas.start), new TextureRegionDrawable(Assets.getInstance().assetAtlas.startdown));
		layer.add(btnMenuPlay);
		btnMenuPlay.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onPlayClicked();
			}
		});

		layer.row().pad(20, 20, 20, 20);

		// + Options Button
		// btnMenuOptions = new Button(skinCanyonBunny, "options");
		btnMenuOptions = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetAtlas.options), new TextureRegionDrawable(Assets.getInstance().assetAtlas.optionsdown));
		layer.add(btnMenuOptions);

		btnMenuOptions.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onOptionsClicked();
			}
		});

		layer.row().pad(20, 20, 20, 20);
		// btnMenuExit = new Button(skinCanyonBunny, "options");
		btnMenuExit = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetAtlas.exit), new TextureRegionDrawable(Assets.getInstance().assetAtlas.exitdown));

		layer.add(btnMenuExit);
		btnMenuExit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				onExitClicked();
			}
		});

		if (debugEnabled)
			layer.debug();

		return layer;
	}

	private Table bulidLeftLayer() {
		Table layer = new Table();

		layer.left().bottom().pad(20, 20, 20, 20);

		btnMenuHelp = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetAtlas.help), new TextureRegionDrawable(Assets.getInstance().assetAtlas.helpdown));
		layer.add(btnMenuHelp);
		btnMenuHelp.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				onHelpClicked();
			}
		});
		
		layer.row().pad(20, 20, 20, 20);

		btnMenuAbout = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetAtlas.credits), new TextureRegionDrawable(Assets.getInstance().assetAtlas.creditsdown));
		layer.add(btnMenuAbout);
		btnMenuAbout.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				onAboutClicked();
			}
		});

		if (debugEnabled)
			layer.debug();
		return layer;
	}

	private void onPlayClicked() {

		SelectScreen screen = new SelectScreen(game);

		ScreenTransition transition = ScreenTransitionSlide.init(0.75f,
				ScreenTransitionSlide.DOWN, false, Interpolation.sine);

		game.setScreen(screen, transition);
	}

	private void onOptionsClicked() {
		layerOptionsWindow.setVisible(true);

		loadSettings();
		showMenuButtons(false);
		showOptionsWindow(true, true);
	}

	private void onExitClicked() {
		Gdx.app.exit();
	}

	private void onAboutClicked() {
		ScreenTransition transition = ScreenTransitionSlide.init(1f,
				ScreenTransitionSlide.LEFT, false, Interpolation.sine);
		game.setScreen(new CreditsScreen(game), transition);

	}

	private void onHelpClicked() {
		ScreenTransition transition = ScreenTransitionSlide.init(1f,
				ScreenTransitionSlide.LEFT, false, Interpolation.sine);
		game.setScreen(new HelpScreen(game), transition);

	}

	private void loadSettings() {
		GamePreferences prefs = GamePreferences.getInstance();
		prefs.load();
		chkSound.setChecked(prefs.soundOn);
		sldSound.setValue(prefs.soundVol);
		chkMusic.setChecked(prefs.musicOn);
		sldMusic.setValue(prefs.musicVol);
	}

	private Table buildObjectsLayer() {
		Table layer = new Table();
		layer.right();

		backgroundBall = new BackgroundBall(
				Assets.getInstance().mateGame.get("gear"));
		backgroundBall.setVisible(false);
		backgroundBall.setPosition(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT
				/ 2 - backgroundBall.getHeight() / 2);
		backgroundBall.setOrigin(backgroundBall.getWidth() / 2,
				backgroundBall.getHeight() / 2);

		backgroundBall.addAction(parallel(sequence(alpha(1),run(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				backgroundBall.setVisible(true);
			}})),
				moveTo(Constants.VIEW_WIDTH - backgroundBall.getWidth() / 2,
						Constants.VIEW_HEIGHT / 2 - backgroundBall.getHeight()
								/ 2, 1, Interpolation.swing),
				delay(0.3f, run(new Runnable() {
					public void run() {
						showMenuButtons(true);
						preShowMenuButtons();
					}
				})), forever(sequence(rotateTo(360, 5), run(new Runnable() {
					public void run() {
						backgroundBall.setRotation(0);
					}
				})))));

		layer.add(backgroundBall);

		if (debugEnabled)
			layer.debug();
		return layer;
	}

	private float debugRebuildStage;
	private final float DEBUG_REBUILD_INTERVAL = 5.0f;

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		
		
		if (debugEnabled) {
			debugRebuildStage -= delta;
			if (debugRebuildStage <= 0) {
				debugRebuildStage = DEBUG_REBUILD_INTERVAL;
				buildStage();
			}
		}

		stage.act(delta);
		stage.draw();
		
		SpriteBatch batch = (SpriteBatch)stage.getBatch();
		
		batch.begin();
		actors.act(delta);
		actors.draw(batch);
		batch.end();
		super.render(delta);
	}

	@Override
	public void resize(int width, int height) {
	}
	
	
	private ElectronicActors actors;
//	EffectActor effectActor;
	@Override
	public void show() {
		stage = new BasicStage();
		
	
		
		buildStage();
		
		
		
		
//		ParticleEffect effect = new ParticleEffect();
//		effect.load(Gdx.files.internal("data/a.p"), Gdx.files.internal("data/"));
//
//		ParticleEffectPool pool = new ParticleEffectPool(effect, 500, 1000);
//		effectActor = new EffectActor(pool);
//
//		// actor1
//		float pX1 = 0, pY1 = 340;
//		float pX2 = 100, pY2 = 440;
//		float pX3 = 200, pY3 = 440;
//		float pX4 = 300, pY4 = 240;
//		float pX5 = 500, pY5 = 240;
//		effectActor.setPosition(pX1, pY1);
//		effectActor.addAction(forever(sequence(moveTo(pX2, pY2, 2),
//				moveTo(pX3, pY3, 2), moveTo(pX4, pY4, 2), moveTo(pX5, pY5, 2),
//				moveTo(pX1, pY1))));
//		stage.addActor(effectActor);
		
		
		actors =new ElectronicActors(stage);
		actors.create();
		
		
		GamePreferences.getInstance().load();
		
		AudioManager.getInstance().play(
				Assets.getInstance().music.bgm_main);
	}

	
	
	
}
