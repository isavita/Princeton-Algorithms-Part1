import edu.princeton.cs.algs4.Queue;

public class Board {
    private final int [][] grid;
    private final int gridSize;
    
    // construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.gridSize = blocks.length;
        this.grid = copyBlocks(blocks);
    }
    
    // board dimension n
    public int dimension() {
        return gridSize;
    }
    
    // number of blocks out of place
    public int hamming() {
        int outOfPlaceBlocksCount = 0;
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                if (!isSpace(i, j) && grid[i][j] != (i * gridSize + j + 1)) outOfPlaceBlocksCount++;
        return outOfPlaceBlocksCount;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int sumOfManhattanDistances = 0;
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                if (!isSpace(i, j) && grid[i][j] != goalBlockValue(i, j)) sumOfManhattanDistances += manhattanDistanceBetweenBlocksGoal(i, j);
        return sumOfManhattanDistances;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize - 1; j++)
                if (!isSpace(i, j) && !isSpace(i, j + 1))
                    return new Board(swap(i, j, i, j + 1));
        return null;
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || !(y instanceof Board)) return false;
        Board yGrid = (Board) y; 
        if (this.dimension() != yGrid.dimension()) return false;
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                if (grid[i][j] != yGrid.grid[i][j]) return false;
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<Board>();
        int spaceRow = 0;
        int spaceCol = 0;
        
        // finding the empty space location
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                if (isSpace(i, j)) {
                    spaceRow = i;
                    spaceCol = j;
                    i = gridSize; // to break the external loop too
                    break;
                }
        
        // add all neighbors    
        if (spaceRow > 0) neighbors.enqueue(new Board(swap(spaceRow, spaceCol, spaceRow - 1, spaceCol)));
        if (spaceRow < gridSize - 1) neighbors.enqueue(new Board(swap(spaceRow, spaceCol, spaceRow + 1, spaceCol)));
        if (spaceCol > 0) neighbors.enqueue(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol - 1)));
        if (spaceCol < gridSize - 1) neighbors.enqueue(new Board(swap(spaceRow, spaceCol, spaceRow, spaceCol + 1)));;
        
        return neighbors;
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(gridSize + "\n");
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                s.append(String.format("%2d ", grid[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
        
    }
    
    private int[][] swap(int row, int col, int newRow, int newCol) {
        int[][] newBlocks = copyBlocks(grid);
        int temp = newBlocks[row][col];
        newBlocks[row][col] = newBlocks[newRow][newCol];
        newBlocks[newRow][newCol] = temp;
        return newBlocks;
    }
    
    private int manhattanDistanceBetweenBlocksGoal(int row, int col) {
        int value = grid[row][col];
        int goalRow = value / gridSize;
        int goalCol =  value - 1 - (goalRow * gridSize);
        boolean lastColBlock = (goalCol == -1);
        
        if (lastColBlock) {
            goalRow--;
            goalCol = gridSize - 1;
        }
      
        return Math.abs(goalRow - row) + Math.abs(goalCol - col);
    }
    
    private int goalBlockValue(int row, int col) {
        return row * gridSize + col + 1;
    }
    
    private boolean isSpace(int row, int col) {
        return grid[row][col] == 0;
    }
    
    private int[][] copyBlocks(int [][] blocks) {
        int[][] newBlocks = new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                newBlocks[i][j] = blocks[i][j];
        return newBlocks;
    }
    
    // unit tests (not graded)
    public static void main(String[] args) {
    }
}