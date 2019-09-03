package Screens;

import Lookup.Sprites;
import Lookup.Utility;
import Minesweeper.Main;

public class GameScreen extends Screen{

	// Timing
	private int millis1;
	private int startMillis; // Time when you open the first mine (so the timer is correct)
	private int chordMillis; // Timer for the time between x and z presses
	private final int chordBuffer = 1000; // Number of millis allowed between z and x presses to count as a chord

	// Input
	private int x;
	private int z;
	private boolean xRelease;
	private boolean zRelease;

	// State
	private boolean canFlag = true;

	GameScreen(Main main){
		this.main = main;
		initialize();
	}

	@Override
	protected void initialize(){
		// Draw Background
		Main.mainClass.image(Sprites.background, 0, 0, Main.screenW, Main.screenH);
		Main.mainClass.pushStyle();
		Main.mainClass.tint(255, 150);
		Main.mainClass.image(Sprites.vignette, 0, 0, Main.screenW, Main.screenH);
		Main.mainClass.popStyle();

		Main.mainClass.fill(0, 75);
		Main.mainClass.rect(0, 0, Main.screenW, Main.screenH);
		Main.mainClass.fill(255);

		Main.mainClass.fill(30);
		Main.mainClass.rect(0, 0, 144, 96);
		Main.mainClass.rect(Main.screenW-144, 0, Main.screenW, 96);
		Main.mainClass.fill(255);

		// Draw Timer
		Main.mainClass.image(Sprites.sevenSegDisplay[0], Main.screenW-48, 0);
		Main.mainClass.image(Sprites.sevenSegDisplay[0], Main.screenW-96, 0);
		Main.mainClass.image(Sprites.sevenSegDisplay[0], Main.screenW-144, 0);

		// Draw Num bombs
		String num = Integer.toString(Math.abs(main.grid.numBombs - main.grid.flaggedBombs));

		while(num.length() < 3)
			num = "0" + num;

		if(main.grid.numBombs - main.grid.flaggedBombs < 0)
			num = "-" + num.substring(1);

		Main.mainClass.image(Sprites.sevenSegDisplay[Integer.parseInt(num.substring(2, 3))], 96, 0);
		Main.mainClass.image(Sprites.sevenSegDisplay[Integer.parseInt(num.substring(1, 2))], 48, 0);
		Main.mainClass.image(Sprites.sevenSegDisplay[Integer.parseInt(num.substring(0, 1))], 0, 0);

		main.grid.updateGrid(0, 0);
	}

	@Override
	public void update(){
		if(!main.grid.gameFinished && main.time != -1 && main.startGame) {

			Main.mainClass.fill(30);
			Main.mainClass.rect(0, 0, 144, 96);
			Main.mainClass.rect(Main.screenW-144, 0, Main.screenW, 96);
			Main.mainClass.fill(255);

			// Draw Timer
			String sec = String.format("%03d", Utility.clamp((Main.mainClass.millis()-startMillis+1000)/1000, 0, 999));
			main.time =  Utility.clamp((Main.mainClass.millis()-startMillis+1000)/1000, 0, 999);

			Main.mainClass.image(Sprites.sevenSegDisplay[Integer.parseInt(sec.substring(2, 3))], Main.screenW-48, 0);
			Main.mainClass.image(Sprites.sevenSegDisplay[Integer.parseInt(sec.substring(1, 2))], Main.screenW-96, 0);
			Main.mainClass.image(Sprites.sevenSegDisplay[Integer.parseInt(sec.substring(0, 1))], Main.screenW-144, 0);

			// Draw Num bombs
			String num = Integer.toString(Math.abs(main.grid.numBombs - main.grid.flaggedBombs));
			while(num.length() < 3) num = "0" + num;

			if(main.grid.numBombs - main.grid.flaggedBombs < 0) num = "-" + num.substring(1);

			for(int i = 0; i < 3; i++) {
				try {
					Main.mainClass.image(Sprites.sevenSegDisplay[Integer.parseInt(num.substring(i, i+1))], i*48, 0);
				} catch (NumberFormatException e) {
					Main.mainClass.image(Sprites.sevenSegDisplay[10], i*48, 0);
				}
			}
		}

		if(main.grid.gameFinished) {
			if (millis1 > 1000){
				main.ChangeScreen(new FinishScreen(main));

				startMillis = 0;
				main.nameInput = "";
			}else{
				millis1 += Main.mainClass.millis() / 1000;
			}
		}

		if(z == 2) main.grid.updateGrid(z, x);
	}

	@Override
	public void keyPressed(int keyCode){
		if(keyCode == 'Z') z = 2;
		else if(keyCode == 'X'){
			if(canFlag) {
				x = 1;
				canFlag = false;
			}
		}

		main.grid.updateGrid(z, x);

		if(!canFlag)
			x = 0;
	}

	@Override
	public void keyReleased(int keyCode){
		if(!main.startGame){
			main.time = 0;
			main.startGame = true;
			startMillis = Main.mainClass.millis();
		}

		if (keyCode == 'Z') {
			onReleaseZ();
		} else if (keyCode == 'X') {
			onReleaseX();
		}

		if (xRelease && zRelease) {
			onReleaseChord();
		} else {
			main.grid.updateGrid(z, x);
			z = 0;
			if (Main.mainClass.millis() - chordMillis > chordBuffer) {
				zRelease = false;
				xRelease = false;
				chordMillis = 0;
			}
		}
	}

	private void onReleaseZ(){
		z = 1;
		zRelease = true;

		if (chordMillis == 0) chordMillis = Main.mainClass.millis();
		else if (Main.mainClass.millis() - chordMillis > chordBuffer) {
			zRelease = false;
			xRelease = false;
			chordMillis = 0;
		}
	}

	private void onReleaseX(){
		canFlag = true;
		xRelease = true;

		if (chordMillis == 0) chordMillis = Main.mainClass.millis();
		else if (Main.mainClass.millis() - chordMillis > chordBuffer) {
			zRelease = false;
			xRelease = false;
			chordMillis = 0;
		}
	}

	private void onReleaseChord(){
		main.grid.updateGrid(1, 1);
		z = 0;
		x = 0;
		xRelease = false;
		zRelease = false;
		chordMillis = 0;
	}
}
