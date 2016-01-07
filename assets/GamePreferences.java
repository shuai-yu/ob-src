package com.omnibounce.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.omnibounce.constants.Constants;

public final class GamePreferences implements Disposable {

	private static GamePreferences instance;

	// platform-dependent preferences
	private Preferences prefs;

	// shared access
	public boolean soundOn;
	public boolean musicOn;
	public float soundVol;
	public float musicVol;

	private GamePreferences() {
		Gdx.app.log("PREFMGR", "init");
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
		// for test only
		Gdx.app.log("PREFMGR", "Last run on " + prefs.getLong("LastRun", -1));
		prefs.putLong("LastRun", System.currentTimeMillis());
	}

	public static synchronized GamePreferences getInstance() {
		if (instance == null) {
			instance = new GamePreferences();
		}
		return instance;
	}

	// load/save method
	public void load() {
		Gdx.app.log("PREFMGR", "reloading");
		// load settings from preferences
		soundOn = prefs.getBoolean("SoundOn", true);
		musicOn = prefs.getBoolean("MusicOn", true);
		soundVol = MathUtils.clamp(prefs.getFloat("SoundVolume", 0.5f), 0.0f,
				1.0f);
		musicVol = MathUtils.clamp(prefs.getFloat("MusicVolume", 0.5f), 0.0f,
				1.0f);
	}

	public void save() {
		// save settings to preferences
		prefs.putBoolean("SoundOn", soundOn);
		prefs.putBoolean("MusicOn", musicOn);
		prefs.putFloat("SoundVolume", soundVol);
		prefs.putFloat("MusicVolume", musicVol);
		// flush the buffer
		prefs.flush();
		
		Gdx.app.log("PREFMGR", "saved");
	}

	public void clear() {
		prefs.clear();
	}
	
	public void flush() {
		prefs.flush();
	}

	@Override
	public void dispose() {
		// save changes when being disposed
		save();
		
		Gdx.app.log("PREFMGR", "terminated");
	}

	// game stage state
	public int getScore(String stageName, boolean GravityMode) {
		String name = stageName;
		if (!GravityMode)
			name = "n" + name;

		int score = prefs.getInteger(name, 0);
		Gdx.app.debug("PERFMGR", "getScore(" + name + ") = " + score);
		return score;

	}

	public void saveScore(String stageName, int score, boolean GravityMode) {
		String name = stageName;
		if (!GravityMode)
			name = "n" + name;

		prefs.putInteger(name, score);
		Gdx.app.debug("PERFMGR", "saveScore(" + name + " , " + score + ")");
		prefs.flush();
	}
	
	public int getTime(String stageName, boolean GravityMode) {
		String name = stageName + "T";
		if (!GravityMode)
			name = "n" + name;

		int score = prefs.getInteger(name, 999);
		Gdx.app.debug("PERFMGR", "getTime(" + name + ") = " + score);
		return score;

	}

	public void saveTime(String stageName, int time, boolean GravityMode) {
		String name = stageName + "T";
		if (!GravityMode)
			name = "n" + name;

		prefs.putInteger(name, time);
		Gdx.app.debug("PERFMGR", "saveTime(" + name + " , " + time + ")");
		prefs.flush();
	}

	public void setStageOpen(String stageName, boolean isOpen) {
		String name = "open" + stageName;
		prefs.putBoolean(name, isOpen);
		prefs.flush();
	}

	public boolean getStageOpen(String stageName) {
		String name = "open" + stageName;
		return prefs.getBoolean(name, false);
	}

	public Array<Boolean> getStageOpenStageList() {
		Array<Boolean> list = new Array<Boolean>();
		Array<String> nameList = Constants.getAllLevelList();

		for (int i = 0; i < nameList.size; i++) {
			list.add(getStageOpen(nameList.get(i)));
		}
		return list;
	}

	public Array<Integer> getScoresList(boolean isGravity) {
		Array<Integer> list = new Array<Integer>();
		Array<String> nameList = Constants.getAllLevelList();
		for (int i = 0; i < nameList.size; i++) {
			list.add(getScore(nameList.get(i), isGravity));
		}
		return list;
	}

}
