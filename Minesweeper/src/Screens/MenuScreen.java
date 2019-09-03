package Screens;

import Lookup.Sprites;
import Lookup.Utility;
import Minesweeper.Cell;
import Minesweeper.Main;

public class MenuScreen extends Screen {

	public MenuScreen(Main main){
		this.main = main;
	}

	@Override
	protected void initialize(){

	}

	@Override
	public void update(){
		drawBackground();

		Main.mainClass.image(Sprites.titleSprite, 0, 0);

		// Draw Menu Buttons
		for(int i = 0; i < 4; i++){
			Main.mainClass.image(Sprites.menuButtons[i], Cell.CELL_WIDTH*12, Cell.CELL_HEIGHT*(9+i), Cell.CELL_WIDTH*6, Cell.CELL_HEIGHT);
		}

		// Hover Arrow
		if(Utility.inRect(Utility.mousePos(), Cell.CELL_WIDTH * 11, Cell.CELL_HEIGHT * 9, Cell.CELL_WIDTH * 6, Cell.CELL_HEIGHT*4)){
			Main.mainClass.image(Sprites.fontCharacters[25], Main.mainClass.mouseX+32, Main.mainClass.mouseY);

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
			if (Utility.inRect(Utility.mousePos(), Cell.CELL_WIDTH * 11, Cell.CELL_HEIGHT * 9, Cell.CELL_WIDTH * 6, Cell.CELL_HEIGHT)) { // Play Button
				main.ChangeScreen(new StartScreen(main));

				drawBackground();

				// Draw Difficulty Buttons
				Main.mainClass.image(Sprites.difficultyLabels[0], Cell.CELL_WIDTH*12, Cell.CELL_HEIGHT*(9), Cell.CELL_WIDTH*6, Cell.CELL_HEIGHT);
				Main.mainClass.image(Sprites.difficultyLabels[1], Cell.CELL_WIDTH*12, Cell.CELL_HEIGHT*(10), Cell.CELL_WIDTH*6, Cell.CELL_HEIGHT);
				Main.mainClass.image(Sprites.difficultyLabels[2], Cell.CELL_WIDTH*12, Cell.CELL_HEIGHT*(11), Cell.CELL_WIDTH*6, Cell.CELL_HEIGHT);

			}
			if (Utility.inRect(Utility.mousePos(), Cell.CELL_WIDTH * 11, Cell.CELL_HEIGHT * 10, Cell.CELL_WIDTH * 6, Cell.CELL_HEIGHT)) { // Scores Button
				main.ChangeScreen(new ScoreScreen(main));
				drawBackground();
			}
			if (Utility.inRect(Utility.mousePos(), Cell.CELL_WIDTH * 11, Cell.CELL_HEIGHT * 11, Cell.CELL_WIDTH * 6, Cell.CELL_HEIGHT)) { // About Button
				main.ChangeScreen(new AboutScreen(main));
			}

			if (Utility.inRect(Utility.mousePos(), Cell.CELL_WIDTH * 11, Cell.CELL_HEIGHT * 12, Cell.CELL_WIDTH * 6, Cell.CELL_HEIGHT)) { // Exit Button
				main.Shutdown();
			}
		}
	}

	private void drawBackground(){
		Main.mainClass.image(Sprites.background, 0, 0);
		Main.mainClass.pushStyle();
		Main.mainClass.tint(255, 150);
		Main.mainClass.image(Sprites.vignette, 0, 0);
		Main.mainClass.popStyle();
	}
}
