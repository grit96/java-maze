package maze;

import java.awt.EventQueue;

public class Main {
	private void createAndShowGUI() {
		Maze g = new Maze();
		g.start();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Main m = new Main();
				m.createAndShowGUI();	
			}
		});
	}
}
