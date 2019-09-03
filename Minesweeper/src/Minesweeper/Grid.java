package Minesweeper;

import Lookup.Sprites;
import Lookup.Utility;
import processing.core.PApplet;

import java.util.Random;

public class Grid {
    private PApplet parent;

    private int gridWidth;
    private int gridHeight;
    public int numBombs; // Number of total bombs
    public int flaggedBombs; // Number of flagged bombs (not necessarily correct)
    private int revealedTiles; // Number of uncovered tiles (uncover all not-bomb tiles to win)
    public int difficulty;

    private Cell[][] grid;
    private Boolean[][] coverGrid;
    private Boolean[][] flagGrid;

    private boolean gridGenerated;
    public boolean gameFinished;
    public int gameResult; // 1 for Win, 0 for Lose

    private int gridOffsetX; // Offset to apply to horizontally center the board
    private int gridOffsetY; // Offset to apply to vertically center the board

    public Grid(PApplet parent, int width, int height, int numBombs){
        this.parent = parent;
        this.gridWidth = width;
        this.gridHeight = height;
        this.numBombs = numBombs;
        gameFinished = false;

        initializeGrids();
    }

    /**
     * @param parent Link to the PApplet parent
     * @param gridSetup Object gridSetup, which
     *                  defines the width, height,
     *                  and number of bombs as the
     *                  preset difficulties
     */
    public Grid(PApplet parent, GridSetup gridSetup){
        this.parent = parent;
        this.gridWidth = gridSetup.width;
        this.gridHeight = gridSetup.height;
        this.numBombs = gridSetup.numBombs;
        gameFinished = false;
        switch(gridSetup.numBombs){
            case 9:  difficulty = 0;
            case 40: difficulty = 1;
            case 99: difficulty = 2;
        }

        initializeGrids();
    }

    /**
     * Initialize the grids holding the location of the bombs,
     * unopened tiles, and flags.
     */
    private void initializeGrids(){
        gridOffsetX = (parent.width-gridWidth*Cell.CELL_WIDTH)/2;
        gridOffsetY = (parent.height-(gridWidth*Cell.CELL_HEIGHT)-96)/2;
        if(gridOffsetY < 0) gridOffsetY = 0;

        gridGenerated = false;

        grid = new Cell[gridWidth][gridHeight];
        coverGrid = new Boolean[gridWidth][gridHeight];
        flagGrid = new Boolean[gridWidth][gridHeight];

        for(int i = 0; i < gridWidth; i++){
            for(int j = 0; j < gridHeight; j++){
                // Initialize grid array
                setGrid(i, j, new Cell(i, j));

                // Initialize coverGrid array
                setCover(i, j, true);

                // Initialize flagGrid array
                setFlag(i, j, false);
            }
        }
    }

    /**
     * Randomly generate a field of bombs
     * Ensure that the tile under the cursor is never a bomb,
     * and that bombs do not spawn too close to the mouse,
     * to ensure a good start
     * Also, give each empty tile the number of bombs adjacent to it
     */
    private void generateGrid(){
        gridGenerated = true;
        Coordinate mousePos = new Coordinate((Utility.clamp(parent.mouseX - gridOffsetX, 0, gridWidth*Cell.CELL_WIDTH))/Cell.CELL_WIDTH, (Utility.clamp(parent.mouseY - gridOffsetY-Cell.CELL_WIDTH * 2, 0, gridHeight*Cell.CELL_HEIGHT))/Cell.CELL_HEIGHT);

        // Initialize bombs in random locations
        for(int i = 0; i < numBombs; i++){
            if(i > gridWidth*gridHeight) break;

            Random bombRand = new Random();
            int row = bombRand.nextInt(gridWidth);
            int col = bombRand.nextInt(gridHeight);
            while((mousePos.x == row && mousePos.y == col) || getGrid(row, col).distance(mousePos) < 3 || getGrid(row, col).getCellType() == Cell.BOMB) {
                row = bombRand.nextInt(gridWidth);
                col = bombRand.nextInt(gridHeight);
            }
            getGrid(row, col).setCellType(Cell.BOMB);
        }

        // Determine cell value for empty tiles
        for(int i = 0; i < gridWidth; i++){
            for(int j = 0; j < gridHeight; j++){
                Cell cell = getGrid(i, j);
                if(cell.getCellType() == Cell.BOMB) continue;

                // Check for adjacent bombs
                int val = 0;
                for(int x = -1; x <= 1; x++){
                    for(int y = -1; y <= 1; y++){
                        if(onGrid(i+x, j+y)){
                            if(getGrid(i+x, j+y).getCellType() == Cell.BOMB){
                                val++;
                            }
                        }
                    }
                }
                cell.setCellValue(val);
            }
        }
    }

