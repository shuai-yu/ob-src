package com.omnibounce.objects;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ElectronicActors {

	private final Stage stage;

	private ParticleEffect effect;
	private ParticleEffectPool pool;

	private EffectActor effectActor;

	public ElectronicActors(Stage stage) {
		this.stage = stage;
	}

	public void create() {
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("data/a.p"), Gdx.files.internal("data/"));

		pool = new ParticleEffectPool(effect, 500, 1000);
		effectActor = new EffectActor(pool);

		float pX1 = 0, pY1 = 480-230;
		float pX2 = 150, pY2 = 480-230;
		float pX3 = 150, pY3 = 480-310;
		float pX4 = 215, pY4 = 480-310;
		float pX5 = 215, pY5 = 480-275;
		float pX6 = 285, pY6 = 480-275;
		float pX7 = 285, pY7 = 480-310;
		float pX8 = 345, pY8 = 480-310;
		float pX9 = 345, pY9 = 480-445;
		float pX10 = 460, pY10 = 480-445;
		float pX11 = 460, pY11 = 480-190;
		float pX12 = 540, pY12 = 480-190;
		float pX13 = 540, pY13 = 480-110;
		float pX14 = 500, pY14 = 480-110;
		float pX15 = 500, pY15 = 480-50;
		float pX16 = 590, pY16 = 480-50;
		float pX17 = 590, pY17 = 480;
		effectActor.setPosition(pX1, pY1);
		effectActor.addAction(forever(sequence(moveTo(pX2, pY2, 2),
				moveTo(pX3, pY3, 1), moveTo(pX4, pY4, 1),
				moveTo(pX5, pY5, 0.5f), moveTo(pX6, pY6, 1f),
				moveTo(pX7, pY7, 0.5f), moveTo(pX8, pY8, 0.5f),
				moveTo(pX9, pY9, 2f), moveTo(pX10, pY10, 2f),
				moveTo(pX11, pY11, 4f), moveTo(pX12, pY12, 0.5f),
				moveTo(pX13, pY13, 0.5f), moveTo(pX14, pY14, 0.3f),
				moveTo(pX15, pY15, 0.5f), moveTo(pX16, pY16, 1f),
				moveTo(pX17, pY17, 0.5f), moveTo(pX1, pY1))));

		stage.addActor(effectActor);

	}

	public void dispose() {
		effectActor.dispose();
	}
	
	public void draw(SpriteBatch batch) {
		effectActor.draw(batch, 1.0f);
	}
	
	public void act(float delta) {
		effectActor.act(delta);	
	}

}
