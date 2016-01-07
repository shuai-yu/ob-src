package com.omnibounce.screens;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.omnibounce.assets.Assets;
import com.omnibounce.assets.AudioManager;
import com.omnibounce.assets.GamePreferences;
import com.omnibounce.assets.KMaterial;
import com.omnibounce.constants.Constants;
import com.omnibounce.drawobject.KCircleActor;
import com.omnibounce.drawobject.KStillActor;
import com.omnibounce.interfaces.ICircle;
import com.omnibounce.interfaces.IHittable;
import com.omnibounce.interfaces.ILevelConfig;
import com.omnibounce.interfaces.IMovable;
import com.omnibounce.levels.Level;
import com.omnibounce.levels.LevelUtils;
import com.omnibounce.maps.KMap;
import com.omnibounce.maps.KMapManager;
import com.omnibounce.maps.tokens.BrickToken;
import com.omnibounce.maps.tokens.TBlackHole;
import com.omnibounce.maps.tokens.TContainerBrick;
import com.omnibounce.maps.tokens.TContinuousWall;
import com.omnibounce.maps.tokens.TCounterBrick;
import com.omnibounce.maps.tokens.THardBrick;
import com.omnibounce.maps.tokens.TShooter;
import com.omnibounce.maps.tokens.TSingleWall;
import com.omnibounce.maps.tokens.WallToken;
import com.omnibounce.math.Point2D;
import com.omnibounce.math.RealOp;
import com.omnibounce.math.Vector2D;
import com.omnibounce.objects.Ball;
import com.omnibounce.objects.Balloon;
import com.omnibounce.objects.Brick;
import com.omnibounce.objects.CircleElectronicActors;
import com.omnibounce.objects.Divider;
import com.omnibounce.objects.Paddle;
import com.omnibounce.objects.Shield;
import com.omnibounce.stages.BasicStage;
import com.omnibounce.stages.StageManager;
import com.omnibounce.transition.AbstractGameScreen;
import com.omnibounce.transition.DirectedGame;
import com.omnibounce.transition.ScreenTransition;
import com.omnibounce.transition.ScreenTransitionFade;
import com.omnibounce.transition.ScreenTransitionSlice;
import com.omnibounce.transition.ScreenTransitionSlide;
import com.omnibounce.utils.KTimer;


public class NewGameScreen extends AbstractGameScreen {

	// constants
	private static final Point2D ViewCenter = new Point2D(Constants.VIEW_WIDTH / 2,
			Constants.VIEW_HEIGHT / 2);
	private static final Point2D DeskCircleCenter = new Point2D(400, 240);
	
	private final Point2D CenterOnScreen = new Point2D(400, 240);
	
	private static final float CIRCLE_RADIUS_OUTER = 218;
	private static final float CIRCLE_RADIUS_INNER = CIRCLE_RADIUS_OUTER - 8;
	private static final float CIRCLE_MIN_OFFSET = CIRCLE_RADIUS_INNER * 0.08f;

	// director
	private final DirectedGame director;
	
	// rendering options
	
	// assets & materials
	private final Assets assets;
	private final AudioManager audio;
	private final KMaterial mate;
	
	// game state
	private boolean exiting;
	private boolean paused; // Don't change it directly.
	private boolean ready;
	private boolean ended;
	private boolean victory;
	
	// game data
	private int levelNumber;
	private KMap map;
	
	// game control
	private BasicLevel level;
	private boolean gravity;
	private int swapPaddle;

	// game timers
	
	// particle effects
	private ParticleEffectPool collisionEffectPool;
	private ParticleEffect collisionEffect;
	private List<ParticleEffect> colTemp;
	
	// layers
	private BackgroundStage bg;
	private DeskStage desk;
	private WallStage walls;
	private BrickStage bricks;
	private PaddleStage paddles;
	private BalloonStage balloons;
	private BallStage balls;
	private UIStage ui;
	
	public NewGameScreen(DirectedGame game, String levelName, boolean gravityMode) {
		// Call super class's constructor
		super(game);
		
		this.director = game;
		this.assets = Assets.getInstance();
		this.mate = assets.mateGame;
		this.audio = AudioManager.getInstance();
		this.gravity = gravityMode;
		
		// Check if assets are all loaded
		if (!assets.isLoadedCompletely()) {
			Gdx.app.log("GSCREEN", "Some assets are not loaded yet. Please wait...");
			assets.delayInit();
		}
		
		// Build stages
		bg = new BackgroundStage();
		desk = new DeskStage();
		walls = new WallStage();
//		ui = new UIStage();
		bricks = new BrickStage();
		paddles = new PaddleStage();
		balloons = new BalloonStage();
		balls = new BallStage();
		
		// Create new game
		newGame(levelName);
		
	}

	// SCREEN CONTROL
	
	@Override
	public InputProcessor getInputProcessor() {

		return ui;
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		// clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// ui control
		ui.control();
		
		// render stages
		bg.render(delta);
		desk.render(delta);
		walls.render(delta);
		bricks.render(delta);
		paddles.render(delta);
		balloons.render(delta);
		balls.render(delta);
		ui.render(delta);
	}
	
	@Override
	public void show() {
		super.show();
		// called when being switched to this screen
		Gdx.app.log("GSCREEN", "show()");
		 Gdx.input.setCatchBackKey(true);
	}
	
	@Override
	public void hide() {
		super.hide();
		// called when being switched to another screen
		Gdx.app.log("GSCREEN", "hide()");
		 Gdx.input.setCatchBackKey(false);
	}
	
	@Override
	public void pause() {
		super.pause();
		// may be called when the transition starts
		Gdx.app.log("GSCREEN", "pause()");
	}
	
	@Override
	public void resume() {
		super.resume();
		Gdx.app.log("GSCREEN", "resume()");
	}
	
	@Override
	public void resize(int w, int h) {
		super.resize(w, h);
		Gdx.app.log("GSCREEN", "resize("+w+","+h+")");
		CenterOnScreen.set(w/2, h/2);
	}
	
	public void returnToLevelScreen() {
		if (!exiting) {
			exiting = true;
			
			Gdx.app.log("GSCREEN", "exit()");
			ScreenTransition transition = ScreenTransitionSlide.init(1.25f,
	                ScreenTransitionSlide.RIGHT, false, Interpolation.swing);
	        director.setScreen(new SelectScreen(director), transition);
		}
		
	}
	
	// GAME OBJECTS [Basic]
	
	private abstract class BasicBall extends Ball {

		private static final float DEFAULT_INVINCIBLE_DURATION = 5;
		private static final float DEFAULT_TRANSPARENT_DURATION = 3;
		private static final float DEFAULT_TRANSPARENT_ALPHA = 0.5f;