    /**
     * Draw the game board
     */
    private void drawGrid() {
        //System.out.println("Drawing grid    " + parent.millis());

        parent.pushMatrix();
        parent.translate(gridOffsetX, Cell.CELL_WIDTH * 2+gridOffsetY);
        Random rand = new Random(1);
        int buffer; // Use up the next random int to make sure we get the same numbers every time

        // Draw ground tiles
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                parent.image(Sprites.groundTiles[rand.nextInt(3)], i * Cell.CELL_WIDTH, j * Cell.CELL_HEIGHT, Cell.CELL_WIDTH, Cell.CELL_HEIGHT);
            }
        }

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                Cell cell = getGrid(i, j);
                if (cell.getCellType() == Cell.BOMB) {
                    parent.fill(0, 0, 0);
                    parent.image(Sprites.mine, i * Cell.CELL_WIDTH, j * Cell.CELL_HEIGHT, Cell.CELL_WIDTH, Cell.CELL_HEIGHT);
                    parent.fill(255);
                } else if (cell.getCellValue() != 0) {
                    parent.image(Sprites.numbers[rand.nextInt(2)][cell.getCellValue()-1], i * Cell.CELL_WIDTH, j * Cell.CELL_HEIGHT, Cell.CELL_WIDTH, Cell.CELL_HEIGHT);
                }else{
                    buffer = rand.nextInt(1);
                }

                // Draw coverGrid
                if (getCover(i, j)) {
                    parent.image(Sprites.tiles[rand.nextInt(4)], i * Cell.CELL_WIDTH, j * Cell.CELL_HEIGHT, Cell.CELL_WIDTH, Cell.CELL_HEIGHT);
                }else{
                    buffer = rand.nextInt(1);
                }

                // Draw flagGrid
                if (getFlag(i, j)) {
                    parent.image(Sprites.flag, i * Cell.CELL_WIDTH, j * Cell.CELL_HEIGHT, Cell.CELL_WIDTH, Cell.CELL_HEIGHT);
                }

                parent.fill(0, rand.nextInt(25));
                parent.rect(i * Cell.CELL_WIDTH, j * Cell.CELL_HEIGHT, Cell.CELL_WIDTH, Cell.CELL_HEIGHT);
                parent.fill(255, 255);
            }
        }
        parent.popMatrix();
    }

    /**
     * Parse inputs from the Main.Main Class
     * 0 = button is not down
     * 1 = button was released / clicked
     * 2 = button is being held down
     * @param left Open tile (z or left click)
     * @param right Flag tile (x or right click)
     */
    public void updateGrid(int left, int right){
        if((left == 0 && right == 0) || gameFinished){
            drawGrid();
            return;
        }

        Coordinate mousePos = new Coordinate((Utility.clamp(parent.mouseX - gridOffsetX, 0, gridWidth*Cell.CELL_WIDTH))/Cell.CELL_WIDTH, (Utility.clamp(parent.mouseY - gridOffsetY-Cell.CELL_WIDTH * 2, 0, gridHeight*Cell.CELL_HEIGHT))/Cell.CELL_HEIGHT);
        if((parent.mouseX - gridOffsetX) < 0 || (parent.mouseY - gridOffsetY-Cell.CELL_WIDTH * 2) < 0 || !onGrid(mousePos)){
            drawGrid();
            return;
        }

        if(left == 1 && right == 1 && gridGenerated && flaggedAroundCell(mousePos)){
            // If all bombs nearby are flagged, reveal every non-bomb around this tile
            for(int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (onGrid(mousePos.x + x, mousePos.y + y)) {
                        revealCell(new Coordinate(mousePos.x + x, mousePos.y + y));
                    }
                }
            }
            drawGrid();
            return;
        }

        if(left == 1){
            if (!gridGenerated){
                generateGrid();
            }
            drawGrid();
            revealCell(new Coordinate(mousePos.x, mousePos.y));
            if(!gameFinished) drawGrid();
            return;
        }

        if(right == 1){
            if(gridGenerated)
                flagTile(mousePos);
        }

        drawGrid();

        if(left == 2){
            if(
            getCover(mousePos) &&
            !getFlag(mousePos)
            ) {
                int x, y;
                if(gridWidth % 2 == 1)
                    x = ((parent.mouseX+Cell.CELL_WIDTH/2) / Cell.CELL_WIDTH) * Cell.CELL_WIDTH-Cell.CELL_WIDTH/2;
                else
                    x = ((parent.mouseX) / Cell.CELL_WIDTH) * Cell.CELL_WIDTH;

                if(gridHeight % 2 == 1)
                    y = ((parent.mouseY+Cell.CELL_HEIGHT/2) / Cell.CELL_HEIGHT) * Cell.CELL_HEIGHT-Cell.CELL_HEIGHT/2;
                else
                    y = ((parent.mouseY) / Cell.CELL_HEIGHT) * Cell.CELL_HEIGHT;
                parent.image(Sprites.pressedTile, x, y, Cell.CELL_WIDTH, Cell.CELL_HEIGHT);
            }
        }
    }

    /**
     * Recursive call to open all surrounding empty cells:
     * If you open a tile containing a mine, end the game
     * If you find a tile with a number, open it and stop
     * Otherwise, keep opening the empty cells
     * @param pos Specifies which tile to open next
     */
    private void revealCell(Coordinate pos){
        if(getFlag(pos)) return;
        if(!getCover(pos)) return;
        setCover(pos, false);

        if(getGrid(pos).getCellType() == Cell.BOMB)
            loseGame();
        else{
            revealedTiles++;
            if(revealedTiles == (gridWidth*gridHeight-numBombs))
                winGame();
        }

        if(getGrid(pos).getCellValue() != 0) {
            return;
        }

        // Reveal all surrounding tiles
        for(int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (onGrid(pos.x + x, pos.y + y)) {
                    revealCell(new Coordinate(pos.x+x, pos.y+y));
                }
            }
        }
    }

    /**
     * Checks if the number of adjacent flags is the same as the number on the tile
     * Used for Chording (if flaggedAroundCell, open all tiles around)
     * @param pos the tile at which to check
     * @return boolean
     */
    private boolean flaggedAroundCell(Coordinate pos){
        Cell cell = getGrid(pos);
        if(getCover(pos)) return false;

        int flags = cell.getCellValue();
        int foundFlags = 0;

        for(int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if(onGrid(pos.x+x, pos.y+y)){
                    if(getFlag(pos.x+x, pos.y+y)){
                        foundFlags++;
                    }
                }
            }
        }
        return foundFlags == flags;
    }

    /**
     * Toggles the flag on the tile under the mouse Position
     * Only remove / add a flag if the tile has not been opened yet
     * If the number of correctly flagged tiles matches the number of total bombs, you won!
     *
     */
    private void flagTile(Coordinate mousePos){
        if(!getCover(mousePos))return;

        setFlag(mousePos, !getFlag(mousePos));
    }

    /**
     * Open the rest of the tiles
     * Set the game to be finished
     */
    private void loseGame() {
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                coverGrid[i][j] = false;
            }
        }
        drawGrid();

        gameFinished = true;
        gameResult = 0;
    }

    private void winGame(){
        System.out.println("Congrats!!! You swept the minefield clean of mines!");
        gameFinished = true;
        gameResult = 1;
    }

    /**
     * Check if the given Main.Main.Coordinate is within the bounds of the game board
     * @param pos the Main.Main.Coordinate to check
     * @return boolean
     */
    private boolean onGrid(Coordinate pos){
        //System.out.println(pos.x);
        //System.out.println( pos.x >= 0 && pos.x < gridWidth && pos.y >= 0 && pos.y < gridHeight);
        return pos.x >= 0 && pos.x < gridWidth && pos.y >= 0 && pos.y < gridHeight;
    }

    private boolean onGrid(int x, int y){
        return x >= 0 && x < gridWidth && y >= 0 && y < gridHeight;
    }

    // Array Accessors
    private Cell getGrid(Coordinate pos){
        return grid[pos.x][pos.y];
    }

    private boolean getCover(Coordinate pos){
        return coverGrid[pos.x][pos.y];
    }

    private boolean getFlag(Coordinate pos){
        return flagGrid[pos.x][pos.y];
    }

    private Cell getGrid(int row, int col){
        return grid[row][col];
    }

    private boolean getCover(int row, int col){
        return coverGrid[row][col];
    }

    private boolean getFlag(int row, int col){
        return flagGrid[row][col];
    }

    // Array Setters
    private void setGrid(Coordinate pos, Cell cell){ // Already have set access in Main.Main.Cell
        grid[pos.x][pos.y] = cell;
    }

    private void setCover(Coordinate pos, boolean cover){
        coverGrid[pos.x][pos.y] = cover;
    }

    private void setFlag(Coordinate pos, boolean flag){
        flagGrid[pos.x][pos.y] = flag;
        if(flag) flaggedBombs++;
        else flaggedBombs--;
    }

    private void setGrid(int row, int col, Cell cell){
        grid[row][col] = cell;
    }

    private void setCover(int row, int col, boolean cover){
        coverGrid[row][col] = cover;
    }

    private void setFlag(int row, int col, boolean flag){
        flagGrid[row][col] = flag;
    }


    public int getGridWidth(){
        return gridWidth;
    }

    public int getGridHeight(){
        return gridHeight;
    }
}
