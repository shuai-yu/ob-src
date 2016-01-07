package com.omnibounce.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omnibounce.drawobject.KMovableCircleActor;
import com.omnibounce.interfaces.ICircle;
import com.omnibounce.interfaces.IMovable;
import com.omnibounce.math.Vector2D;

public abstract class Ball extends KMovableCircleActor implements ICircle, IMovable {

	public static final float INITIAL_SPEED = 3.0f;
	public static final float MAX_SPEED = 5.0f;
	public static final float MIN_SPEED = 1.5f;
	public static final float SPEEDUP_PERCENT = 0.4f;
	public static final float SPEEDDOWN_PERCENT = 0.2f;
	
	private ParticleEffectPool particlePool;
	private ArrayList<ParticleEffect> particleList;
	
	public Ball(TextureRegion reg) {
		super(reg);
		
		initParticle();
	}

	// Shoot
	protected void shoot(Paddle bar) {
		shoot(bar.getAngle());
		this.v.scalarMul(-INITIAL_SPEED);
	}
	
	public void shoot(Vector2D v) {
		assert (isMoving() == false);
		moveStart(v);
	}
	
	public final boolean isReadyToShoot() {
		return !isMoving();
	}
	
	public final boolean isShot() {
		return isMoving();
	}

	public void resetAll(Paddle bar) {
		stickTo(bar);
		moveStop();
		speedRestore();
	}

	public abstract void stickTo(Paddle bar);

	// Speed control
	public void speedUp() {
		if (isMoving()) {
			v.scalarMul(SPEEDUP_PERCENT+1);
			speedFix();
		}
	}
	
	public void speedDown() {
		if (isMoving()) {
			v.scalarMul(1 - SPEEDDOWN_PERCENT);
			speedFix();
		}
	}
	
	protected final void speedFix() {
		v.clampLength(MIN_SPEED, MAX_SPEED);
	}
	
	public void speedRestore() {
		v.setLength(INITIAL_SPEED);
	}
	
	// Act & Draw
	@Override
	public void onAct(float delta) {
		super.onAct(delta);
		if (isPresent() && isMoving()) {
			ParticleEffect tem = particlePool.obtain();
			tem.setPosition(getActualX(), getActualY());
			particleList.add(tem);
		}
	}
	
	@Override
	public void onDraw(Batch batch, float timer) {
		for (int i = 0; i < particleList.size(); i++) {
			particleList.get(i).draw(batch, Gdx.graphics.getDeltaTime());
		}
	}
	
	@Override
	public void afterDraw() {
		for (int i=0;i<particleList.size();i++) {
        	ParticleEffect temparticle = particleList.get(i);
            if (temparticle.isComplete()) {
            	particleList.remove(i);
            }
        }
	}
	
	private final void initParticle() {
		ParticleEffect effect = new ParticleEffect();
		effect.load(Gdx.files.internal("data/ring.p"), Gdx.files.internal("data"));
		particlePool = new ParticleEffectPool(effect, 7, 10);
		particleList = new ArrayList<ParticleEffect>();
	}
}