		private final int id;
		private int paddleId;
		private int power;
		private boolean requested;
		private KTimer timerInvincible;
		private KTimer timerTransparent;

		public BasicBall(TextureRegion region, int id) {
			super(region);
			setCenterPosition(DeskCircleCenter);
			this.id = id;
			this.paddleId = -1;
		}
		
		public final int getId() {
			return id;
		}
		
		public final Paddle getCurrentPaddle() {
			if (paddleId >= 0)
				return paddles.get(paddleId ^ swapPaddle);
			else
				return null;
		}

		public final int getPower() {
			if (KTimer.isTicking(timerInvincible))
				return 255;
			else
				return power;
		}

		public final void PowerUp() {
			++power;
		}

		public final void PowerMax() {
			KTimer.kill(timerInvincible);
			timerInvincible = new KTimer(DEFAULT_INVINCIBLE_DURATION) {

				@Override
				protected void onOver() {
				}

			};
		}

		public final void setTransparent() {
			KTimer.kill(timerTransparent);
			timerTransparent = new KTimer(DEFAULT_TRANSPARENT_DURATION) {

				@Override
				protected void onStart() {
					setAlpha(DEFAULT_TRANSPARENT_ALPHA);
				}

				@Override
				protected void onOver() {
					setAlpha(1);
				}

			};
		}

		public void requestShoot() {
			if (isReadyToShoot()) {
				requested = true;
				Gdx.app.log("GAME","REQUEST TO SHOOT");
			}
		}
		
		public void stick() {
			if (!isMoving()) {
				stickTo(getCurrentPaddle());
			}
		}
		
		@Override
		public void stickTo(Paddle bar) {
			setOffset(Point2D.Origin.forwardTo(bar.getAngle(), CIRCLE_RADIUS_INNER
					- getRadius()));
		}
		
		@Override
		public void resetAll(Paddle paddle) {
			
			if (paddle != null) {
				this.paddleId = ((BasicPaddle)paddle).getId() ^ swapPaddle;
			}
			if (paddleId >= 0) {
				super.resetAll(getCurrentPaddle());
			}
			
			this.power = 1;
			this.requested = false;
			
			KTimer.kill(timerInvincible);
			KTimer.kill(timerTransparent);
		}

		@Override
		public void onAct(float delta) {
			super.onAct(delta);

			if (!isPresent()) {
				KTimer.kill(timerInvincible);
				KTimer.kill(timerTransparent);
				return;
			}

			KTimer.tick(timerInvincible, delta);
			KTimer.tick(timerTransparent, delta);
			
			if (isMoving()) {
				// Brick Hit Test
				ballHitTest(this);
				if (!isPresent())
					return;
				// Ball Bounce Test
				if (!ballBounceTest(this)) {
					// Ball Miss Test
					ballMissTest(this);
				}
			}else {
				if (requested && paddleId >= 0 && level.isBuiltInBall(id)) {
					Gdx.app.log("GAME", "SHOOT");
					stick();
					shoot(getCurrentPaddle());
					requested = false;
				}
			}
		}
	}
	
	private abstract class BasicPaddle extends Paddle {

		private static final float DEFAULT_VIBRATE_DURATION = 0.5f;
		private static final float DEFAULT_VIBRATE_AMPLITUDE = 0.1f;
		private static final int DEFAULT_VIBRATE_CYCLES = 6;
		private static final float DEFAULT_REVERSE_DURATION = 3.0f;
		private static final float DEFAULT_MAGNETIC_DURATION = 5.0f;
		
		private final int id;
		private KTimer timerVibrate;
		private KTimer timerReverse;
		private KTimer timerMagnetic;
		private boolean reversed;
		private boolean magnetic;

		public BasicPaddle(TextureRegion region, int id, float endSize) {
			super(region, CIRCLE_RADIUS_OUTER, endSize);
			setCenterPosition(DeskCircleCenter);
			this.id = id;
			
			setAngle(getAngle());
		}
		
		public final int getId() {
			return id;
		}

		public void resetAll() {
			super.resetAll();
			this.reversed = false;
			this.magnetic = false;
			KTimer.kill(timerVibrate);
			KTimer.kill(timerReverse);
			KTimer.kill(timerMagnetic);
		}

		@Override
		public void onAct(float delta) {
			// Adjust position
			Vector2D curAngle = ui.getPaddleAngle();
			if (curAngle != null && /*curAngle.y <=0 && */ !safeZone(curAngle)) {
				if (0 == (id & 1)) {
					setAngle(curAngle);
				}else {
					setAngle(curAngle.copy().reverse());
				}
			}
			
			// Do Item Catch Test
			itemCatchTest(this);

			KTimer.tick(timerVibrate, delta);
			KTimer.tick(timerReverse, delta);
			KTimer.tick(timerMagnetic, delta);
		}

		protected void turnMagneticOn() {
			magnetic = true;
			changeTexture(assets.objs.magneticPaddle);
		}

		protected void turnMagneticOff() {
			magnetic = false;
			changeTexture(assets.objs.paddle);
		}

		public void startMagnetic() {
			KTimer.kill(timerMagnetic);
			timerMagnetic = new KTimer(DEFAULT_MAGNETIC_DURATION) {
				@Override
				protected void onStart() {
					turnMagneticOn();
				}

				@Override
				protected void onOver() {
					turnMagneticOff();
				}
			};
		}

		public void setReverse() {
			KTimer.kill(timerReverse);
			timerReverse = new KTimer(DEFAULT_REVERSE_DURATION) {
				@Override
				protected void onStart() {
					reversed = true;
				}

				@Override
				protected void onOver() {
					reversed = false;
				}
			};
		}

		public void startVibrate() {
			KTimer.kill(timerVibrate);
			timerVibrate = new KTimer(DEFAULT_VIBRATE_DURATION, false, true) {
				@Override
				protected void onStart() {
					Gdx.input.vibrate((int) (DEFAULT_VIBRATE_DURATION * 1000));
				}

				@Override
				protected void onOver() {
					Gdx.input.cancelVibrate();
					
					Gdx.app.log("PADDLE", "Vibration stops");
				}
			};
		}

		@Override
		public void setAngle(Vector2D v) {
			if (isVibrating()) { // don't change its angle while vibrating
				super.setAngle(getAngle());

				// vibration simulation
				float tmp = MathUtils.sin((timerVibrate
						.getTicks() * DEFAULT_VIBRATE_CYCLES * (MathUtils.PI2)));
				setOffset(getOffset().forwardToN(getAngle().getOneNormal(),
						getValidLength() * DEFAULT_VIBRATE_AMPLITUDE * tmp));
			} else {
				if (reversed)
					v.y = -v.y;
				super.setAngle(v);
			}
		}

