package FileStreaming;

import java.io.*;

public class Scores {
	public static String[][] highscoreNames = new String[3][10]; // Multidimensional arrays to hold the top 10 names for the three difficulties
	public static int[][] highscoreTimes = new int[3][10]; // Multidimensional arrays to hold the top 10 times for the three difficulties

	public static void loadData(){
		try {
			FileReader fr = new FileReader("data/_MinesweeperSave.txt");
			BufferedReader br = new BufferedReader(fr);
			System.out.println("Loading Data ..... Please Stand By");

			String str = br.readLine();

			if(str != null){ // File has stuff
				for(int i = 0; i < 3; i++){ // For each of the three difficulties
					str = br.readLine();
					for(int j = 0; j < 10; j++) { // For each of the 10 top scores
						int time = Integer.parseInt(br.readLine());
						String name = br.readLine();

						highscoreTimes[i][j] = time;
						highscoreNames[i][j] = name;
					}
				}
			}else{
				for(int i = 0; i < 3; i++){ // For each of the three difficulties
					for(int j = 0; j < 10; j++) { // For each of the 10 top scores
						highscoreTimes[i][j] = 1000;
						highscoreNames[i][j] = "*";
					}
				}
				saveData();
			}

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveData(){
		try {
			FileWriter fw = new FileWriter("_MinesweeperSave.txt");
			PrintWriter pw = new PrintWriter(fw);

			pw.println("Save Data");
			for(int i = 0; i < 3; i++){ // For each of the three difficulties
				pw.println("difficulty " + i);
				for(int j = 0; j < 10; j++) { // For each of the 10 top scores
					pw.println(highscoreTimes[i][j]);
					pw.println(highscoreNames[i][j]);
				}
			}

			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
