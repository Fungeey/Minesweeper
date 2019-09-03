package Screens;

import Minesweeper.Cell;
import Lookup.*;
import Minesweeper.Grid;
import Minesweeper.Main;
import Minesweeper.Settings;

public class StartScreen extends Screen {

	StartScreen(Main main){
		this.main = main;
		initialize();
	}

	@Override
	protected void initialize() {
		Main.mainClass.image(Sprites.background, 0, 0);
		Main.mainClass.pushStyle();
		Main.mainClass.tint(255, 150);
		Main.mainClass.image(Sprites.vignette, 0, 0);
		Main.mainClass.popStyle();

		// Draw Difficulty Buttons
		Main.mainClass.image(Sprites.difficultyLabels[0], Cell.CELL_WIDTH*12, Cell.CELL_HEIGHT*(9), Cell.CELL_WIDTH*6, Cell.CELL_HEIGHT);
		Main.mainClass.image(Sprites.difficultyLabels[1], Cell.CELL_WIDTH*12, Cell.CELL_HEIGHT*(10), Cell.CELL_WIDTH*6, Cell.CELL_HEIGHT);
		Main.mainClass.image(Sprites.difficultyLabels[2], Cell.CELL_WIDTH*12, Cell.CELL_HEIGHT*(11), Cell.CELL_WIDTH*6, Cell.CELL_HEIGHT);
	}

	@Override
	public void update(){
		// Draw Background
		Main.mainClass.image(Sprites.background, 0, 0);
		Main.mainClass.pushStyle();
		Main.mainClass.tint(255, 150);
		Main.mainClass.image(Sprites.vignette, 0, 0);
		Main.mainClass.popStyle();

		// Draw instructions]
		Main.mainClass.fill(150);
		Main.mainClass.image(Sprites.instructions, 0, 0);
		Main.mainClass.fill(255);

		// Draw Difficulty Buttons
		Main.mainClass.image(Sprites.difficultyLabels[0], Cell.CELL_WIDTH*12, Cell.CELL_HEIGHT*(9), Cell.CELL_WIDTH*6, Cell.CELL_HEIGHT);
		Main.mainClass.image(Sprites.difficultyLabels[1], Cell.CELL_WIDTH*12, Cell.CELL_HEIGHT*(10), Cell.CELL_WIDTH*6, Cell.CELL_HEIGHT);
		Main.mainClass.image(Sprites.difficultyLabels[2], Cell.CELL_WIDTH*12, Cell.CELL_HEIGHT*(11), Cell.CELL_WIDTH*6, Cell.CELL_HEIGHT);

		// Hover Arrow
		if(Utility.inRect(Utility.mousePos(), Cell.CELL_WIDTH * 11, Cell.CELL_HEIGHT * 9, Cell.CELL_WIDTH * 6, Cell.CELL_HEIGHT*35)){
			int y = (Main.mainClass.mouseY / Cell.CELL_HEIGHT);
			Main.mainClass.image(Sprites.selectArrowL, Cell.CELL_WIDTH * 11, Cell.CELL_HEIGHT * y, Cell.CELL_WIDTH, Cell.CELL_HEIGHT);
			Main.mainClass.image(Sprites.selectArrowR, Cell.CELL_WIDTH * 18, Cell.CELL_HEIGHT * y, Cell.CELL_WIDTH, Cell.CELL_HEIGHT);
		}
	}

	@Override
	public void keyPressed(int keyCode){

	}

	@Override
	public void keyReleased(int keyCode){
		if(keyCode == 'Z') {
			boolean createGame = false;

			if (Utility.inRect(Utility.mousePos(), Cell.CELL_WIDTH * 11, Cell.CELL_HEIGHT * 9, Cell.CELL_WIDTH * 6, Cell.CELL_HEIGHT)){
				main.grid = new Grid(Main.mainClass, Settings.EASY);
				createGame = true;
			}
			if (Utility.inRect(Utility.mousePos(), Cell.CELL_WIDTH * 11, Cell.CELL_HEIGHT * 10, Cell.CELL_WIDTH * 6, Cell.CELL_HEIGHT)){
				main.grid = new Grid(Main.mainClass, Settings.MEDIUM);
				createGame = true;
			}
			if (Utility.inRect(Utility.mousePos(), Cell.CELL_WIDTH * 11, Cell.CELL_HEIGHT * 11, Cell.CELL_WIDTH * 6, Cell.CELL_HEIGHT)){
				main.grid = new Grid(Main.mainClass, Settings.HARD);
				createGame = true;
			}

			if(createGame){
				main.ChangeScreen(new GameScreen(main));
			}
		}
	}
}