		public boolean isVibrating() {
			return KTimer.isTicking(timerVibrate);
		}

		@Override
		public Vector2D onHit(ICircle obj, Point2D intersection, Vector2D incidence,
				Vector2D reflected) {

			if (magnetic) {
				if (obj instanceof BasicBall) {
					((BasicBall) obj).resetAll(this);
					
					// play magnet sound
					return null; // no reflection
				}
			}
			audio.play(assets.sounds.fx_hitpaddle);
			return reflected;
		}
	}
	
	private abstract class BasicBrick extends Brick {

		protected boolean postTest = false;

		public BasicBrick(TextureRegion region, BrickToken token) {
			super(region);
			setCenterPosition(DeskCircleCenter);
			
			if (token != null) {
				moveTo(token.getX(), token.getY());
				
				// apply attribute template
				token.applyTo(this);
				if (token.getTexture() != null)
					super.changeTexture(token.getTexture());
			}
		}

		public abstract boolean isDestroyed();

		protected void onDestroy(BasicBall ball) {
			level.onDestroyBrick();
		}
		
		public final boolean isPostTest() {
			return postTest;
		}
		
		@Override
		public Vector2D onHit(ICircle obj, Point2D intersection, Vector2D incidence,
				Vector2D reflected) {
			
			// spill effect
			Point2D p = intersection.forwardTo(this.getCenter(), obj.getRadius());
			
			Vector2D vv = p.dispTo(this.getCenter()).getOneNormal();
			
			ParticleEffect tmp1, tmp2;
			tmp1 = collisionEffectPool.obtain();
			tmp2 = collisionEffectPool.obtain();
			for (int i = 0; i < tmp1.getEmitters().size; i++) {
				tmp1.getEmitters().get(i).getAngle().setHigh(vv.degrees() - 5, vv.degrees() + 5);
			}
			for (int i = 0; i < tmp2.getEmitters().size; i++) {
				tmp2.getEmitters().get(i).getAngle().setHigh(vv.degrees() - 5+180, vv.degrees() + 5+180);
			}
			tmp1.start();
			tmp2.start();
			
			float px = p.x + DeskCircleCenter.x;
			float py = p.y + DeskCircleCenter.y;
			tmp1.setPosition(px,py);
			tmp2.setPosition(px,py);
			
			colTemp.add(tmp1);
			colTemp.add(tmp2);
			
			return reflected;
		}
	}
	
	private abstract class BasicDivider extends Divider {

		public BasicDivider(TextureRegion region, WallToken token, Point2D p1, Point2D p2, float width) {
			super(region, p1, p2, width);
			setCenterPosition(DeskCircleCenter);
			
			if (token != null) {
				
				// apply attribute template
				token.applyTo(this);
				if (token.getTexture() != null)
					super.changeTexture(token.getTexture());
			}
		}

		@Override
		public Vector2D onHit(ICircle obj, Point2D intersection, Vector2D incidence,
				Vector2D reflected) {
			if (obj instanceof BasicBall)
				audio.play(assets.sounds.fx_hitwall);
			
			return reflected;
		}
	}
	
	private abstract class BasicBalloon extends Balloon {

		private final boolean positive;

		public BasicBalloon(TextureRegion region, boolean positive) {
			super(region);
			setCenterPosition(DeskCircleCenter);
			this.positive = positive;
		}

		public final boolean isPositive() {
			return positive;
		}

		@Override
		public void onAct(float delta) {
			super.onAct(delta);
			if (!isPresent()) {
				// already destroyed (caught by paddle or broken by wall)
				
				//  TODO: debug
				balloons.remove(this);
			}
		}

		protected void onCatch(BasicPaddle paddle) {
			// TODO: play customized sounds
			if (paddle != null) audio.play(isPositive()?assets.sounds.fx_getpositem:assets.sounds.fx_getnegitem);
			System.out.println("Paddle" + paddle.getId() + " caught " + this);
		}

		protected void onMiss() {
			System.out.println("You missed me " + this + "(" + this.getOffsetX() + "," + this.getOffsetY() + ") len = "+new Point2D(this.getOffsetX(), this.getOffsetY()).distTo(Point2D.Origin));
		}

		protected void onBreak() {
			System.out.println("broken " + this);
		}
	}
	
	private abstract class GameEffectBalloon extends BasicBalloon {

		public GameEffectBalloon(TextureRegion region, boolean positive) {
			super(region, positive);
		}

		protected abstract void onEffect();

		@Override
		protected void onCatch(BasicPaddle paddle) {
			super.onCatch(paddle);
			onEffect();
		}
	}

	private abstract class BallEffectBalloon extends BasicBalloon {

		public BallEffectBalloon(TextureRegion region, boolean positive) {
			super(region, positive);
		}

		protected abstract void onEffect(BasicBall eachBall);

		@Override
		protected void onCatch(BasicPaddle paddle) {
			super.onCatch(paddle);
			
			// takes effect for every ball
			for (BasicBall b : balls.enumAll())
				if (b != null && b.isPresent())
					onEffect(b);
		}
	}

	private abstract class PaddleEffectBalloon extends BasicBalloon {

		public PaddleEffectBalloon(TextureRegion region, boolean positive) {
			super(region, positive);
		}

		protected abstract void onEffect(BasicPaddle eachPaddle);

		@Override
		protected void onCatch(BasicPaddle paddle) {
			super.onCatch(paddle);
			
			// takes effect for every ball
			for (BasicPaddle p : paddles.enumAll())
				onEffect(p);
		}
	}
	
	// GAME OBJECTS [Default]

	private final class DefaultBall extends BasicBall {

		public DefaultBall(int id) {
			super(assets.objs.ball, id);
		}
	}
	
	
	private final class DefaultDivider extends BasicDivider {

		public DefaultDivider(TSingleWall model) {
			this(model, new Point2D(model.getX1(), model.getY1()), new Point2D(model.getX2(), model.getY2()));
		}
		
		public DefaultDivider(TContinuousWall model, int offset) {
			this(model, model.getPoint(offset), model.getPoint(offset+1));
		}
		
		public DefaultDivider(WallToken model, Point2D p1, Point2D p2) {
			super(assets.objs.vwall, model, p1, p2, assets.objs.vwall.getRegionWidth() / 2 );
		}
	}
	
	private final class DefaultPaddle extends BasicPaddle {
		public DefaultPaddle(int id) {
			super(assets.objs.paddle, id, 6f);
		}
	}
	
	private static boolean safeZone(Vector2D OO1) {
		return RealOp.epsSignByDiff(Math.abs(OO1.cosineBetween(Vector2D.PosY)), 0.891f) >= 0;
	}
	
	private final class NShield extends Shield {

