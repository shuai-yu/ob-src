package com.omnibounce.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.omnibounce.constants.Constants;

public final class Assets implements Disposable, AssetErrorListener {

	private static Assets instance;
	private AssetManager assetManager;
	
	// background worker
	private Thread bgWorker;
	private volatile boolean mainThreadReady;
	private volatile boolean workerThreadDone;
	private volatile boolean fullyLoaded;
	
	// my assets
	public AssetFonts fonts;

	public AssetSounds sounds;
	public AssetMusic music;

	public AssetAtlas assetAtlas;
	public AssetLevelAtlas assetLevelAtlas;
	public AssetItemAtlas items;
	public AssetObjectAtlas objs;

	// material (will be removed in next version)
	public KMaterial mateGame = null;
	
	private Assets() {
	}
	
	public static synchronized Assets getInstance() {
		if (instance == null) {
			instance = new Assets();
		}
		return instance;
	}

	public void init(AssetManager assetManager, boolean nonblock) {
		this.assetManager = assetManager;
		// set asset manager error handler
		assetManager.setErrorListener(this);
		
		long loadTime;
		// load texture atlas
		loadTime = TimeUtils.millis();
		Gdx.app.log("ASSET", "loading atlas");
		assetManager.load(Constants.MENU_TEXTURE_ATLAS, TextureAtlas.class);
		assetManager.load(Constants.LEVEL_TEXTURE_ATLAS, TextureAtlas.class);
		assetManager.load(Constants.ITEM_TEXTURE_ATLAS, TextureAtlas.class);
		assetManager.load(Constants.OBJECT_TEXTURE_ATLAS, TextureAtlas.class);
		assetManager.finishLoading();
		Gdx.app.log("ASSET", "done in "+TimeUtils.timeSinceMillis(loadTime)+" ms");
		
		// load sounds
		loadTime = TimeUtils.millis();
		Gdx.app.log("ASSET", "loading sounds");
		assetManager.load("soundfx/se_maoudamashii_system49.wav", Sound.class);
		assetManager.load("soundfx/se_maoudamashii_system20.wav", Sound.class);
		assetManager.load("soundfx/se_maoudamashii_system21.wav", Sound.class);
		assetManager.load("soundfx/se_maoudamashii_system26.wav", Sound.class);
		assetManager.load("soundfx/se_maoudamashii_system41.wav", Sound.class);
		assetManager.load("soundfx/se_maoudamashii_system23.wav", Sound.class);
		assetManager.load("soundfx/se_maoudamashii_system19.wav", Sound.class);
		assetManager.finishLoading();
		Gdx.app.log("ASSET", "done in "+TimeUtils.timeSinceMillis(loadTime)+" ms");
		
		// load music
		loadTime = TimeUtils.millis();
		Gdx.app.log("ASSET", "loading open music");
//		assetManager.load("bgm/bgm_maoudamashii_cyber10.mp3", Music.class); // main
		assetManager.load("bgm/bgm_maoudamashii_cyber07.mp3", Music.class); // levels
		assetManager.finishLoading();
		Gdx.app.log("ASSET", "done in "+TimeUtils.timeSinceMillis(loadTime)+" ms");
		
		if (nonblock) {
			// run in background
			bgWorker = new Thread(new BackgroundLoadThread());
			bgWorker.start();
		}else {
			loadOtherAssets();
		}
		
		// get loaded resources
		
		// fonts
		// fonts = new AssetFonts();

		// atlas
		assetAtlas = new AssetAtlas((TextureAtlas)assetManager.get(Constants.MENU_TEXTURE_ATLAS));
		assetLevelAtlas = new AssetLevelAtlas((TextureAtlas)assetManager.get(Constants.LEVEL_TEXTURE_ATLAS));
		items = new AssetItemAtlas((TextureAtlas)assetManager.get(Constants.ITEM_TEXTURE_ATLAS));
		objs = new AssetObjectAtlas((TextureAtlas)assetManager.get(Constants.OBJECT_TEXTURE_ATLAS));
		
		// music
		sounds = new AssetSounds(assetManager);
		music = new AssetMusic(assetManager);

		// texture
		mateGame = new KMaterial();
		loadGameTexture2(new Texture("gamematerial.png"));
		loadScreenTexture(new Texture("still_bg.png"));
		mateGame.add("gear", new TextureRegion(new Texture("gear.png")));
		
		mainThreadReady = true;
		if (!nonblock) {
			delayInit();
		}
	}
	
