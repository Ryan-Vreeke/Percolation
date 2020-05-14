import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Window(int width, int height, String title, PercolationVisual game)
	{
		JFrame frame = new JFrame();

		frame.setPreferredSize(new Dimension(width, height));
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.pack();
		frame.setVisible(true);

		game.start();

	}

	public Window(int width, int height, String title, PSimulation game)
	{
		JFrame frame = new JFrame();

		frame.setPreferredSize(new Dimension(width, height));
		frame.setTitle(title);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setVisible(true);

		game.start();

	}
}