		public NShield() {
			super(mate.get("shield_edge"), CIRCLE_RADIUS_INNER);
			hide();
			setCenterPosition(DeskCircleCenter);
		}
		
		@Override
		protected boolean isSafe(Vector2D OO1) {
			return safeZone(OO1);
		}
	}
	
	private abstract class SolidBrick extends BasicBrick {

		public SolidBrick(TextureRegion region, THardBrick model) {
			super(region, model);
		}
		
		@Override
		public final boolean isDestroyed() {
			return true;
		}

		@Override
		public final void onDestroy(BasicBall ball) {
			// This kind of bricks can't be destroyed.
			// Actually this method won't be called.
			super.onDestroy(ball);
		}
		
		@Override
		public Vector2D onHit(ICircle obj, Point2D intersection, Vector2D incidence,
				Vector2D reflected) {
			return reflected;
		}
	}
	
	private final class HardBrick extends SolidBrick {

		public HardBrick(THardBrick model) {
			super(assets.objs.hardBrick, model);
		}
		
	}
	
	private final class PivotBrick extends SolidBrick {

		public PivotBrick(THardBrick model) {
			super(assets.objs.brick, model);
			
			postTest = true;
		}
		
		@Override
		public Vector2D onHit(ICircle obj, Point2D intersection, Vector2D incidence,
				Vector2D reflected) {

			// pivot brick is a part of wall
			audio.play(assets.sounds.fx_hitwall);
			return reflected;
		}
		
	}
	
	private final class ShooterBrick extends SolidBrick {

		private KTimer timerBullet;

		public ShooterBrick(TShooter model) {
			super(assets.objs.shooterBrick, model);
			
			timerBullet = new KTimer(model.getShootInterval(), true, true) {
				@Override
				protected void onOver() {
					// send a bullet
					BasicBalloon bullet = new BulletBalloon();
					Vector2D rndV = Vector2D.makeRandomUnitVector();
					bullet.setCenter(getCenter().forwardTo(rndV, getRadius()));
					bullet.moveStart(rndV);
					bullet.dontRotate();
					balloons.add(bullet);
				}
			};
		}

		@Override
		public void onAct(float delta) {
			super.onAct(delta);

			KTimer.tick(timerBullet, delta);
		}
	}
	
	private final class BlackHoleBrick extends SolidBrick {

		private final BlackHoleBrick partner;

		public BlackHoleBrick(TBlackHole model) {
			super(assets.objs.blackHoleBrick, model);
			
			this.partner = new BlackHoleBrick(this, model);
		}
		
		public BlackHoleBrick(BlackHoleBrick partner, TBlackHole model) {
			super(assets.objs.blackHoleBrick, model);
			moveTo(model.getPalX(), model.getPalY());
			
			this.partner = partner;
		}

		public BlackHoleBrick getPartner() {
			return partner;
		}

		@Override
		public Vector2D onHit(ICircle obj, Point2D intersection, Vector2D incidence,
				Vector2D reflected) {

			level.addScore(150);
			obj.setCenter(partner.getCenter().forwardTo(incidence,
					partner.getRadius() + obj.getRadius()));
			return null;
		}

	}
	
	private class CounterBrick extends BasicBrick {

		protected int hitCount;

		public CounterBrick(TextureRegion region, TCounterBrick model) {
			super(region, model);
			this.hitCount = model.getHits();
		}

		@Override
		public Vector2D onHit(ICircle obj, Point2D intersection, Vector2D incidence,
				Vector2D reflected) {

			audio.play(assets.sounds.fx_hitbrick);
			if (hitCount != -1) {
				if (obj instanceof BasicBall) {
					BasicBall b = (BasicBall) obj;
					hitCount -= b.getPower();
					onCountDown(hitCount);
					if (hitCount <= 0) {
						onDestroy(b);
						dispose();
						if (hitCount <= -3) // hit by invincible ball
							return null; // penetrate
					}
				}
			}
			return super.onHit(obj, intersection, incidence, reflected);
		}

		protected void onCountDown(int counter) {
			if (counter == 2)
				setScale(0.75f);
			if (counter == 1)
				setScale(0.6f);
		}

		@Override
		public final boolean isDestroyed() {
			return (hitCount <= 0);
		}
	}
	
	private final class ContainerBrick extends CounterBrick {

		private final static float DEFAULT_BALLOON_SIZE = 0.5f;
		
		private BasicBalloon balloon;

		public ContainerBrick(TContainerBrick model) {
			// TODO: design a texture
			super(assets.objs.brick, model);
			
			switch (model.getItemType()) {
			
			case SHIELD:
				balloon = new ShieldBalloon();
				break;
			case SPLIT:
				balloon = new SplitBalloon();
				break;
			case POWERUP:
				balloon = new PowerUpBalloon();
				break;
			case POWERMAX:
				balloon = new PowerMaxBalloon();
				break;
			case TRANSPARENT:
				balloon = new TransparentBalloon();
				break;
			case ADDLIFE:
				balloon = new AddLifeBalloon();
				break;
			case BULLET:
				balloon = new BulletBalloon();
				break;
			case MANGET:
				balloon = new MagneticBalloon();
				break;
			case EXPAND:
				balloon = new ExpandBalloon();
				break;
			case SHRINK:
				balloon = new ShrinkBalloon();
				break;
			case SPEEDUP:
				balloon = new SpeedUpBalloon();
				break;
			case SPEEDDOWN:
				balloon = new SpeedDownBalloon();
				break;
			case REVERSE:
				balloon = new ReverseBalloon();
				break;
			default:
				balloon = null;
				break;
				
			}
			
			assert (balloon != null);

			balloon.setAlpha(model.getBalloonOpacity());
			balloon.setScale(DEFAULT_BALLOON_SIZE);
			balloon.dontRotate();
			balloon.show();
		}

		@Override
		protected void onDestroy(BasicBall ball) {
			super.onDestroy(ball);
			// produce balloon (before reflecting)
			Vector2D rndV = ball.getVelocity().copy();
			rndV = rndV.reverse();
			this.bubble(rndV);
		}

		protected void bubble(Vector2D v) {
			if (balloon.isPresent()) {
				balloon.setAlpha(1);
				balloon.setScale(1);
				balloon.moveStart(v);
			}
		}
		
		@Override
		protected void onStage() {
			balloon.setCenter(this.getCenter());
			balloons.add(balloon);
		}
	}
	
	// GAME OBJECTS [Balloons]
	
	private final class MagneticBalloon extends PaddleEffectBalloon {

		public MagneticBalloon() {
			super(assets.items.magnetic, true);
		}

		@Override
		protected void onEffect(BasicPaddle p) {
			p.startMagnetic();
		}
	}

	private final class ExpandBalloon extends PaddleEffectBalloon {
		public ExpandBalloon() {
			super(assets.items.expand, true);
		}