	private class BackgroundLoadThread implements Runnable {
		
		@Override
		public void run() {
			loadOtherAssets();
		}
		
	}
	
	private void loadOtherAssets() {
		Gdx.app.log("ASSETW", "loading all music");
		long loadTime = TimeUtils.millis();
		assetManager.load("bgm/bgm_maoudamashii_cyber04.mp3", Music.class);
		assetManager.load("bgm/bgm_maoudamashii_cyber09.mp3", Music.class);
		assetManager.load("bgm/bgm_maoudamashii_cyber08.mp3", Music.class);
		assetManager.load("bgm/bgm_maoudamashii_cyber06.mp3", Music.class);
		assetManager.load("bgm/bgm_maoudamashii_cyber01.mp3", Music.class);
		assetManager.finishLoading();
		
//		
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		workerThreadDone = true;
		Gdx.app.log("ASSETW", "done in "+TimeUtils.timeSinceMillis(loadTime)+" ms");
	}
	
	public synchronized void delayInit() {
		if (fullyLoaded) return;
		
		while (!workerThreadDone || !mainThreadReady) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		// make sure that all assets have been loaded
		assetManager.finishLoading();
		
		music.loadTheRest(this.assetManager);
		fullyLoaded = true;
	}
	
	public boolean isLoadedCompletely() {
		return fullyLoaded;
	}

	private void loadGameTexture2(Texture img) {
		img.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		mateGame.add("background", new TextureRegion(img, 0, 0, 800, 480));
		mateGame.add("shield_edge", new TextureRegion(img, 500, 490, 450, 450));
		mateGame.add("digit", new TextureRegion(img, 460, 950, 180, 15));
//		
//		mateGame.add("btn_mainmenu", new TextureRegion(img, 830, 120, 192, 41));
//		mateGame.add("btn_gravity", new TextureRegion(img, 460, 975, 250, 41));
//		mateGame.add("btn_touch", new TextureRegion(img, 0, 975, 250, 41));
	}
	
	private void loadScreenTexture(Texture img) {
		img.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		mateGame.add("screen_win", new TextureRegion(img, 0, 0, 800, 480));
		mateGame.add("screen_pause", new TextureRegion(img, 0, 480, 800, 480));
		mateGame.add("screen_lose", new TextureRegion(img, 0, 960, 800, 480));
		mateGame.add("screen_ready", new TextureRegion(img, 0, 1440, 800, 480));
	}
	
	@Override
	public void error(@SuppressWarnings("rawtypes") AssetDescriptor asset, Throwable throwable) {
		// TODO Auto-generated method stub
		Gdx.app.error("ASSET", "Couldn't load asset '" + asset.fileName + "'",
				(Exception) throwable);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		assetManager.dispose();
		// fonts dispose
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
		// texture
		mateGame.dispose();
	}

	public static class AssetFonts {
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;

		public AssetFonts() {
			// create three fonts using Libgdx's 15px bitmap font
			defaultSmall = new BitmapFont(
					Gdx.files.internal("images/arial-15.fnt"), true);
			defaultNormal = new BitmapFont(
					Gdx.files.internal("images/arial-15.fnt"), true);
			defaultBig = new BitmapFont(
					Gdx.files.internal("images/arial-15.fnt"), true);
			// set font sizes
			defaultSmall.setScale(0.75f);
			defaultNormal.setScale(1.0f);
			defaultBig.setScale(2.0f);
			// enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture()
					.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}

	public static class AssetSounds {
		public final Sound fx_ok;
		public final Sound fx_cancel;
		public final Sound fx_hitbrick;
		public final Sound fx_hitwall;
		public final Sound fx_hitpaddle;
		public final Sound fx_getpositem;
		public final Sound fx_getnegitem;

		public AssetSounds(AssetManager am) {
			fx_ok = am.get("soundfx/se_maoudamashii_system49.wav", Sound.class);
			fx_cancel = am.get("soundfx/se_maoudamashii_system20.wav", Sound.class);
			fx_hitbrick = am.get("soundfx/se_maoudamashii_system21.wav", Sound.class);
			fx_hitwall = am.get("soundfx/se_maoudamashii_system26.wav", Sound.class);
			fx_hitpaddle = am.get("soundfx/se_maoudamashii_system41.wav", Sound.class);
			fx_getpositem = am.get("soundfx/se_maoudamashii_system23.wav", Sound.class);
			fx_getnegitem = am.get("soundfx/se_maoudamashii_system19.wav", Sound.class);
		}
	}

