package com.omnibounce.interfaces;

public interface ILevelController {
	
	// Event driven
	public void onGetReady();
	public void onGameOver();
	public void onTimeUp();
	public void onGameWin();
	public void onLoseBall(int id);
	public void onCatchBall(int id);
	public void onCatchBalloon();
	public void onDestroyBrick();
	
	// Score control
	public int getCurrentScore();
	public void addScore(int amount);
	public void setScoreMultiplier(float x);
	public int getNumStars();
	
	// Life control
	public int getLives();
	public void die(int id);
	public void newLife();
	public void newBall();
	public boolean isAlive();
}