		@Override
		protected void onEffect(BasicPaddle p) {
			p.lengthen();
		}
	}

	private final class ShrinkBalloon extends PaddleEffectBalloon {
		public ShrinkBalloon() {
			super(assets.items.shrink, false);
		}

		@Override
		protected void onEffect(BasicPaddle p) {
			p.shorten();
		}
	}

	private final class ReverseBalloon extends PaddleEffectBalloon {
		public ReverseBalloon() {
			super(assets.items.reverse, false);
		}

		@Override
		protected void onEffect(BasicPaddle p) {
			p.setReverse();
		}
	}

	private final class BulletBalloon extends PaddleEffectBalloon {
		public BulletBalloon() {
			super(assets.objs.bullet, false);
		}

		@Override
		protected void onCatch(BasicPaddle paddle) {
			super.onCatch(paddle);
			Gdx.app.log("BULLET", "You shouldn't have got me.");
		}

		@Override
		protected void onEffect(BasicPaddle p) {
			p.startVibrate();
		}

		@Override
		public void onAct(float delta) {
			super.onAct(delta);
			if (isPresent())
				itemBreakTest(this);
		}
	}

	private final class SplitBalloon extends GameEffectBalloon {
		public SplitBalloon() {
			super(assets.items.split, true);
		}

		@Override
		public void onEffect() {
			for (int i = 0; i < level.getBallCount(); i++) {
				if (balls.get(i).isPresent())
					showShadowBall(balls.get(i));
			}
			level.addScore(175);
		}
	}

	private final class AddLifeBalloon extends GameEffectBalloon {
		public AddLifeBalloon() {
			super(assets.items.addLife, true);
		}

		@Override
		public void onEffect() {
			level.newLife();
			level.addScore(200);
		}
	}

	private final class ShieldBalloon extends GameEffectBalloon {
		public ShieldBalloon() {
			super(assets.items.shield, true);
		}

		@Override
		public void onEffect() {
			desk.enableShield();
			level.addScore(150);
		}
	}

	private final class PowerUpBalloon extends BallEffectBalloon {
		public PowerUpBalloon() {
			super(assets.items.powerUp, true);
		}

		@Override
		protected void onEffect(BasicBall b) {
			b.PowerUp();
			level.addScore(100);
		}
	}

	private final class PowerMaxBalloon extends BallEffectBalloon {
		public PowerMaxBalloon() {
			super(assets.items.powerMax, true);
		}

		@Override
		protected void onEffect(BasicBall b) {
			b.PowerMax();
			level.addScore(300);
		}
	}

	private final class SpeedUpBalloon extends BallEffectBalloon {
		public SpeedUpBalloon() {
			super(assets.items.speedUp, false);
		}

		@Override
		protected void onEffect(BasicBall b) {
			b.speedUp();
		}
	}

	private final class SpeedDownBalloon extends BallEffectBalloon {
		public SpeedDownBalloon() {
			super(assets.items.speedDown, true);
		}

		@Override
		protected void onEffect(BasicBall b) {
			b.speedDown();
			level.addScore(100);
		}
	}

	private final class TransparentBalloon extends BallEffectBalloon {
		public TransparentBalloon() {
			super(assets.items.transparent, false);
		}

		@Override
		protected void onEffect(BasicBall b) {
			b.setTransparent();
		}
	}
	
	// LAYERS
	
	private final class BackgroundStage extends BasicStage {
		BackgroundStage() {
			super();
			new KStillActor(mate.get("background"), ViewCenter)
					.addToStage(this);
		}
	}
	
	private final class DeskStage extends BasicStage {
		private static final float DEFAULT_SHIELD_DURATION = 5.0f;

		private KTimer timerShield;
		private Shield objShield;
		private Shield nShield;
//		private CircleElectronicActors actors;
		
		DeskStage() {
			super();
			objShield = new DefaultShield();
			objShield.hide();
			objShield.addToStage(this);
			
			nShield = new NShield();
			
//			actors = new CircleElectronicActors(this);
//			actors.create();
		}
		
//		protected void onRender(float delta, Batch batch) {
//			actors.act(delta);
//			batch.begin();
//			actors.draw((SpriteBatch)batch);
//			batch.end();
//		}

		public Shield getShield() {
			if (KTimer.isTicking(timerShield))
				return objShield;
			else
				return null;
		}
		
		public Shield getNShield() {
			return nShield;
		}

		public void enableShield() {
			KTimer.kill(timerShield);
			timerShield = new KTimer(DEFAULT_SHIELD_DURATION) {

				@Override
				protected void onOver() {
					objShield.hide();
				}

				@Override
				protected void onStart() {
					objShield.show();
				}

			};
		}
		
		private final class DefaultShield extends Shield {

			public DefaultShield() {
				super(mate.get("shield_edge"), CIRCLE_RADIUS_INNER);
				setCenterPosition(DeskCircleCenter);
			}

			@Override
			public void onAct(float delta) {
				super.onAct(delta);

				KTimer.tick(timerShield, delta);
			}

			@Override
			protected boolean isSafe(Vector2D OO1) {
				return OO1.y >= 0;
			}
		}
	}
	
	private final class WallStage extends StageManager<BasicDivider> {
	}
	
	private final class BrickStage extends StageManager<BasicBrick> {
	}
	
	private final class PaddleStage extends StageManager<BasicPaddle> {
	}
	
	private final class BalloonStage extends StageManager<BasicBalloon> {
	}
	
	private final class BallStage extends StageManager<BasicBall> {
	}
	
	private final class UIStage extends BasicStage {
		
		private TextureRegion[][] regDigits;
		private KTimer timerHurry;
		private KTimer timerReady;

		private KStillActor scrPause;
		private KStillActor scrReady;
		
		private float curSample;
		private final Point2D curCursor = new Point2D(Point2D.Origin);
		
		private final static float DEFAULT_MULTIPLIER = 1.0f;
		private float[] lastValues = new float[10];
		
		private ImageButton swapBtn;
		
