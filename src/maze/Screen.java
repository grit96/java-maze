package maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JPanel;


public class Screen extends JPanel implements MouseListener {
	private static final int COLS = 20;
	private static final int ROWS = 20;

	private static final int WIDTH = Cell.SIZE * COLS;
	private static final int HEIGHT = Cell.SIZE * ROWS;

	private Cell[] grid;
	private Stack<Cell> stack;

	private Cell current;
	private Cell end;
	private int distance;

	public Screen() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.GRAY);

		init();

		addMouseListener(this);
	}

	private void init() {
		grid = new Cell[ROWS * COLS];

		for (int y = 0; y < ROWS; y++) {
			for (int x = 0; x < COLS; x++) {
				grid[y * COLS + x] = new Cell(x, y);
			}
		}

		distance = 0;

		current = grid[0];
		current.visit();

		stack = new Stack<Cell>();
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		for (Cell c : grid) {
			if (c.isVisited()) c.fill(g2d, Color.RED);
			c.draw(g2d, Color.DARK_GRAY);
		}

		current.fill(g2d, Color.GREEN);
		end.fill(g2d, Color.BLUE);
	}

	public void tick() {
		ArrayList<Cell> neighbours = getNeighbours(current);
		if (neighbours.size() > 0) {
			Cell next = pickCell(neighbours);
			stack.push(current);
			next.visit();
			removeWalls(current, next);
			current = next;

			if (stack.size() > distance) {
				end = current;
				distance = stack.size();
			}
		} else if (!stack.isEmpty()) {
			current = stack.pop();
		}
	}

	private void removeWalls(Cell current, Cell next) {
		int diffY = next.getY() - current.getY();
		int diffX = next.getX() - current.getX();

		if (diffY == -1) {
			next.drawBottom = false;
			current.drawTop = false;
		}
		if (diffX == 1) {
			next.drawLeft = false;
			current.drawRight = false;
		}
		if (diffY == 1) {
			next.drawTop = false;
			current.drawBottom = false;
		}
		if (diffX == -1) {
			next.drawRight = false;
			current.drawLeft = false;
		}
	}

	private Cell pickCell(ArrayList<Cell> neighbours) {
		Random r = new Random();
		int index = r.nextInt(neighbours.size());
		return neighbours.get(index);
	}

	public void render() {
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	private Cell getCell(int x, int y) {
		int index = y * COLS + x;
		if (x < 0 || x >= COLS || y < 0 || y >= ROWS) return null;
		return grid[index];
	}

	private Cell getUnvisitedCell(int x, int y) {
		Cell cell = getCell(x, y);
		if (cell == null || cell.isVisited()) return null;
		return cell;
	}

	private ArrayList<Cell> getNeighbours(Cell cell) {
		Cell topCell = getUnvisitedCell(cell.getX(), cell.getY() - 1);
		Cell rightCell = getUnvisitedCell(cell.getX() + 1, cell.getY());
		Cell bottomCell = getUnvisitedCell(cell.getX(), cell.getY() + 1);
		Cell leftCell = getUnvisitedCell(cell.getX() - 1, cell.getY());

		ArrayList<Cell> neighbours = new ArrayList<Cell>();

		if (topCell != null) neighbours.add(topCell);
		if (rightCell != null) neighbours.add(rightCell);
		if (bottomCell != null) neighbours.add(bottomCell);
		if (leftCell != null) neighbours.add(leftCell);

		return neighbours;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		init();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
