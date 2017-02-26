// Aleksandar Dimov 02/01/2017 
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF sites;
	private int openSitesCount;
	private int topRoot;
	private int bottomRoot;
	private int[][] grid; 
	private int gridSize;
	
	public Percolation(int n)  {
		if (n <= 0) throw new IllegalArgumentException(Integer.toString(n));
		grid = new int[n][n];
		gridSize = n;
		topRoot = 0;
		bottomRoot = n * n + 1;
		sites = new WeightedQuickUnionUF(n * n + 2); // the additional two element are for top and bottom roots
		openSitesCount = 0;
		for (int i = 0; i < n; i++) { // connected top and bottom raws to corresponding roots
			for (int j = 0; j < n; j++)
				grid[i][j] = 0;
		}
	}
	
	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		if (isOutsideRange(row, col)) throw new java.lang.IndexOutOfBoundsException();
		if (!isOpen(row, col)) { 
			if (row == 1) sites.union(topRoot, xyTo1D(row, col));
			if (row == gridSize) sites.union(xyTo1D(row, col), bottomRoot);
			grid[row-1][col-1] = 1;
			connectAllNeighbors(row, col);
			openSitesCount += 1;
		}
	}
	
	// is site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (isOutsideRange(row, col)) throw new java.lang.IndexOutOfBoundsException();
		return grid[row-1][col-1] != 0;
	}
	
	// is site (row, col) full?
	public boolean isFull(int row, int col) {
		if (isOutsideRange(row, col)) throw new java.lang.IndexOutOfBoundsException();
		return sites.connected(xyTo1D(row, col), topRoot);
	}
	
	// number of open sites
	public int numberOfOpenSites() {
		return openSitesCount;
	}
	
	// does the system percolate?
	public boolean percolates() {
		return sites.connected(bottomRoot, topRoot);
	}
	
	// grid boundaries check
	private boolean isOutsideRange(int row, int col) {
		return gridSize < row || gridSize < col || row <= 0 || col <= 0;
	}
	
	// connect with all open neighbors  	
	private void connectAllNeighbors(int row, int col) {
		if (!isOutsideRange(row + 1, col) && isOpen(row + 1, col)) {
			sites.union(xyTo1D(row + 1, col), xyTo1D(row, col)); // connects with above
		}
		if (!isOutsideRange(row, col + 1) && isOpen(row, col + 1)) {
			sites.union(xyTo1D(row, col + 1), xyTo1D(row, col)); // connects with right
		}
		if (!isOutsideRange(row, col - 1) && isOpen(row, col - 1)) {
			sites.union(xyTo1D(row, col - 1), xyTo1D(row, col)); // connects with left
		}
		if (!isOutsideRange(row - 1, col) && isOpen(row - 1, col)) {
			sites.union(xyTo1D(row - 1, col), xyTo1D(row, col)); // connects with under
		}
	}
	
	// It's needed because the formula for going form 2D Array to 1D is (row - 1) * width + column if we start with 1
	private int xyTo1D(int row, int col) {
        return (row - 1) * gridSize + col;
    }
	
	public static void main(String[] args) {
	}
}
