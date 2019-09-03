package Lookup;

import Minesweeper.Coordinate;
import Minesweeper.Main;

public class Utility {

    public static Coordinate mousePos(){
        return new Coordinate(Main.mainClass.mouseX, Main.mainClass.mouseY);
    }

    /**
     * Returns whether or not the coordinate c is within the rectangular bounds defined by the x, y, w, and h
     */
    public static boolean inRect(Coordinate c, int x, int y, int w, int h){
        return c.x > x && c.x < x+w && c.y > y && c.y < y+h;
    }

    public static int clamp(int n, int min, int max){
        if(n < min) return min;
        else if(n > max) return max;
        return n;
    }
}