		public UIStage() {
			super();
			
			regDigits = mate.get("digit").split(18, 15);
			scrPause = addInvisibleScreen(mate.get("screen_pause"));
			scrReady = addInvisibleScreen(mate.get("screen_ready"));
			
			swapBtn = new ImageButton(new TextureRegionDrawable(Assets.getInstance().assetLevelAtlas.swapbtnRegion),new TextureRegionDrawable(Assets.getInstance().assetLevelAtlas.swapDownRegion));
			swapBtn.addListener(new ChangeListener() {

				@Override
				public void changed(ChangeEvent event, Actor actor) {
					swapPaddle ^= 1;
				}
			});
			swapBtn.setPosition(652, 480 - 266);
			
			addActor(swapBtn);
			
			timerHurry = new KTimer(level.getMaxTime(), false, false) {

				@Override
				protected void onOver() {
					level.onTimeUp();
				}

			};
			
			timerReady = new KTimer(1f) {

				@Override
				protected void onStart() {
					scrReady.show();
					level.onGetReady();
				}
				
				@Override
				protected void onOver() {
					ready = true;
					scrReady.hide();
					audio.play(assets.music.bgm_stage[LevelUtils.levelGetWorld(levelNumber)]);
					timerHurry.start();
				}
				
			};

			addListener(new InputListener() {
				@Override
				public boolean keyDown(InputEvent event, int keycode) {
					if (keycode == Input.Keys.BACK || keycode == Input.Keys.ENTER) {
						if (paused) {
							returnToLevelScreen();
						} else {
							pauseGame();
						}
					}
					return true;
				}
			});
		}
		
		public float getCurrentTime() {
			return timerHurry.getPercent() * level.getMaxTime();
		}
		
		protected KStillActor addInvisibleScreen(TextureRegion reg) {
			KStillActor actor = new KStillActor(reg, ViewCenter);
			actor.addToStage(this);
			actor.setVisible(false);
			return actor;
		}
		
		protected void displayNumber(Batch batch, int startX, int startY,
				int x, int digits) {
			// fix y
			startY = Constants.VIEW_HEIGHT - (startY
					+ regDigits[0][0].getRegionHeight());

			while ((digits--) > 0 || x > 0) {
				int tmp = x % 10;
				x /= 10;
				TextureRegion r = regDigits[0][(tmp >= 1) ? (tmp - 1) : 9];
				startX -= r.getRegionWidth();
				batch.draw(r, startX, startY);
			}
		}
		
		@Override
		protected void onRender(float delta, Batch batch) {
			
			batch.begin();
			
			if (colTemp != null) {
				
				Iterator<ParticleEffect> it = colTemp.iterator();
				while (it.hasNext()) {
					ParticleEffect pe = it.next();
					if (!paused) pe.draw(batch);
					pe.update(delta);
					if (pe.isComplete()) {
						it.remove();
					}
				}
			}
			
			scrPause.setVisible(paused && ready && !ended);
			swapBtn.setVisible(!(paused && ready && !ended));
			
			if (!paused) {
				if (ready)
					KTimer.tick(timerHurry, delta);
				else
					KTimer.tick(timerReady, delta);
			} else if (paused && Gdx.input.isTouched()) {
				// gamePaused == true and touched
				if (!ended) {
					resumeGame();
				}
			}

			if (/*!ended && */!paused && ready) {
				// time
				displayNumber(batch, 212, 29, (int) timerHurry.getTicks() % 60,
						2);
				displayNumber(batch, 160, 29, (int) timerHurry.getTicks() / 60,
						2);
				// score
				displayNumber(batch, 768, 29, level.getCurrentScore(), 1);
				// lives
				displayNumber(batch, 768, 432, level.getLives(), 1);
				// stage number
				displayNumber(batch, 138, 432, LevelUtils.levelGetWorld(levelNumber), 1);
				displayNumber(batch, 175, 432, LevelUtils.levelGetScene(levelNumber), 1);
			} else if (ended && victory) {
				// time
//				displayNumber(batch, 240 + 40, 137,
//						(int) timerHurry.getTicks() % 60, 2);
//				displayNumber(batch, 240 - 5, 137,
//						(int) timerHurry.getTicks() / 60, 2);
				// score
//				displayNumber(batch, 725, 137, level.getCurrentScore(), 1);
			}
			
			batch.end();
			
			// The game is on.
			for (BasicBall b : balls.enumAll())
				if (b != null && b.isPresent()) {
					if (!b.isShot()) {
						b.stick();
						swapBtn.setTouchable(Touchable.enabled);
					}else {
						swapBtn.setTouchable(Touchable.disabled);
					}
					if (b.isReadyToShoot() && ready && Gdx.input.justTouched() && !isMouseOverUI(curCursor)) {
						b.requestShoot();
					} 
				}
		}
		
		public boolean isMouseOverUI(Point2D cursor) {
			if (RealOp.epsSignByDiff(CenterOnScreen.dispTo_flipY(cursor).cosineBetween(Vector2D.PosY), 0.994309f) >= 0   )
				return true;
			return false;
		}

		public void control() {
			curSample = Gdx.input.getAccelerometerY();
			addSample(curSample);
			curCursor.set(Gdx.input.getX(), Gdx.input.getY());
		}
		
		private void addSample(float current) {
			for (int i = 0; i < lastValues.length - 1; i++)
				lastValues[i] = lastValues[i + 1];

			lastValues[lastValues.length - 1] = current;
		}
		
		public float getCurrentSample() {
			return curSample;
		}
		
		private float getSmoothedSample() {
			float sum = 0;
			for (int i = 0; i < lastValues.length; i++)
				sum += lastValues[i];
			return sum / lastValues.length;
		}
		
		public Vector2D getPaddleAngle() {
			if (!gravity) {
				// touch mode
				if (curCursor.distCmp(CenterOnScreen, CIRCLE_MIN_OFFSET) < 0)
					return null;
				return CenterOnScreen.dispTo_flipY(curCursor);
			}else {
				// gravity mode
				return Vector2D.makeUnitVector(getSmoothedSample() * DEFAULT_MULTIPLIER / 10.0f * (MathUtils.PI / 2) - MathUtils.PI / 2);
			}
		}
	}
	
	// GAME CONTROL
	
	private void newGame(String levelName) {
		resetGame();
		if (initGame(levelName)) {
			startGame();
		}
	}

	private void resetGame() {
		walls.clean();
		balls.clean();
		paddles.clean();
		balloons.clean();

		level = null;
		map = null;
		
		this.ready = false;
		this.ended = false;
		this.victory = false;
		// notes: don't change pause.
	}
	
