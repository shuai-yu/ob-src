package com.omnibounce.maps;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.omnibounce.maps.tokens.BrickToken;
import com.omnibounce.maps.tokens.WallToken;

public abstract class KMap {
	
	// Map info
	protected final String name;
	protected final String author;
	protected final int difficulty;
	
	// Map data
	protected List<BrickToken> brickTokens = new LinkedList<BrickToken>();
	protected List<WallToken> wallTokens = new LinkedList<WallToken>();
	protected List<MapObjectToken> allTokens = new LinkedList<MapObjectToken>();
	
	// Utilities
	protected final KMapUtils utils;
	
	// Template method
	protected abstract void build();
	
	public KMap(String name, String author, int difficulty) {
		this.name = name;
		this.author = author;
		this.difficulty = difficulty;
		this.utils = new KMapUtils(difficulty);
		
		build();
	}
	
	public final String getName() {
		return name;
	}
	
	public final String getAuthor() {
		return author;
	}
	
	public final int getDifficulty() {
		return difficulty;
	}
	
	// method chaining
	protected final KMap get() {
		return this;
	}
	
	// iterator
	public final Iterator<BrickToken> enumBrickTokens() {
		return brickTokens.iterator();
	}
	
	public final Iterator<WallToken> enumWallTokens() {
		return wallTokens.iterator();
	}
	
	public final Iterator<MapObjectToken> enumAllTokens() {
		return allTokens.iterator();
	}
	
	// builder
	protected KMap addBrick(BrickToken brick) {
		brickTokens.add(brick);
		return this;
	}
	
	protected KMap addWall(WallToken wall) {
		wallTokens.add(wall);
		return this;
	}
	
	protected KMap add(MapObjectToken obj) {
		if (obj instanceof BrickToken) {
			addBrick((BrickToken)obj);
		}else if (obj instanceof WallToken) {
			addWall((WallToken)obj);
		}else {
			Gdx.app.log("MAP", "Unknown map object. BE CAREFUL!");
		}
		allTokens.add(obj);
		return this;
	}
	
	@Deprecated
	protected KMap clear() {
		brickTokens.clear();
		wallTokens.clear();
		allTokens.clear();
		return this;
	}

}
