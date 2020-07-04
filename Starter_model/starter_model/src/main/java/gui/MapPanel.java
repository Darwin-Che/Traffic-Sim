package main.java.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = 2518184280997213007L;

	public MapPanel() {

	}

	public void paint(Graphics g) {
		drawMap(g);
	}

	private void drawMap(Graphics g) {
		// draw title image border
		g.setColor(Color.white);
		g.drawRect(24, 10, 851, 55);
	}
}
