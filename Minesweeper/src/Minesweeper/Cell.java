package Minesweeper;

public class Cell {
    public static final int CELL_WIDTH = 48;
    public static final int CELL_HEIGHT = 48;

    static final int EMPTY = 0;
    static final int ADJACENT = 1;
    static final int BOMB = 2;

    boolean isFlagged;
    boolean isOpen;

    private Coordinate pos;
    private int cellType;
    private int cellValue;

    Cell(int x, int y) {
        this.pos = new Coordinate(x, y);
    }

    int getCellType() {
        return cellType;
    }

    int getCellValue() {
        return cellValue;
    }

    Coordinate getPos() {
        return pos;
    }

    void setCellType(int cellType) {
        this.cellType = cellType;
    }

    void setCellValue(int cellValue) {
        this.cellValue = cellValue;
        switch (this.cellValue) {
            case 0: setCellType(Cell.EMPTY);
            default: setCellType(Cell.ADJACENT);
        }
    }

    public void setPos(Coordinate pos) {
        this.pos = pos;
    }

    public int distance(Cell other){
        Coordinate pos1 = other.getPos();
        return Math.abs(pos.x-pos1.x) + Math.abs(pos.y - pos1.y);
    }

    int distance(Coordinate other){
        return Math.abs(pos.x - other.x) + Math.abs(pos.y - other.y);
    }
}