	private boolean initGame(String levelName) {
		this.levelNumber = LevelUtils.levelNameToNum(levelName);
		
		// init collision effects
		collisionEffect = new ParticleEffect();
		collisionEffect.load(Gdx.files.internal("data/collision.p"),
				Gdx.files.internal("data/"));
		collisionEffectPool = new ParticleEffectPool(collisionEffect, 20, 50);
		colTemp = new LinkedList<ParticleEffect>();

		String mapName = null;
		// find map and level controller
		switch (levelNumber) {
		case 0:
			mapName = "Test only";
			this.level = new BasicLevel();
			break;
		case 2:
			mapName = "Faze";
			this.level = new BasicLevel();
			break;
		case 1:
			mapName = "Joystick";
			this.level = new BasicLevel();
			break;
		case 3:
			mapName = "Trashcan";
			this.level = new BasicLevel();
			break;
		case 11:
			mapName = "20130";
			this.level = new BasicLevel();
			break;
			
		case 14:
			mapName = "Wave";
			this.level = new BasicLevel();
			break;
		case 15:
			mapName = "wifi";
			this.level = new BasicLevel();
			break;
		case 10:
			mapName = "demon";
			this.level = new BasicLevel();
			break;
		case 12:
			mapName = "octangle";
			this.level = new BasicLevel();
			break;
		case 5:
			mapName = "uchiha";
			this.level = new BasicLevel();
			break;
		case 8:
			mapName = "superman";
			this.level = new BasicLevel();
			break;
		case 4:
			mapName = "ship";
			this.level = new BasicLevel();
			break;
		case 6:
			mapName = "funnel";
			this.level = new BasicLevel();
			break;
		case 16:
			mapName = "cup";
			this.level = new BasicLevel();
			break;
		case 9:
			mapName = "sword";
			this.level = new BasicLevel();
			break;
		case 7:
			mapName = "fish";
			this.level = new BasicLevel();
			break;
		case 13:
			mapName = "fascist";
			this.level = new BasicLevel();
			break;
		case 17:
			mapName = "sea";
			this.level = new BasicLevel();
			break;
		}
		if (mapName == null) {
			Gdx.app.error("GSCREEN", "No level called "+levelName);
			return false;
		}else {
			this.map = KMapManager.getInstance().findMap(mapName);
			if (map == null) {
				Gdx.app.error("GSCREEN", "Map not found: "+mapName);
				return false;
			}else {
				buildObjects();
				return true;
			}
		}
	}
	
	private void startGame() {
		ui = new UIStage();
		
		resumeGame();
	}
	
	private void pauseGame() {
		if (!paused) {
			paused = true;
			
			Gdx.app.log("GSCREEN", "PAUSED");
			// pause stages
			balls.pause();
			bricks.pause();
			paddles.pause();
			balloons.pause();

			// pause timers
		}
	}
	
	private void resumeGame() {
		if (paused) {
			paused = false;
			
			Gdx.app.log("GSCREEN", "RESUMED");
			// resume stages
			balls.resume();
			bricks.resume();
			paddles.resume();
			balloons.resume();
			
			// resume timers
		}
	}
	
	private void endGame(boolean won) {
		assert( !ended );
		
		pauseGame();
		
		balls.removeAll();
		balloons.clean();
		
		this.victory = won;
		this.ended = true;
		
		if (won) {
			String levelName = LevelUtils.levelNumToName(this.levelNumber);
			int world = LevelUtils.levelGetWorld(levelNumber);
			int scene = LevelUtils.levelGetScene(levelNumber);
			
			// SAVE RECORDS
			int bestScore, bestTime;
			bestScore = GamePreferences.getInstance().getScore(levelName, this.gravity);
			bestTime = GamePreferences.getInstance().getTime(levelName, this.gravity);
			
			if (bestScore < level.getCurrentScore()) {
				// new record
				bestScore = level.getCurrentScore();
				GamePreferences.getInstance().saveScore(levelName, bestScore, this.gravity);
			}
			if (bestTime > (int)ui.getCurrentTime()) {
				// new record
				bestTime =(int) ui.getCurrentTime();
				GamePreferences.getInstance().saveTime(levelName, bestTime, this.gravity);
			}
			String n1 = LevelUtils.levelNextLevel1(world, scene);
			String n2 = LevelUtils.levelNextLevel2(world, scene);
			if (n1 != null) GamePreferences.getInstance().setStageOpen(n1, true);
			if (n2 != null) GamePreferences.getInstance().setStageOpen(n2, true);
			
			ScreenTransition transition = ScreenTransitionSlice.init(1, ScreenTransitionSlice.UP_DOWN, 10, Interpolation.sine);
			game.setScreen(new PassScreen(game, (int)ui.getCurrentTime(), bestTime, world, (char)(scene + 'a' -1) ,  level.getMaxLives() - level.getLives() + 1 ),transition,false);
		}else {
			ScreenTransition transition = ScreenTransitionFade.init(0.3f);
			game.setScreen(new GameOverScreen(game), transition,false);
		}
	}
	
	private void buildObjects() {
		assert( map != null );
		
		Gdx.app.log("GSCREEN", "Loading map: "+map.getName());
		// build bricks
		Iterator<BrickToken> itb = map.enumBrickTokens();
		while (itb.hasNext()) {
			final BrickToken token = itb.next();
			BasicBrick b = null;
			if (token instanceof TShooter) {
				b = new ShooterBrick((TShooter)token);
			}else if (token instanceof TBlackHole) {
				b = new BlackHoleBrick((TBlackHole)token);
				bricks.add(((BlackHoleBrick)b).getPartner()); // add its partner to stage as well
			}else if (token instanceof TContainerBrick) {
				b = new ContainerBrick((TContainerBrick)token);
			}else if (token instanceof TCounterBrick) {
				// normal brick is also counter brick
				b = new CounterBrick(assets.objs.brick, (TCounterBrick)token);
			}else if (token instanceof THardBrick) {
				b = new HardBrick((THardBrick)token);
			}
			if (b == null) {
				Gdx.app.error("GSCREEN", "Unknown type of brick: "+token.toString());
			}else {	
				bricks.add(b);
			}
		}
		// build walls
		Iterator<WallToken> itw = map.enumWallTokens();
		while (itw.hasNext()) {
			final WallToken token = itw.next();
			BasicDivider d = null;
			if (token instanceof TContinuousWall) {
				TContinuousWall w = (TContinuousWall)token;
				for (int i = 0; i < w.getWallCount(); i++) {
					d = new DefaultDivider(w ,i);
					walls.add(d);
				}
				for (int i = 0; i < w.getPointCount(); i++) {
					BasicBrick b = new PivotBrick(new THardBrick(w.getPoint(i).x, w.getPoint(i).y));
					b.setScale(d.getActualWidth() / b.getActualWidth());
					bricks.add(b);
				}
				continue;
			}
			if (d == null) {
				Gdx.app.error("GSCREEN", "Unknown type of wall: "+token.toString());
			}else {	
				walls.add(d);
			}
		}
		
		ILevelConfig level = this.level;
		// create paddles
		for (int i = 0; i < level.getPaddleCount(); i++) {
			BasicPaddle p = new DefaultPaddle(i);
			p.resetAll();
			paddles.add(p);
		}
		// create balls
		for (int i = 0; i < level.getBallCount(); i++) {
			BasicBall b = new DefaultBall(i);
			b.resetAll(paddles.get(i));
			balls.add(b);
		}
		// create shadow balls
		for (int i = 0; i < level.getBallCount(); i++) balls.add((BasicBall)null);
	}
	
