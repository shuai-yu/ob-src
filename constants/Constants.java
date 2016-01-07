package com.omnibounce.constants;

import com.badlogic.gdx.utils.Array;

public class Constants {

	// preferences
	public static final String PREFERENCES = "preferences";

	public static final int VIEW_WIDTH = 800;
	public static final int VIEW_HEIGHT = 480;

	public static final String TEXTURE_ATLAS_LIBGDX_UI = "images/uiskin.atlas";
	// Location of description file for skins
	public static final String SKIN_LIBGDX_UI = "images/uiskin.json";

	// atlas
	public static final String MENU_TEXTURE_ATLAS = "atlas/menuAtlas.pack";
	public static final String LEVEL_TEXTURE_ATLAS = "atlas/levelAtlas.pack";
	public static final String ITEM_TEXTURE_ATLAS = "atlas/itemAtlas.pack";
	public static final String OBJECT_TEXTURE_ATLAS = "atlas/objectAtlas.pack";
	

	public static final String level1 = "level1a";
	public static final String level2 = "level2a";
	public static final String level3 = "level2b";
	public static final String level4 = "level3a";
	public static final String level5 = "level3b";
	public static final String level6 = "level3c";
	public static final String level7 = "level3d";
	public static final String level8 = "level4a";
	public static final String level9 = "level4b";
	public static final String level10 = "level4c";
	public static final String level11 = "level4d";
	public static final String level12 = "level4e";
	public static final String level13 = "level5a";
	public static final String level14 = "level5b";
	public static final String level15 = "level5c";
	public static final String level16 = "level5d";
	public static final String level17 = "level5e";
	
	public static final int LINES_SUM_NUMBER = 19; 

	public static Array<String> getAllLevelList() {
		Array<String> list = new Array<String>();
		list.add(level1);
		list.add(level2);
		list.add(level3);
		list.add(level4);
		list.add(level5);
		list.add(level6);
		list.add(level7);
		list.add(level8);
		list.add(level9);
		list.add(level10);
		list.add(level11);
		list.add(level12);
		list.add(level13);
		list.add(level14);
		list.add(level15);
		list.add(level16);
		list.add(level17);

		return list;
	}

}
