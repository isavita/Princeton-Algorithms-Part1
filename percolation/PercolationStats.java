import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] percolationThresholds;
	private int numberOfExperiments;

	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException();
		numberOfExperiments = trials;
		percolationThresholds = new double[trials];
		for (int i = 0; i < trials; i++) {
			int row,  col;
			double numberOfThreshold = 0;
			Percolation perc = new Percolation(n);
			while (!perc.percolates()) {
				row = StdRandom.uniform(1, n + 1);
				col = StdRandom.uniform(1, n + 1);
				if (!perc.isOpen(row, col)) {
					numberOfThreshold++;
					perc.open(row, col);
				}
			}
			percolationThresholds[i] = numberOfThreshold / (n * n);
		}
	}
	
	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(percolationThresholds);	
	}
	
	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(percolationThresholds);	
	}
	
	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - (1.96 * stddev() / Math.sqrt(numberOfExperiments));
	}
	
	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + (1.96 * stddev() / Math.sqrt(numberOfExperiments));
	}
	
	// test client (described below)
	public static void main(String[] args) {
		int gridSize = Integer.parseInt(args[0]);
		int numberOfTrials = Integer.parseInt(args[1]);
		PercolationStats percStats = new PercolationStats(gridSize, numberOfTrials);
		System.out.format("mean = %f\n", percStats.mean());
		System.out.format("stddev = %f\n", percStats.stddev());
		System.out.format("95%% confidence interval = %f, %f", percStats.confidenceLo(), percStats.confidenceHi());
	}
}
