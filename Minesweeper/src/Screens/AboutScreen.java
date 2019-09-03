package Screens;

import Lookup.Sprites;
import Minesweeper.Cell;
import Minesweeper.Main;

public class AboutScreen extends Screen {

	AboutScreen(Main main){
		this.main = main;
	}

	@Override
	protected void initialize() {

	}

	@Override
	public void update(){
		drawBackground();

		Main.mainClass.image(Sprites.aboutText, 483, Cell.CELL_HEIGHT*3);
		Main.mainClass.image(Sprites.anyKey, 528, Cell.CELL_HEIGHT*15);
	}

	@Override
	public void keyPressed(int keyCode){

	}

	@Override
	public void keyReleased(int keyCode){
		main.ChangeScreen(new MenuScreen(main));
	}

	private void drawBackground(){
		Main.mainClass.image(Sprites.background, 0, 0, Main.screenW, Main.screenH);
		Main.mainClass.pushStyle();
		Main.mainClass.tint(255, 150);
		Main.mainClass.image(Sprites.vignette, 0, 0, Main.screenW, Main.screenH);
		Main.mainClass.popStyle();
	}
}
