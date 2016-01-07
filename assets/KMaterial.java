package com.omnibounce.assets;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class KMaterial {

//	// Singleton Pattern
//	private KMaterial() {
//	}
//	
//	private static KMaterial singleton = null;
//	
//	public synchronized static KMaterial getInstance() {
//		if (singleton == null) {
//			singleton = new KMaterial();
//		}
//		return singleton;
//	}
//	
//	// Normal Pattern
//	public static KMaterial newInstance() {
//		return new KMaterial();
//	}
//	
	// Manager
	private Map<String, TextureRegion> mapRegions = new HashMap<String, TextureRegion>();
	
	public void add(String name, TextureRegion region) {
		mapRegions.put(name, region);
	}
	
	public TextureRegion get(String name) {
		if (!mapRegions.containsKey(name))
			System.out.println(name + " doesn't exist!");
		assert( mapRegions.containsKey(name) );
		return mapRegions.get(name);
	}
	
	public void dispose() {
		mapRegions.clear();
		
	}
}
 