	public static class AssetMusic {
		public final Music bgm_main;
		public final Music bgm_levels;
		public final Music bgm_stage[] = new Music[6];


		public AssetMusic(AssetManager am) {
			bgm_main = am.get("bgm/bgm_maoudamashii_cyber07.mp3", Music.class);
			bgm_levels = am.get("bgm/bgm_maoudamashii_cyber07.mp3", Music.class);
		}
		
		public void loadTheRest(AssetManager am) {
			bgm_stage[1] = am.get("bgm/bgm_maoudamashii_cyber04.mp3", Music.class);
			bgm_stage[2] = am.get("bgm/bgm_maoudamashii_cyber09.mp3", Music.class);
			bgm_stage[3] = am.get("bgm/bgm_maoudamashii_cyber08.mp3", Music.class);
			bgm_stage[4] = am.get("bgm/bgm_maoudamashii_cyber06.mp3", Music.class);
			bgm_stage[5] = am.get("bgm/bgm_maoudamashii_cyber01.mp3", Music.class);
		}
	}

	public static class AssetAtlas {
		public final AtlasRegion bg;
		public final AtlasRegion logo;
//		public final AtlasRegion mask;
		public final AtlasRegion options;
		public final AtlasRegion optionsdown;
		public final AtlasRegion start;
		public final AtlasRegion startdown;
		public final AtlasRegion alphafont;
		public final AtlasRegion stageclear;
		public final AtlasRegion timescore;
		

		public final AtlasRegion exit;
		public final AtlasRegion exitdown;
		public final AtlasRegion credits;
		public final AtlasRegion creditsdown;
		public final AtlasRegion help;
		public final AtlasRegion helpdown;
		public final AtlasRegion mainmenubutton;
		public final AtlasRegion mainmenubuttondown;
		public final AtlasRegion nextstagebutton;
		public final AtlasRegion nextstagebuttondown;
		public final AtlasRegion showoffbutton;
		public final AtlasRegion showoffbuttondown;

		public AssetAtlas(TextureAtlas atlas) {
			bg = atlas.findRegion("bg");
			logo = atlas.findRegion("logo");
//			mask = atlas.findRegion("mask");
			options = atlas.findRegion("options");
			optionsdown = atlas.findRegion("optionsdown");
			start = atlas.findRegion("stage");
			startdown = atlas.findRegion("stagedown");
			credits = atlas.findRegion("credits");
			creditsdown = atlas.findRegion("creditsdown");
			help = atlas.findRegion("help");
			helpdown = atlas.findRegion("helpdown");

			alphafont = atlas.findRegion("alphefont");
			stageclear = atlas.findRegion("stageclear");
			timescore = atlas.findRegion("timescore");
			mainmenubutton = atlas.findRegion("mainmenubutton");
			nextstagebutton = atlas.findRegion("nextstagebutton");
			showoffbutton = atlas.findRegion("showoffbutton");
			mainmenubuttondown = atlas.findRegion("mainmenubuttondown");
			nextstagebuttondown = atlas.findRegion("nextstagebuttondown");
			showoffbuttondown = atlas.findRegion("showoffbuttondown");
			
			exit = atlas.findRegion("exit");
			exitdown = atlas.findRegion("exitdown");
		}
	}

	public static class AssetLevelAtlas {
		public final AtlasRegion levelRegion1;
		public final AtlasRegion levelRegion2a;
		public final AtlasRegion levelRegion2b;
		public final AtlasRegion levelRegion3a;
		public final AtlasRegion levelRegion3b;
		public final AtlasRegion levelRegion3c;
		public final AtlasRegion levelRegion3d;
		public final AtlasRegion levelRegion4a;
		public final AtlasRegion levelRegion4b;
		public final AtlasRegion levelRegion4c;
		public final AtlasRegion levelRegion4d;
		public final AtlasRegion levelRegion4e;
		public final AtlasRegion levelRegion5a;
		public final AtlasRegion levelRegion5b;
		public final AtlasRegion levelRegion5c;
		public final AtlasRegion levelRegion5d;
		public final AtlasRegion levelRegion5e;
		public final AtlasRegion creditsRegion;
		public final AtlasRegion helpRegion;
		
