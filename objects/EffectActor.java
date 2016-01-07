package com.omnibounce.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EffectActor extends Actor {

	private final static int THRESHOLD = 32;

	private ParticleEffectPool particleEffectPool;
	private ArrayList<ParticleEffect> particleEffects;
	// ParticleEffect effect;
	private ParticleEffect tempEffect;
	float x = this.getX();
	float y = this.getY();

	public EffectActor(ParticleEffectPool particleEffectPool) {
		this.particleEffectPool = particleEffectPool;
		particleEffects = new ArrayList<ParticleEffect>();
		lastX = (int) this.getX();
		lastY = (int) this.getY();
	}

	public void draw(SpriteBatch batch, float parentAlpha) {
		// effect.draw(batch); // define behavior when stage calls Actor.draw()
		
//		System.out.println("effect");
		for (int i = 0; i < particleEffects.size(); i++) {
			particleEffects.get(i).draw(batch);
		}
	}

	private int lastX;
	private int lastY;

	public void act(float delta) {
		super.act(delta);
		// effect.setPosition(x, y); // set to whatever x/y you prefer
		// effect.update(delta); // update it
		// effect.start(); // need to start the particle spawning

		int curX = (int) super.getX();
		int curY = (int) super.getY();

		int des = approx_distance2D(curX, curY, lastX, lastY);
		if (des > THRESHOLD) {
			tempEffect = particleEffectPool.obtain();
			tempEffect.setPosition(super.getX(), super.getY());
			tempEffect.start();
			particleEffects.add(tempEffect);
			lastX = curX;
			lastY = curY;
			// System.out.println("add X" + x + "  Y" + y);
		}

		for (int i = 0; i < particleEffects.size(); i++) {
			tempEffect = particleEffects.get(i);
			if (tempEffect.isComplete()) {
				particleEffects.remove(i);
				// System.out.println("remove");
				break;
			}
			tempEffect.update(delta);
		}

	}

	public ParticleEffectPool getEffectPool() {
		return particleEffectPool;
	}

	int approx_distance2D(int curX, int curY, int lastX, int lastY) {
		int x = lastX - curX;
		int y = lastY - curY;

		return x * x + y * y;
	}

	public void dispose() {
		if (tempEffect != null)
			tempEffect.dispose();
		particleEffectPool.clear();
	}

}
