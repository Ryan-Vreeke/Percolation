
public class PercolationStats
{
	private final int N, T;
	private Percolation p;
	private double sumOfXt;
	private double[] g;

	public PercolationStats(int N, int T)
	{
		// T independent experiments on an N-by-N Grid

		if (N <= 0 || T <= 0)
		{
			throw new IllegalArgumentException();
		}

		this.T = T;
		this.N = N;
		sumOfXt = 0;
		g = new double[T];

		for (int i = 0; i < T; i++)
		{

			p = new Percolation(N);

			while (!p.percolates())
			{
				int x = StdRandom.uniform(N);
				int y = StdRandom.uniform(N);

				p.open(x, y);

			}

			sumOfXt += p.getNumOf() / (N * N);
			g[i] = p.getNumOf() / (N * N);
		}

	}

	public double mean()// sample mean of percolation threshold
	{

		return sumOfXt / T;
	}

	public double stddev()// sample standard deviation of percolation threshold
	{
		double top = 0;
		for (int i = 0; i < g.length; i++)
		{
			top += Math.pow(g[i] - mean(), 2);
		}

		return Math.sqrt(top / (T - 1));
	}

	public double confidenceLow()// low endpoint of 95% confidence interrval
	{
		double top = 1.96 * Math.sqrt(stddev());
		return mean() - (top / Math.sqrt(T));
	}

	public double confidenceHigh()// high endpoint of 95% confidence interval
	{
		double top = 1.96 * Math.sqrt(stddev());
		return mean() + (top / Math.sqrt(T));
	}

	public static void main(String[] args)
	{
		PercolationStats hh = new PercolationStats(200, 100);

		System.out.println(hh.mean());
		System.out.printf("%1.20f%n", hh.stddev());
		System.out.printf("%1.20f%n", hh.confidenceLow());
		System.out.printf("%1.20f%n", hh.confidenceHigh());

	}

}