		public final AtlasRegion backRegion;
		public final AtlasRegion gravitybtnRegion;
		public final AtlasRegion touchbtnRegion;
		public final AtlasRegion swapbtnRegion;
		
		public final AtlasRegion backDownRegion;
		public final AtlasRegion graDownRegion;
		public final AtlasRegion touchDownRegion;
		public final AtlasRegion swapDownRegion;

		public AssetLevelAtlas(TextureAtlas atlas) {
			levelRegion1 = atlas.findRegion("level1");
			levelRegion2a = atlas.findRegion("level2a");
			levelRegion2b = atlas.findRegion("level2b");
			levelRegion3a = atlas.findRegion("level3a");
			levelRegion3b = atlas.findRegion("level3b");
			levelRegion3c = atlas.findRegion("level3c");
			levelRegion3d = atlas.findRegion("level3d");
			levelRegion4a = atlas.findRegion("level4a");
			levelRegion4b = atlas.findRegion("level4b");
			levelRegion4c = atlas.findRegion("level4c");
			levelRegion4d = atlas.findRegion("level4d");
			levelRegion4e = atlas.findRegion("level4e");
			levelRegion5a = atlas.findRegion("level5a");
			levelRegion5b = atlas.findRegion("level5b");
			levelRegion5c = atlas.findRegion("level5c");
			levelRegion5d = atlas.findRegion("level5d");
			levelRegion5e = atlas.findRegion("level5e");
			creditsRegion = atlas.findRegion("about");
			helpRegion = atlas.findRegion("help");
			backRegion = atlas.findRegion("back");
			gravitybtnRegion = atlas.findRegion("gravity");
			touchbtnRegion = atlas.findRegion("touch");
			swapbtnRegion = atlas.findRegion("swap");
			backDownRegion = atlas.findRegion("backdown");
			graDownRegion = atlas.findRegion("gravitydown");
			touchDownRegion = atlas.findRegion("touchdown");
			swapDownRegion = atlas.findRegion("swapdown");
		}
	}
	
	public static class AssetItemAtlas {
		public final AtlasRegion addLife;
		public final AtlasRegion powerMax;
		public final AtlasRegion expand;
		public final AtlasRegion magnetic;
		public final AtlasRegion powerDown;
		public final AtlasRegion powerUp;
		public final AtlasRegion reverse;
		public final AtlasRegion shield;
		public final AtlasRegion shrink;
		public final AtlasRegion speedDown;
		public final AtlasRegion speedUp;
		public final AtlasRegion split;
		public final AtlasRegion transparent;

		public AssetItemAtlas(TextureAtlas atlas) {
			addLife = atlas.findRegion("1up");
			powerMax = atlas.findRegion("breakthrough");
			expand = atlas.findRegion("expand");
			magnetic = atlas.findRegion("magnet");
			powerDown = atlas.findRegion("powerdown");
			powerUp = atlas.findRegion("powerup");
			reverse = atlas.findRegion("reverse");
			shield = atlas.findRegion("shield");
			shrink = atlas.findRegion("shrink");
			speedDown = atlas.findRegion("speeddown");
			speedUp = atlas.findRegion("speedup");
			split = atlas.findRegion("split");
			transparent = atlas.findRegion("trans");
		}
	}
	
	public static class AssetObjectAtlas {
		public final AtlasRegion ball;
		public final AtlasRegion brick;
		public final AtlasRegion hardBrick;
		public final AtlasRegion shooterBrick;
		public final AtlasRegion blackHoleBrick;
		public final AtlasRegion paddle;
		public final AtlasRegion magneticPaddle;
		public final AtlasRegion hwall;
		public final AtlasRegion vwall;
		public final AtlasRegion bullet;
		

		public AssetObjectAtlas(TextureAtlas atlas) {
			ball = atlas.findRegion("ball");
			brick = atlas.findRegion("brick");
			hardBrick = atlas.findRegion("hardbrick");
			shooterBrick = atlas.findRegion("shooterbrick");
			blackHoleBrick = atlas.findRegion("blackhole");
			paddle = atlas.findRegion("paddle");
			magneticPaddle = atlas.findRegion("magnetpaddle");
			hwall = atlas.findRegion("hwall");
			vwall = atlas.findRegion("vwall");
			bullet = atlas.findRegion("bullet");
		}
	}

}
