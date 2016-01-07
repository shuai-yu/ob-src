package com.omnibounce.objects;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class CircleElectronicActors {
	private final Stage stage;

	private ParticleEffect effect;
	private ParticleEffectPool pool;

	private EffectActor effectActor;
	private EffectActor effectActor2;
	private EffectActor effectActor22;
	private EffectActor effectActor3;
	private EffectActor effectActor4;
	private EffectActor effectActor5;
	private EffectActor effectActor55;
	private EffectActor effectActor6;
	private EffectActor effectActor66;

	public CircleElectronicActors(Stage stage) {
		this.stage = stage;
	}

	public void create() {
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("data/a.p"), Gdx.files.internal("data/"));

		pool = new ParticleEffectPool(effect, 500, 1000);
		effectActor = new EffectActor(pool);

		// actor1
		float pX1 = 216, pY1 = 480 - 385;
		float pX2 = 153, pY2 = 480 - 416;
		float pX3 = 84, pY3 = 480 - 416;
		float pX4 = 84, pY4 = 480 - 385;
		float pX5 = 0, pY5 = 480 - 385;
		effectActor.setPosition(pX1, pY1);
		effectActor.addAction(forever(sequence(moveTo(pX2, pY2, 1),
				moveTo(pX3, pY3, 1), moveTo(pX4, pY4, 1), moveTo(pX5, pY5, 1),
				moveTo(pX1, pY1))));
		stage.addActor(effectActor);
		// actor2
		effectActor2 = new EffectActor(pool);
		float p2X1 = 174, p2Y1 = 480 - 210;
		float p2X2 = 0, p2Y2 = 480 - 210;
		effectActor2.setPosition(p2X1, p2Y1);
		effectActor2.addAction(forever(sequence(moveTo(p2X2, p2Y2, 2),
				moveTo(p2X1, p2Y1))));
		stage.addActor(effectActor2);

		// actor2
		effectActor22 = new EffectActor(pool);
		float p22X1 = 174, p22Y1 = 480 - 195;
		float p22X2 = 0, p22Y2 = 480 - 195;
		effectActor22.setPosition(p22X1, p22Y1);
		effectActor22.addAction(forever(sequence(moveTo(p22X2, p22Y2, 2),
				moveTo(p22X1, p22Y1))));
		stage.addActor(effectActor22);

		// actor3
		effectActor3 = new EffectActor(pool);
		float p3X1 = 210, p3Y1 = 480 - 93;
		float p3X2 = 192, p3Y2 = 480 - 69;
		float p3X3 = 110, p3Y3 = 480 - 69;
		float p3X4 = 110, p3Y4 = 480;
		effectActor3.setPosition(p3X1, p3Y1);
		effectActor3.addAction(forever(sequence(moveTo(p3X2, p3Y2, 0.5f),
				moveTo(p3X3, p3Y3, 2), moveTo(p3X4, p3Y4, 2),
				moveTo(p3X1, p3Y1))));
		stage.addActor(effectActor3);

		// actor4
		effectActor4 = new EffectActor(pool);
		float p4X1 = 570, p4Y1 = 480 - 78;
		float p4X2 = 680, p4Y2 = 480 - 78;
		float p4X3 = 730, p4Y3 = 480 - 125;
		float p4X4 = 800, p4Y4 = 480 - 125;
		effectActor4.setPosition(p4X1, p4Y1);
		effectActor4.addAction(forever(sequence(moveTo(p4X2, p4Y2, 1.5f),
				moveTo(p4X3, p4Y3, 1.5f), moveTo(p4X4, p4Y4, 1),
				moveTo(p4X1, p4Y1))));
		stage.addActor(effectActor4);

		// actor5
		effectActor5 = new EffectActor(pool);
		float p5X1 = 616, p5Y1 = 480 - 317;
		float p5X2 = 700, p5Y2 = 480 - 373;
		effectActor5.setPosition(p5X1, p5Y1);
		effectActor5.addAction(forever(sequence(moveTo(p5X2, p5Y2, 2),
				moveTo(p5X1, p5Y1))));
		stage.addActor(effectActor5);

		effectActor55 = new EffectActor(pool);
		float p55X1 = 606, p55Y1 = 480 - 327;
		float p55X2 = 690, p55Y2 = 480 - 383;
		effectActor55.setPosition(p55X1, p55Y1);
		effectActor55.addAction(forever(sequence(moveTo(p55X2, p55Y2, 2),
				moveTo(p55X1, p55Y1))));
		stage.addActor(effectActor55);

		effectActor6 = new EffectActor(pool);
		float p6X1 = 595, p6Y1 = 480 - 373;
		float p6X2 = 595, p6Y2 = 480 - 412;
		float p6X3 = 656, p6Y3 = 480 - 412;
		effectActor6.setPosition(p6X1, p6Y1);
		effectActor6.addAction(forever(sequence(moveTo(p6X2, p6Y2, 2),
				moveTo(p6X3, p6Y3, 2), moveTo(p6X1, p6Y1))));
		stage.addActor(effectActor6);

		effectActor66 = new EffectActor(pool);
		float p66X1 = 580, p66Y1 = 480 - 396;
		float p66X2 = 580, p66Y2 = 0;
		effectActor66.setPosition(p66X1, p66Y1);
		effectActor66.addAction(forever(sequence(moveTo(p66X2, p66Y2, 2),
				moveTo(p66X1, p66Y1))));
		stage.addActor(effectActor66);
	}

	public void draw(SpriteBatch batch) {
		effectActor.draw(batch, 1.0f);
		effectActor2.draw(batch, 1.0f);
		effectActor22.draw(batch, 1.0f);
		effectActor3.draw(batch, 1.0f);
		effectActor4.draw(batch, 1.0f);
		effectActor5.draw(batch, 1.0f);
		effectActor55.draw(batch, 1.0f);
		effectActor6.draw(batch, 1.0f);
		effectActor66.draw(batch, 1.0f);
	}
	
	public void act(float delta) {
		effectActor.act(delta);
		effectActor2.act(delta);
		effectActor22.act(delta);
		effectActor3.act(delta);
		effectActor4.act(delta);
		effectActor5.act(delta);
		effectActor55.act(delta);
		effectActor6.act(delta);
		effectActor66.act(delta);
	}
	
	public void dispose() {
		effectActor.dispose();
		effectActor2.dispose();
		effectActor3.dispose();
		effectActor4.dispose();
	}
}
