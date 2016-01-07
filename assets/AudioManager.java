package com.omnibounce.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public final class AudioManager {
	private static AudioManager instance = new AudioManager();
	private Music playingMusic;

	// singleton: prevent instantiation from other classes
	private AudioManager() {
	}

	public static final AudioManager getInstance() {
		if (instance == null)
			instance = new AudioManager();

		return instance;
	}

	public void play(Sound sound) {
		play(sound, 1);
	}

	public void play(Music music) {
		if (music == null) {
			Gdx.app.error("AUDIO", "No such music");
			return;
		}
		stopMusic();
		playingMusic = music;
		if (GamePreferences.getInstance().musicOn) {
			music.setLooping(true);
			music.setVolume(GamePreferences.getInstance().musicVol);
			music.play();
		}
	}
	
	public void resumeMusic() {
		if (playingMusic != null)
			playingMusic.play();
	}
	
	public void pauseMusic() {
		if (playingMusic != null)
			playingMusic.pause();
	}
	
	public void stopMusic() {
		if (playingMusic != null)
			playingMusic.stop();
	}

	public void onSettingsUpdated() {
		if (playingMusic == null)
			return;
		playingMusic.setVolume(GamePreferences.getInstance().musicVol);
		if (GamePreferences.getInstance().musicOn) {
			if (!playingMusic.isPlaying())
				playingMusic.play();
		} else {
			playingMusic.pause();
		}
	}

	public void play(Sound sound, float volume) {
		play(sound, volume, 1);
	}

	public void play(Sound sound, float volume, float pitch) {
		play(sound, volume, pitch, 0);
	}

	public void play(Sound sound, float volume, float pitch, float pan) {
		if (sound == null) {
			Gdx.app.error("AUDIO", "No such sound");
			return;
		}
		if (!GamePreferences.getInstance().soundOn)
			return;
		sound.play(GamePreferences.getInstance().soundVol * volume, pitch, pan);
	}
}
