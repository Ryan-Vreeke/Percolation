
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

public class PercolationVisual extends Canvas implements Runnable, MouseListener
{

	public static final int WIDTH = 400, HEIGHT = 400;
	private Thread thread;
	static Color color = Color.WHITE;
	private boolean running = false;
	private int w;
	private int n;
	private Percolation p;
	private int row;
	private Box[][] set;

	/**
	 * 
	 */
	private static final long serialVersionUID = -6800175946442857266L;

	// set up all of the fields that will be used
	public PercolationVisual()
	{
		// size of the grid
		n = 20;
		// size that each box will need to be
		w = WIDTH / n;
		// length of the row
		row = WIDTH / w;
		// makes set, a two dimensional array size of the grid
		set = new Box[n][n];
		// makes a new percolation object with n size
		p = new Percolation(n);
		// add mouseListener to object
		this.addMouseListener(this);
		// creates a new widow with this object in it
		new Window(WIDTH + 6, HEIGHT + 29, "Percolation Visualization", this);
		// populates the set array
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < row; j++)
			{

				set[i][j] = new Box(i * w, j * w, w);
			}

		}

	}

	// starts a new Thread and sets running to true
	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	// stops the thread and sets running to false
	public synchronized void stop()
	{
		try
		{
			thread.join();
			running = false;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// this is the run loop, it calls tick and render at constant rate that you can
	// set with a field. it also counts the frames per second to manage performance
	@Override
	public void run()
	{
		long lastTime = System.nanoTime();
		double amountOfTicks = 30;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1)
			{
				tick();// calls tick
				delta--;
			}
			if (running)
			{
				render();// calls render

			}
			frames++;

			if (System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				// prints the frames per second to the console
				// System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	// keeps track of all the objects and events that occur and need to be updated
	// every frame. this will get called every 30 frames
	private void tick()
	{
		// this loop will check and update the color if the position isFilled
		for (int i = 0; i < set.length; i++)
		{
			for (int j = 0; j < set.length; j++)
			{
				// checks if current box isFull by passing the index to the isFull method in
				// percolation
				if (p.isFull(i, j))
				{
					// color set blue if full
					set[j][i].color = Color.BLUE;
				}
				if (p.isOpen(i, j) && !p.isFull(i, j))
				{
					// otherwise it is white if open and not full
					set[j][i].color = Color.white;
				}
			}
		}
	}

	// renders all of the graphics
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(color);
		g.fillRect(0, 0, n * w, n * w);

		g.setColor(color.black);

		// this will just call the render option for the box object in the array set

		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < row; j++)
			{
				set[i][j].render(g);

			}

		}

		g.dispose();
		bs.show();

	}

	public static void main(String[] args)
	{
		new PercolationVisual();
	}

	// gets mouse location and gives that info to the open method in percolation
	@Override
	public void mouseClicked(MouseEvent arg0)
	{

		// gets the mouse location and turns it into a float between 0 and 1
		float x = arg0.getX() / 400f;
		float y = arg0.getY() / 400f;
		// floors the product of mouse location and the number of elements on the
		// screen, this allow you to keep track of what box the mouse is clicking on
		int j = (int) Math.floor(x * n);
		int i = (int) Math.floor(y * n);
		// opens that location
		p.open(i, j);

	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// unused method from mouseListener

	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// unused method from mouseListener

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// unused method from mouseListener

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// unused method from mouseListener

	}

}
