import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{

	private WeightedQuickUnionUF f, d;
	public boolean[][] p;
	private final int TOP;
	private final int BOTTOM;
	private final int N;
	private double numOf;

	public Percolation(int n)
	{
		p = new boolean[n][n];
		f = new WeightedQuickUnionUF((n * n) + 2);
		d = new WeightedQuickUnionUF((n * n) + 2);
		BOTTOM = n * n;
		TOP = BOTTOM + 1;
		N = n;

		for (int i = 0; i < n; i++)
		{
			f.union(i, TOP);
			f.union((n * n - 1) - i, BOTTOM);
			// this was used to make sure backwash didn't occur
			// making a new union that doesn't union to the bottom ever
			d.union(i, TOP);

		}

	}

	public void open(int i, int j)
	{
		if (!p[i][j])
		{
			numOf++;// adds open locations
			p[i][j] = true;
		} else
			return;// stops the method because that location is already open, saving computing time

		int center = transform(i, j);

		List<Integer> check = new ArrayList<Integer>();

		check.add(transform(i, j - 1));// Left square
		check.add(transform(i, j + 1));// right
		check.add(transform(i - 1, j));// top
		check.add(transform(i + 1, j));// bottom

		// loops only 4 times not matter how big N is, making it very efficient.
		for (int o : check)
		{
			/*
			 * I used this to get the index numbers of the 4 locations that can be
			 * connected. this was done to avoid array out of bounds exception
			 */

			int x = o % N;// this will give the j location by doing transform in reverse
			int y = (o - x) / N;// gives i index by doing transform in reverse

			if (y < 0 || x < 0 || y > N - 1 || x > N - 1)
			{
				// System.out.println("out");
			} else
			{
				// this was to stop from connecting boxes on the edges in certain locations.
				if (j == 0 && x == N - 1 || j == N - 1 && x == 0)
				{

				} else if (p[y][x])
				{
					f.union(o, center);// unions center, being the original location with either the left, right, top,
										// or bottom
					d.union(o, center);
				}
			}
		}

	}

	public boolean isOpen(int i, int j)
	{
		return p[i][j];
	}

	public boolean isFull(int i, int j)
	{

		// checks if location is connected to d's top
		return d.connected(transform(i, j), TOP) && isOpen(i, j);
	}

	public boolean percolates()
	{
		// f is used to check percolation because it has a top and bottom while d does
		// not
		return f.connected(BOTTOM, TOP);
	}

	private int transform(int i, int j)
	{

		return (i * N) + j;// this will give the 2d location so it can be union

	}

	// returns number of open locations
	public double getNumOf()
	{
		return numOf;
	}

}
