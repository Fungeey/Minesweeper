package Minesweeper;

import FileStreaming.Scores;
import Lookup.Utility;
import Screens.MenuScreen;
import Screens.Screen;
import Lookup.Sprites;
import processing.core.PApplet;

public class Main extends PApplet{
    public static PApplet mainClass;

    Screen screen;

    public Main(){
        mainClass = this;
    }

    private enum MainStates {
        MENU, CREATEGAME, GAME, FINISH, SCORES, ABOUT
    }
    private MainStates mainState = MainStates.MENU;

    // Game
    public Grid grid;

    public static int screenW, screenH;

    // Input
    private int x; // OBSOLETE - MOVED TO GAMESCREEN
    private int z; // OBSOLETE - MOVED TO GAMESCREEN
    private boolean xRelease; // OBSOLETE - MOVED TO GAMESCREEN
    private boolean zRelease; // OBSOLETE - MOVED TO GAMESCREEN
    private int chordMillis; // OBSOLETE - MOVED TO GAMESCREEN
    private boolean canFlag = true; // OBSOLETE - MOVED TO GAMESCREEN
    private final int chordBuffer = 1000; // OBSOLETE - MOVED TO GAMESCREEN

    public String nameInput;

    private int millis1; // OBSOLETE - MOVED TO GAMESCREEN

    public boolean startGame;
    int startMillis; // OBSOLETE - MOVED TO GAMESCREEN

    public int time = -1; // Timer on the game;

    public static void main(String[] args) {
        PApplet.main("Minesweeper.Main");
    }

    @Override
    public void settings() {
        size(1440, 768+96);
        screenW = 1440;
        screenH = 864;
    }

    @Override
    public void setup() {
        startGame = false;
		screen = new MenuScreen(this);

        noStroke();
        textAlign(CENTER);

        Sprites.loadSprites();
        Scores.loadData();

        cursor(Sprites.cursor, 0, 0);
    }

    public void ChangeScreen(Screen screen){
    	this.screen = screen;
    	System.out.println("New Screen: " + screen);
	}

	public void Shutdown(){
		System.exit(0);
	}

    @Override
    public void draw() {
        screen.update();
    }

    @Override
    public void keyPressed() {
		screen.keyPressed(keyCode);
    }


    @Override
    public void keyReleased() {
        screen.keyReleased(keyCode);
    }

    // OBSOLETED FUNCTION
    private void setKey(int key, int n){
        switch(key){
            case 'Z':
                z = n;

            case 'X':
                x = n;
        }
    }
}