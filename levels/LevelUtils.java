package com.omnibounce.levels;

import com.omnibounce.constants.Constants;

public final class LevelUtils {

	public static String levelNumToName(int num) {
		switch (num) {
		case 1:
			return Constants.level1;
		case 2:
			return Constants.level2;
		case 3:
			return Constants.level3;
		case 4:
			return Constants.level4;
		case 5:
			return Constants.level5;
		case 6:
			return Constants.level6;
		case 7:
			return Constants.level7;
		case 8:
			return Constants.level8;
		case 9:
			return Constants.level9;
		case 10:
			return Constants.level10;
		case 11:
			return Constants.level11;
		case 12:
			return Constants.level12;
		case 13:
			return Constants.level13;
		case 14:
			return Constants.level14;
		case 15:
			return Constants.level15;
		case 16:
			return Constants.level16;
		case 17:
			return Constants.level17;
		default:
			return null;
		}
	}

	public static int levelNameToNum(String name) {
		if (name.equals(Constants.level1))
			return 1;
		if (name.equals(Constants.level2))
			return 2;
		if (name.equals(Constants.level3))
			return 3;
		if (name.equals(Constants.level4))
			return 4;
		if (name.equals(Constants.level5))
			return 5;
		if (name.equals(Constants.level6))
			return 6;
		if (name.equals(Constants.level7))
			return 7;
		if (name.equals(Constants.level8))
			return 8;
		if (name.equals(Constants.level9))
			return 9;
		if (name.equals(Constants.level10))
			return 10;
		if (name.equals(Constants.level11))
			return 11;
		if (name.equals(Constants.level12))
			return 12;
		if (name.equals(Constants.level13))
			return 13;
		if (name.equals(Constants.level14))
			return 14;
		if (name.equals(Constants.level15))
			return 15;
		if (name.equals(Constants.level16))
			return 16;
		if (name.equals(Constants.level17))
			return 17;
		return 0;
	}

	public static int levelGetWorld(int levelNumber) {
		int base;
		int levelWorld, levelScene;
		if (levelNumber <= 1) {
			levelWorld = 1;
			base = 0;
		} else if (levelNumber <= 3) {
			levelWorld = 2;
			base = 1;
		} else if (levelNumber <= 7) {
			levelWorld = 3;
			base = 3;
		} else if (levelNumber <= 12) {
			levelWorld = 4;
			base = 7;
		} else {
			levelWorld = 5;
			base = 12;
		}
		levelScene = levelNumber - base;
		return levelWorld;
	}

	public static int levelGetScene(int levelNumber) {
		int base;
		int levelWorld, levelScene;
		if (levelNumber <= 1) {
			levelWorld = 1;
			base = 0;
		} else if (levelNumber <= 3) {
			levelWorld = 2;
			base = 1;
		} else if (levelNumber <= 7) {
			levelWorld = 3;
			base = 3;
		} else if (levelNumber <= 12) {
			levelWorld = 4;
			base = 7;
		} else {
			levelWorld = 5;
			base = 12;
		}
		levelScene = levelNumber - base;
		return levelScene;
	}

	public static String levelNextLevel1(int levelWorld, int levelScene) {

		int a = -1, b = -1;

		if (levelWorld <= 2) {
			a = 1 + (levelScene - 1) * 2;
			b = 2 + (levelScene - 1) * 2;
		} else if (levelWorld == 3) {
			a = levelScene;
			b = levelScene + 1;
		} else if (levelWorld == 4) {
			a = levelScene;
		}

		if (a != -1)
			return "level" + (levelWorld + 1) + (char) (a + 'a' - 1);
		else
			return null;
	}

	public static String levelNextLevel2(int levelWorld, int levelScene) {

		int a = -1, b = -1;

		if (levelWorld <= 2) {
			a = 1 + (levelScene - 1) * 2;
			b = 2 + (levelScene - 1) * 2;
		} else if (levelWorld == 3) {
			a = levelScene;
			b = levelScene + 1;
		} else if (levelWorld == 4) {
			a = levelScene;
		}

		if (b != -1)
			return "level" + (levelWorld + 1) + (char) (b + 'a' - 1);
		else
			return null;
	}

}
