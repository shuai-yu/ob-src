package com.omnibounce.maps;

import java.util.HashMap;
import java.util.Map;

public final class KMapManager {

	// Singleton
	private static KMapManager instance = null;
	
	// Maps storage
	private Map<String, KMap> maps = new HashMap<String, KMap>();
	
	private KMapManager() {
	}
	
	public static synchronized KMapManager getInstance() {
		if (instance == null) {
			instance = new KMapManager();
		}
		return instance;
	}
	
	public void loadBuiltInMaps() {
		// TODO: Add your map classes here
		addMap(new MapTest());
		
		addMap(new MapWave());
		addMap(new MapFaze());
		addMap(new MapTrashCan());
		addMap(new MapJoystick());
		addMap(new MapWifi());
		addMap(new MapCup());
		addMap(new MapDemon());
		addMap(new MapOctangle());
		addMap(new MapUchiha());
		addMap(new MapSuperman());
		addMap(new MapShip());
		addMap(new MapFascist());
		addMap(new MapFish());
		addMap(new MapSea());
		addMap(new Map20130());
		addMap(new MapFunnel());
		addMap(new MapSword());
	}
	
	// manager
	public KMapManager addMap(KMap map) {
		maps.put(map.getName(), map);
		return this;
	}
	
	public KMap findMap(String mapName) {
		return maps.get(mapName);
	}
}
