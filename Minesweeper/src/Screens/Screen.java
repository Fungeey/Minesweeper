package Screens;

import Minesweeper.Main;

public abstract class Screen {
	protected Main main;

	protected abstract void initialize();
	public abstract void update();
	public abstract void keyPressed(int keyCode);
	public abstract void keyReleased(int keyCode);
}
