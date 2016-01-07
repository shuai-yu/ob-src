package com.omnibounce.maps.tokens;

public class TCounterBrick extends BrickToken {

	private final int hits;
	
	public TCounterBrick(float x, float y, int hits) {
		super(x, y);
		
		assert(hits > 0);
		this.hits = hits;
	}

	public final int getHits() {
		return hits;
	}
}