	// HIT & COLLISION TEST
	
	private boolean isInsideCircle(ICircle obj) {
		return obj.getCenter().distCmp(Point2D.Origin,
				CIRCLE_RADIUS_INNER - obj.getRadius()) < 0;
	}
	
	private boolean isOffScreen(KCircleActor obj) {
		final float x = obj.getActualX();
		final float y = obj.getActualY();
		final float r = obj.getRadius();
		return (x+r<0 || x-r>Constants.VIEW_WIDTH || y+r<0 || y-r>Constants.VIEW_HEIGHT);
	}
	
	private boolean isOutOfReach(ICircle obj) {
		// true if any paddle can't reach this
		for (BasicPaddle p : paddles.enumAll()) {
			if (!p.isOutOfReach(obj))
				return false;
		}
		return true;
	}

	private boolean itemCatchTest(BasicPaddle p) {
		// called by BasicPaddle::onAct
		for (BasicBalloon b : balloons.enumAll()) {
			if (b == null || !b.isPresent())
				continue;
			
			if (isInsideCircle(b)) // still inside the circle, can't be caught
				continue;
			else if (!isOutOfReach(b)) {
				if (intersectTest(p, b, b.getVelocity()) != null) {
					b.onCatch(p);
					level.onCatchBalloon();
					b.dispose();
				}
			} else if (isOffScreen(b)) {
				b.onMiss();
				b.dispose();
			}
			// The balloon will be removed when its act() is called.
		}
		return true;
		
	}

	private void showShadowBall(BasicBall refBall) {
		int id = level.getBallCount() + refBall.id;
		if (balls.get(id) == null && refBall.isPresent() && refBall.isMoving()) {
			BasicBall shadow = new DefaultBall(id);
			balls.set(id, shadow);
			shadow.setCenter(refBall.getCenter());
			Vector2D rndV = refBall.getVelocity().copy();
			rndV.reverse();
			shadow.resetAll(null);
			shadow.shoot(rndV);
			shadow.addToStage(balls);
			level.newBall();
		}
	}

	private boolean itemBreakTest(BasicBalloon balloon) {
		for (BasicDivider d : walls.enumAll()) {
			if (d.isPresent() && intersectTest(d, balloon, balloon.getVelocity()) != null) {
				balloon.onBreak();
				balloon.dispose();
				return true;
			}
		}
		return false;
	}
	
	private boolean ballHitTest(BasicBall ball) {
		if (isInsideCircle(ball)) {
			for (BasicBrick brick : bricks.enumAll()) {
				// check whether the ball is still present
				if (!ball.isPresent()) break;
				
				if (brick.isPostTest()) continue;
				if (brick.isPresent() && hitAndBounce(brick, ball, ball))
					return true;
			}
		}
		return false;
	}

	private boolean ballMissTest(BasicBall ball) {
		if (isOutOfReach(ball) || isOffScreen(ball)) {
			level.onLoseBall(ball.id);
			return true;
		} else
			return false;
	}

	private boolean ballBounceTest(BasicBall ball) {
		if (isInsideCircle(ball)) {
			// Post-Test Brick Hit Test
			for (BasicBrick brick : bricks.enumAll()) {
				if (brick.isDestroyed() && brick.isPostTest() && brick.isPresent()) {
					if (hitAndBounce(brick, ball, ball))
						return true;
				}
			}
			
			// Divider Bounce Test
			for (Divider d : walls.enumAll()) {
				if (d.isPresent() && hitAndBounce(d, ball, ball))
					return true;
			}
		} else { // not out of reach
			// Paddle Bounce Test
			for (BasicPaddle paddle : paddles.enumAll()) {
				if (paddle.isPresent() && hitAndBounce(paddle, ball, ball)) {
					level.onCatchBall(ball.id);
					return true;
				}
			}
			// Shield Bounce Test
			Shield shield = desk.getShield();
			if (shield != null && hitAndBounce(shield, ball, ball)) {
				level.onCatchBall(ball.id);
				return true;
			}
			shield = desk.getNShield();
			if (shield != null && hitAndBounce(shield, ball, ball)) {
				level.onCatchBall(ball.id);
				return true;
			}
			
			
		}
		return false;
	}
	
	private static Point2D intersectTest(IHittable hittee, ICircle hitter, Vector2D in) {
		return hittee.intersectTest(hitter, in);
	}

	private boolean hitAndBounce(IHittable hittee, ICircle hitter,
			IMovable hitter2) {
		
		Point2D intersection = hittee.intersectTest(hitter, hitter2.getVelocity());
		if (intersection != null) {
			Vector2D reflect = hittee.hitTest(hitter, intersection, hitter2.getVelocity());
			if (reflect != null) {
//				float dist = intersection.distTo(hitter.getCenter());
//				Gdx.app.log("COLLISION", "Adjust "+dist+" px");
//				if (dist > 40) {
//					pauseGame();
//				}
				hitter.setCenter(intersection);
				hitter2.setVelocity(reflect);
				return true;
			}
		}
		return false;
	}

	// LEVEL CONTROL
	
	private class BasicLevel extends Level {

		@Override
		public void onGetReady() {
			System.out.println("GET READY!");
		}

		@Override
		public void onTimeUp() {
			System.out.println("TIME UP");
			onGameOver();
		}

		@Override
		public void onGameOver() { // lose
			System.out.println("GAME OVER");
			endGame(false);
		}

		@Override
		public void onGameWin() {
			addScore(getLives() * 2000);
			System.out.println("YOU WIN!");
			
			// TOOD: SAVE RECORD

			endGame(true);
		}

		@Override
		public void onCatchBall(int id) {
			System.out.println("You caught ball " + id + ".");
			addScore(50);
		}

		@Override
		public void onCatchBalloon() {
			System.out.println("You caught a balloon.");
			addScore(15);
		}

		@Override
		public void onLoseBall(int id) {
			System.out.println("Oops! Ball " + id + " was lost.");
			super.onLoseBall(id);
			if (getLives() > 0 && isBuiltInBall(id)) { // lose built-in balls
				// prepare another ball
				balls.get(id).resetAll(paddles.get(id));
				// reset paddles
				for (BasicPaddle p : paddles.enumAll()) {
					p.resetAll();
				}
				System.out.println("Remaining lives: " + getLives());
			} else
				balls.destroy(id);
		}

		protected boolean allBlocksDestroyed() {
			for (BasicBrick b: bricks.enumAll()) {
				if (!b.isDestroyed())
					return false;
			}
			return true;
		}

		@Override
		public void onDestroyBrick() {
			System.out.println("Block destroyed");
			addScore(75);
			// Win Criteria Test
			if (allBlocksDestroyed()) {
				onGameWin();
			}
		}
	}

}
