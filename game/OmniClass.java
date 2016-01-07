package com.omnibounce.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.omnibounce.assets.Assets;
import com.omnibounce.assets.GamePreferences;
import com.omnibounce.constants.Constants;
import com.omnibounce.maps.KMapManager;
import com.omnibounce.screens.*;
import com.omnibounce.share.IShareContent;
import com.omnibounce.share.ShareContent;
import com.omnibounce.transition.DirectedGame;

public class OmniClass extends DirectedGame {

	private long frameCount;
	
	public OmniClass(IShareContent content) {
		ShareContent.setContent(content);
	}
	
	@Override
	public void create() {
		super.create();

		// environment
		Gdx.app.log("APP", "running on "+Gdx.app.getType().toString()+" (ver. "+Gdx.app.getVersion()+")");
		Gdx.app.log("APP", "create()");
		// load game preferences
		GamePreferences.getInstance().load();
		// load app resources
		Assets.getInstance().init(new AssetManager(), true);
		// build maps
		KMapManager.getInstance().loadBuiltInMaps();
		// enter first screen
//		setScreen(new MenuScreen(this));
		setScreen(new EnterScreen(this));
//		setScreen(new NewGameScreen(this, Constants.level1, false));
	}

	@Override
	public void render() {
		// render the screen
		super.render();
		++frameCount;
	}
	
	@Override
	public void dispose () {
		Gdx.app.log("APP", "dispose()");
		// save game preferences on exit
		GamePreferences.getInstance().dispose();
		super.dispose();
	}
	
	public long getFrameCount() {
		return frameCount;
	}

}
