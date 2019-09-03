package Lookup;

import Minesweeper.Main;
import processing.core.PImage;

public class Sprites {
	public static PImage[] difficultyLabels = new PImage[3];
	public static PImage[] sevenSegDisplay = new PImage[11];

	public static PImage[] fontCharacters = new PImage[26];
	public static PImage[] fontNumbers = new PImage[10];

	public static PImage[][] numbers = new PImage[2][8];

	public static PImage[] tiles = new PImage[4];
	public static PImage[] groundTiles = new PImage[5];
	public static PImage pressedTile = new PImage();

	public static PImage flag = new PImage();
	public static PImage cursor = new PImage();
	public static PImage mine = new PImage();

	public static PImage[] menuButtons = new PImage[4];
	public static PImage background = new PImage();
	public static PImage vignette = new PImage();
	public static PImage selectArrowR = new PImage();
	public static PImage selectArrowL = new PImage();

	public static PImage instructions = new PImage();
	public static PImage winBanner = new PImage();
	public static PImage loseBanner = new PImage();
	public static PImage anyKey = new PImage();
	public static PImage pressEnter = new PImage();
	public static PImage enterName = new PImage();
	public static PImage aboutText = new PImage();
	public static PImage titleSprite = new PImage();


	private final static String spriteDirectory = "Sprites/";
	private final static String fileType = ".png";

	public static void loadSprites(){
		difficultyLabels = loadSprites("difficulty", 3, "DifficultySprites");
		sevenSegDisplay = loadSprites("7Seg", 11, "7SegNumbers");
		loadFont();

		numbers[0] = loadSprites("Num", 8, "Numbers");
		numbers[1] = loadSprites("Numb", 8, "Numbers");

		tiles = loadSprites("Tile", 4, "Tiles");
		groundTiles = loadSprites("Ground", 3, "Tiles");
		pressedTile = loadSprite("PressedTile", "Tiles");

		flag = loadSprite("Flag", "Misc");
		cursor = loadSprite("Cursor", "Misc");
		mine = loadSprite("Mine", "Misc");

		menuButtons = loadSprites("menu", 4, "Menu");
		background = loadSprite("Background", "Menu");
		vignette = loadSprite("Vignette", "Menu");
		selectArrowR = loadSprite("ArrowR", "Menu");
		selectArrowL = loadSprite("ArrowL", "Menu");
		instructions = loadSprite("Instructions", "Menu");
		winBanner = loadSprite("Win", "Menu");
		loseBanner = loadSprite("Lose", "Menu");
		anyKey = loadSprite("PressAnyKey", "Menu");
		enterName = loadSprite("EnterAName", "Menu");
		pressEnter = loadSprite("PressEnter", "Menu");
		aboutText = loadSprite("Minesweeper About", "Menu");
		titleSprite = loadSprite("Title", "Menu");
	}

	private static PImage[] loadSprites(String name, int length){
		return loadSprites(name, length, "");
	}

	private static PImage[] loadSprites(String name, int length, String customDirectory){
		PImage[] arr = new PImage[length];
		for(int i = 1; i <= arr.length; i++){
			System.out.println("Loading image: " + spriteDirectory + customDirectory + name + i + fileType);
			arr[i-1] = Main.mainClass.loadImage(spriteDirectory + customDirectory + "/" + name + i + fileType);
		}
		return arr;
	}

	private static PImage loadSprite(String name){
		return loadSprite(name, "");
	}

	private static PImage loadSprite(String name, String customDirectory){
		return Main.mainClass.loadImage(spriteDirectory + customDirectory + "/" + name + fileType);
	}

	private static void loadFont(){
		int temp = 0;
		for(char ch = 'A'; ch <= 'Z'; ch++){
			fontCharacters[temp] = loadSprite(ch + "", "MinesweeperFont");
			temp++;
		}

		for(int i = 0; i < 10; i++){
			fontNumbers[i] = loadSprite(i + "", "MinesweeperFont");
		}
	}
}
