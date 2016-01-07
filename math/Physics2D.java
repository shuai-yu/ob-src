package com.omnibounce.math;

import com.omnibounce.interfaces.ICircle;

public final class Physics2D {

	public static Vector2D getReflectedVector(Vector2D incidence, Vector2D normal) {
		Vector2D n = normal.copyt().normalize();
		n = n.scalarMul(n.dotProduct(incidence)*2);
		return incidence.copy().sub(n);
	}
	
	public static Point2D adjustPositionOO(ICircle hitter, Vector2D incidence, ICircle hittee, float desiredDistance) {
		
		// hitter = ball
		// hiitee = brick
		
		// TODO: fix bugs
		
		Point2D O1 = hitter.getCenter();
		Point2D O = hittee.getCenter();
		
		Vector2D o1o = O1.dispTo(O); // O1->O
		Vector2D oh = o1o.projectOn(incidence); // O1->H
		oh.addMul(o1o, -1); // O->H = O1->H - O1->O
		float d2OH = oh.length2();
//		if (!RealOp.epsIsEqual(d2OH, hittee.getRadius() * hittee.getRadius())) {
//			System.out.println("ALGORITHM WRONG "+(Math.sqrt(d2OH)-hittee.getRadius()));
//		}
		float d2OP = desiredDistance * desiredDistance;
		float d2HP = d2OP - d2OH;
		if (RealOp.epsSign(d2HP)<0) {
			System.out.println("ALGORITHM WRONG "+d2HP);
		}
		
		Point2D P = new Point2D(O.x + oh.x, O.y + oh.y); // H
		P = P.forwardTo(incidence, -(float)Math.sqrt(d2HP) * (float)RealOp.epsSign(desiredDistance)); // P
		// hitter.setCenter(P);
		// original center is O1
		// assert( 0 == this.getCenter().distCmp(obj.getCenter(), d2OP));

//		if (0 != hittee.getCenter().distCmp(P, Math.abs(desiredDistance))) {
//			System.out.println("CALC WRONG "+hittee.getCenter().distTo(P)+" <> "+desiredDistance);
//		}
//		
		return P;
	}
	
	public static boolean overlapCircleCircle(ICircle c1, ICircle c2) {
		return RealOp.epsSignByDiff(c1.getCenter().distTo2(c2.getCenter()), RealOp.sumSquare(c1.getRadius(), c2.getRadius())) < 0; 
	}

}
