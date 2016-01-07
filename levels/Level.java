package com.omnibounce.levels;

import com.omnibounce.interfaces.ILevelConfig;
import com.omnibounce.interfaces.ILevelController;

public abstract class Level implements ILevelController, ILevelConfig {

	private int score;
	private float scoreMul;
	private int lives;
	private int lives2;

	public Level() {
		score = 0;
		scoreMul = 1;
		lives = getMaxLives();
		lives2 = 0;
		assert (lives >= getBallCount());
	}

	public int getRefScore(int star) {
		return star * 10000;
	}

	public int getMaxLives() {
		return 5;
	}

	public int getMaxTime() {
		return 90;
	}

	public int getBallCount() {
		return 1;
	}

	public final boolean isBuiltInBall(int id) {
		return (id < getBallCount());
	}

	public int getPaddleCount() {
		return 2;
	}

	public final int getCurrentScore() {
		return score;
	}

	public final void addScore(int amount) {
		this.score += amount * scoreMul;
	}

	public final void setScoreMultiplier(float x) {
		this.scoreMul = x;
	}

	public final int getNumStars() {
		for (int i = 1; i <= 3; i++)
			if (score < getRefScore(i)) {
				return i - 1;
			}
		return 5;
	}

	public void onLoseBall(int id) {
		die(id);
	}

	public final void die(int id) {
		if (isBuiltInBall(id))
			--lives;
		else
			--lives2;

		if (lives <= 0 && lives2 <= 0)
			onGameOver();
	}

	public final void newLife() {
		++lives;
	}

	public final void newBall() {
		++lives2;
	}

	public final int getLives() {
		return lives;
	}

	public final boolean isAlive() {
		return lives > 0 || lives2 > 0;
	}
}
