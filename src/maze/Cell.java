package maze;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class Cell {
	private final int x, y;
	private final Line2D top, right, bottom, left;
	private boolean visited = false;
	public boolean drawTop, drawRight, drawBottom, drawLeft;

	static final int SIZE = 20;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;

		this.top = new Line2D.Double(x * SIZE, y * SIZE, (x + 1) * SIZE, y * SIZE);
		this.right = new Line2D.Double((x + 1) * SIZE, y * SIZE, (x + 1) * SIZE, (y + 1) * SIZE);
		this.bottom = new Line2D.Double(x * SIZE, (y + 1) * SIZE, (x + 1) * SIZE, (y + 1) * SIZE);
		this.left = new Line2D.Double(x * SIZE, y * SIZE, x * SIZE, (y + 1) * SIZE);

		drawTop = drawRight = drawBottom = drawLeft = true;
	}

	public void draw(Graphics2D g2d, Color color) {
		g2d.setColor(color);
		if (drawTop) g2d.draw(top);
		if (drawRight) g2d.draw(right);
		if (drawBottom) g2d.draw(bottom);
		if (drawLeft) g2d.draw(left);
	}

	public void fill(Graphics2D g2d, Color color) {
		g2d.setColor(color);
		g2d.fillRect(x * SIZE, y * SIZE, SIZE, SIZE);
	}

	public void visit() {
		visited = true;
	}

	public boolean isVisited() {
		return visited;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
