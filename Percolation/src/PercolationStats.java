import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
	private static final double CONFIDENCE_NUM = 1.96;
	private final int trials;
	private final double [] results;
	
	// perform independent trials on an n-by-n grid
	 public PercolationStats(int n, int trials) {
		 this.trials = trials;
		 if (n <= 0 || trials <= 0)
			 throw new IllegalArgumentException();
		 this.results = new double[trials];
		 
		 for (int i = 0; i < trials; i++) {
			Percolation percolation = new Percolation(n);
			 int openedSites = 0;
			 while (!percolation.percolates()) {
				 int x = StdRandom.uniform(1, n + 1);
				 int y = StdRandom.uniform(1, n + 1);
				 if (!percolation.isOpen(x, y)) {
					 percolation.open(x, y);
				 }
			 }
			 double fraction = (double) openedSites / (n * n);
			 results[i] = fraction;
		 }
	}
	 // sample mean of percolation threshold
	 public double mean() {
		return StdStats.mean(results); 
	}
	 // sample standard deviation of percolation threshold
	 public double stddev() {
		 return StdStats.stddev(results);
	 }
	 // low endpoint of 95% confidence interval
	 public double confidenceLo() {
		 return mean() - ((CONFIDENCE_NUM * stddev()) / Math.sqrt(trials));
	 }
	 // high endpoint of 95% confidence interval
	 public double confidenceHi() {
		 return mean() + ((CONFIDENCE_NUM * stddev()) / Math.sqrt(trials));
	 }
	 // test client(see below)
	 public static void main(String [] args) {
		 int n = StdIn.readInt();
		 int trials = StdIn.readInt();
		 
		 PercolationStats stats = new PercolationStats(n, trials);
		 StdOut.println("mean = " + stats.mean());
		 StdOut.println("stddev = " + stats.stddev());
		 StdOut.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
	}
}
