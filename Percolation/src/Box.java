import java.awt.Color;
import java.awt.Graphics;

class Box
{
	int x;
	int y;
	int size;
	Color color;

	// constructor with the location and size of rectangle
	public Box(int x, int y, int size)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		color = Color.BLACK;
	}

	// render object that gets called by the main render
	public void render(Graphics g)
	{
		g.setColor(color);
		g.fillRect(x, y, size - 1, size - 1);
	}

}