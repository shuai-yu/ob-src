package com.omnibounce.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.omnibounce.drawobject.KActor;
import com.omnibounce.interfaces.ICircle;
import com.omnibounce.interfaces.IHittable;
import com.omnibounce.math.Line2D;
import com.omnibounce.math.Physics2D;
import com.omnibounce.math.Point2D;
import com.omnibounce.math.RealOp;
import com.omnibounce.math.Vector2D;

public abstract class Divider extends KActor implements IHittable {

	private final Line2D l, l1, l2;
	private final Vector2D n1, n2;
	private final float width;

	public Divider(TextureRegion reg, Point2D p1, Point2D p2, float width) {
		useTextureAndOriginCenter(reg);
		
		this.l = new Line2D(p1, p2);
		this.width = width;
		this.n1 = l.getOneNormal().copy().normalize();
		this.n2 = n1.copy().reverse();
		// translate
		this.l1 = new Line2D(p1.forwardToN(n1, width/2), p2.forwardToN(n1, width/2));
		this.l2 = new Line2D(p1.forwardToN(n2, width/2), p2.forwardToN(n2, width/2));
		
		setPos();
	}

	private void setPos() {
		setScale(width / getOrgWidth(), l.length() / getOrgHeight());
		assert(width == getActualWidth());
		setRotation(RealOp.rad2Deg(l.getOneDirection()) + 90);
		setOffset(l.getMiddle());
	}
	
	public Vector2D getOneNormal() {
		return n1;
	}

	public Line2D whichLine(Point2D p, Vector2D incidence) {
		switch (RealOp.epsSign(n1.cosineBetween(incidence))) {
		case -1:
			return l1;
		case 1:
			return l2;
		case 0:
			// TODO : more accurate
			return l1.distTo(p)<=l2.distTo(p)?l1:l2;
		default:
			return null;
		}
	}
	
	@Override
	public Point2D intersectTest(ICircle obj, Vector2D incidence) {
		
		Point2D O = obj.getCenter();
		Line2D l = whichLine(O, incidence);
		
		float dLO = l.distTo(O);
		
		if (RealOp.epsSignByDiff(dLO, obj.getRadius()) >= 0) 
			return null;
	
		if (!l.isBetween(O) && !l.isBetween(obj))
			return null;
		
		float sine = Math.abs(incidence.sineBetween(l.getOneVector()));
		
		if (RealOp.epsIsPositive(sine)) {
			float offset = obj.getRadius() - dLO;
			Point2D p = O.forwardTo(incidence, -offset / sine);

			if (!l.isBetween(p)) {
				// adjust too much
				return null;
			}
				
			else
				return p;
		}
		return O;
	}
	
	@Override
	public Vector2D hitTest(ICircle obj, Point2D intersection, Vector2D incidence) {
		
		return onHit(obj, intersection, incidence,
				Physics2D.getReflectedVector(incidence, (whichLine(obj.getCenter(), incidence)==l1)?n1:n2 ));

	}
	
	@Override
	public Vector2D onHit(ICircle obj, Point2D intersection, Vector2D incidence,
			Vector2D reflected) {
		
		return reflected;
	}
	
	@Override
	protected void onAct(float delta) {
		
	}
	
	
}
