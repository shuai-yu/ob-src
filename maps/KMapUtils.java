package com.omnibounce.maps;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.omnibounce.maps.tokens.BrickToken;
import com.omnibounce.maps.tokens.ItemType;
import com.omnibounce.maps.tokens.TContainerBrick;
import com.omnibounce.maps.tokens.TCounterBrick;
import com.omnibounce.maps.tokens.TShooter;
import com.omnibounce.math.Point2D;

public final class KMapUtils {

	private final int diff;
	
	public KMapUtils(int diff) {
		this.diff = diff;
	}
	
	public BrickToken anyBrick(Point2D p) {
		return anyBrick(p.x, p.y);
	}
	
	public BrickToken anyBrick(float x, float y) {
		return anyBrick(new Point2D(x, y), MathUtils.random(1, Math.min(1 + (diff - 1) / 2, 3)));
	}
	
	public ItemType anyType() {
		float rnd = MathUtils.random();
		rnd -= 0.05; if (rnd <= 0) return ItemType.ADDLIFE;
		rnd -= 0.08; if (rnd <= 0) return ItemType.EXPAND;
		rnd -= 0.02 * diff; if (rnd <= 0) return ItemType.SHRINK;
		rnd -= 0.05; if (rnd <= 0) return ItemType.MANGET;
		rnd -= 0.01 * diff; if (rnd <= 0) return ItemType.POWERMAX;
		rnd -= 0.04; if (rnd <= 0) return ItemType.POWERUP;
		rnd -= 0.01 * diff; if (rnd <= 0) return ItemType.REVERSE;
		rnd -= 0.06; if (rnd <= 0) return ItemType.SHIELD;
		rnd -= 0.02 * diff; if (rnd <= 0) return ItemType.SPEEDUP;
		rnd -= 0.05; if (rnd <= 0) return ItemType.SPEEDDOWN;
		rnd -= 0.025; if (rnd <= 0) return ItemType.SPLIT;
		rnd -= 0.02; if (rnd <= 0) return ItemType.TRANSPARENT;
		rnd -= 0.4; if (rnd <= 0) return ItemType.NONE;
		return ItemType.UNKNOWN;
		
	}
	
	public static Color toColor(int webColor) {
		return new Color((float)(webColor>>>16)/256, (float)((webColor>>>8)&255)/256, (float)(webColor&255)/256, (float)0.8);
		
	}
	
	public BrickToken anyBrick(Point2D p, int hits) {
		
		BrickToken bb;
		
		ItemType type = anyType();
		if (type != ItemType.NONE && type != ItemType.UNKNOWN)
			bb = new TContainerBrick(p.x, p.y, hits, type);
		else if (type == ItemType.NONE || diff < 3)
			bb = new TCounterBrick(p.x, p.y, hits);
		else
			// only in difficult levels
			bb = new TShooter(p.x, p.y);
		
		switch (diff) {
			case 1:
				bb.setColor(toColor(0x0eaaf4));
				break;
			case 2:
				bb.setColor(toColor(0x8cffc8));
				break;
			case 3:
				bb.setColor(toColor(0xff169e));
				break;
			case 4:
				bb.setColor(toColor(0xf20d06));
				break;
			case 5:
				bb.setColor(toColor(0xd2d2d2));
				break;
		}
		
		return bb;
	}

}
