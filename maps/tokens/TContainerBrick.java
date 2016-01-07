package com.omnibounce.maps.tokens;

public class TContainerBrick extends TCounterBrick {

	public static final float DEFAULT_TRANSPARENT_ALPHA = 0.5f;
	
	private final float opacity;
	private final ItemType type;
	
	public TContainerBrick(float x, float y, ItemType type) {
		this(x, y, 1, DEFAULT_TRANSPARENT_ALPHA, type);
	}

	public TContainerBrick(float x, float y, int hits, ItemType type) {
		this(x, y, hits, DEFAULT_TRANSPARENT_ALPHA, type);
	}

	public TContainerBrick(float x, float y, int hits, float alpha, ItemType type) {
		super(x, y, hits);
		this.opacity = alpha;
		assert (type != ItemType.NONE);
		this.type = type;
	}
	
	public final float getBalloonOpacity() {
		return opacity;
	}
	
	public final ItemType getItemType() {
		return type;
	}

}
