package Screens;

import FileStreaming.Scores;
import Lookup.Sprites;
import Minesweeper.Cell;
import Minesweeper.Main;

public class ScoreScreen extends Screen {

	ScoreScreen(Main main){
		this.main = main;
		initialize();
	}

	@Override
	protected void initialize(){
		Main.mainClass.image(Sprites.background, 0, 0);
		Main.mainClass.pushStyle();
		Main.mainClass.tint(255, 150);
		Main.mainClass.image(Sprites.vignette, 0, 0);
		Main.mainClass.popStyle();
	}

	@Override
	public void update(){
		drawSprites();
		updateHighscores();
	}

	@Override
	public void keyPressed(int keyCode){

	}

	@Override
	public void keyReleased(int keyCode){
		main.ChangeScreen(new MenuScreen(main));
	}

	private void updateHighscores() {
		for(int i = 0; i < 3; i++){ // 3 difficulty categories
			for(int j = 0; j < 10; j++){ // 10 entries per category
				if(Scores.highscoreTimes[i][j] == 1000 || Scores.highscoreNames[i][j].equals("*")) continue;

				for(int a = 0; a < Scores.highscoreNames[i][j].length(); a++) {
					String ch = Scores.highscoreNames[i][j].substring(a, a + 1);
					String ref = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
					int imageIndex = ref.indexOf(ch.toUpperCase());

					if(imageIndex != -1)
						Main.mainClass.image(Sprites.fontCharacters[imageIndex], 144 * (i + 1) + (Cell.CELL_WIDTH * 6)*i + Cell.CELL_WIDTH*a, Cell.CELL_HEIGHT *  3 + Cell.CELL_HEIGHT * j);
				}

				String finishTime = Integer.toString(Scores.highscoreTimes[i][j]);
				while(finishTime.length() < 3) finishTime = "0" + finishTime;

				for(int a = 0; a < finishTime.length(); a++) {
					Main.mainClass.image(Sprites.fontNumbers[Integer.parseInt(finishTime.substring(a, a+1))], 48 * 5 + 144 * (i + 1) + (Cell.CELL_WIDTH * 6) * i + Cell.CELL_WIDTH*a, Cell.CELL_HEIGHT * 3 + Cell.CELL_HEIGHT * j);
				}
			}
		}
	}

	private void drawSprites(){
		Main.mainClass.image(Sprites.anyKey, 528, Cell.CELL_HEIGHT*15);

		// 1 column for each difficulty
		Main.mainClass.image(Sprites.difficultyLabels[0], 144, Cell.CELL_HEIGHT*2);
		Main.mainClass.image(Sprites.difficultyLabels[1], 144*2 + Cell.CELL_WIDTH*6, Cell.CELL_HEIGHT*2);
		Main.mainClass.image(Sprites.difficultyLabels[2], 144*3 + Cell.CELL_WIDTH*6*2, Cell.CELL_HEIGHT*2);
	}
}
