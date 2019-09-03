package Screens;

import FileStreaming.Scores;
import Lookup.Sprites;
import Minesweeper.Cell;
import Minesweeper.Main;

public class FinishScreen extends Screen {

	FinishScreen(Main main){
		this.main = main;
	}

	@Override
	protected void initialize() {

	}

	@Override
	public void update(){
		drawBackground();

		if(main.grid.gameResult == 1)
			drawWinCondition();
		else
			drawLoseCondition();
	}

	@Override
	public void keyPressed(int keyCode){
		String ref = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String ch = Character.toString(Character.toUpperCase(Main.mainClass.key));

		if(ref.contains(ch)) {
			if(main.nameInput.length() < 5)
				main.nameInput += ref.charAt(ref.indexOf(ch));
		}else{
			if(Main.mainClass.key == Main.mainClass.BACKSPACE){
				if(main.nameInput.length() > 0)
					main.nameInput = main.nameInput.substring(0, main.nameInput.length()-1);
			}
		}
	}

	@Override
	public void keyReleased(int keyCode){
		if(main.grid.gameResult == 0) {
			main.ChangeScreen(new MenuScreen(main));
			main.startGame = false;
		}else if(Main.mainClass.key == Main.mainClass.ENTER && main.nameInput.length() > 0) {
			main.ChangeScreen(new ScoreScreen(main));
			main.startGame = false;
			if (main.grid.gameResult == 1)
				updateHighscores();
		}
	}

	private void drawBackground(){
		Main.mainClass.image(Sprites.background, 0, 0);
		Main.mainClass.pushStyle();
		Main.mainClass.tint(255, 150);
		Main.mainClass.image(Sprites.vignette, 0, 0);
		Main.mainClass.popStyle();
	}

	private void drawLoseCondition(){
		Main.mainClass.image(Sprites.loseBanner, Cell.CELL_WIDTH*12, Cell.CELL_HEIGHT*2);
		Main.mainClass.image(Sprites.anyKey, 528, Cell.CELL_HEIGHT*12);
	}

	private void drawWinCondition() {
		Main.mainClass.image(Sprites.winBanner, Cell.CELL_WIDTH * 12, Cell.CELL_HEIGHT * 2);
		Main.mainClass.image(Sprites.enterName, 528, Cell.CELL_HEIGHT * 5);
		Main.mainClass.image(Sprites.pressEnter, 528, Cell.CELL_HEIGHT * 12);

		// Draw name
		for (int i = 0; i < main.nameInput.length(); i++) {
			String ch = main.nameInput.substring(i, i + 1);
			String ref = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			int imageIndex = ref.indexOf(ch);
			Main.mainClass.image(Sprites.fontCharacters[imageIndex], 504 + i * Cell.CELL_WIDTH, Cell.CELL_HEIGHT * 6);
		}

		drawFinishTime();
	}

	private void drawFinishTime(){
		String finishTime = Integer.toString(main.time);
		while(finishTime.length() < 3) finishTime = "0" + finishTime;

		Main.mainClass.image(Sprites.fontNumbers[Integer.parseInt(finishTime.substring(2, 3))], 792 + Cell.CELL_WIDTH*2, Cell.CELL_HEIGHT*6);
		Main.mainClass.image(Sprites.fontNumbers[Integer.parseInt(finishTime.substring(1, 2))], 792 + Cell.CELL_WIDTH, Cell.CELL_HEIGHT*6);
		Main.mainClass.image(Sprites.fontNumbers[Integer.parseInt(finishTime.substring(0, 1))], 792, Cell.CELL_HEIGHT*6);
	}

	private void updateHighscores(){
		for (int i = 0; i < 10; i++) {
			if (main.time < Scores.highscoreTimes[main.grid.difficulty][i] || Scores.highscoreNames[main.grid.difficulty][i].equals("*")) {
				System.out.println(Scores.highscoreTimes[main.grid.difficulty][i]);

				for (int j = 9; j > i; j--) { // Shift everything over to make a space
					Scores.highscoreTimes[main.grid.difficulty][j] = Scores.highscoreTimes[main.grid.difficulty][j - 1];
					Scores.highscoreNames[main.grid.difficulty][j] = Scores.highscoreNames[main.grid.difficulty][j - 1];
				}
				Scores.highscoreTimes[main.grid.difficulty][i] = main.time;
				Scores.highscoreNames[main.grid.difficulty][i] = main.nameInput;
				break;
			}
		}
		Scores.saveData();
	}
}
