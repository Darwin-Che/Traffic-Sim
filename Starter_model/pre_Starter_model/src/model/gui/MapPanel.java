package model.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = 2518184280997213007L;

	int xRoadNum, yRoadNum;
	int xWinLimit, yWinLimit;
	
	public MapPanel() {
		xRoadNum = 9;
		yRoadNum = 9;
		xWinLimit = 850;
		yWinLimit = 850;
	}

	public void paint(Graphics g) {
		drawMap(g);
		g.dispose();
	}

	private void drawMap(Graphics g) {
		
		int xRoadSpace = xWinLimit / (1 + xRoadNum);
		int yRoadSpace = yWinLimit / (1 + yRoadNum);
		double xShiftPerc = 0.1;
		double yShiftPerc = 0.1;
		g.setColor(Color.white);
		for (int i = 1; i <= xRoadNum; i++) {
			int tmp1 = (int) (xRoadSpace * (i - xShiftPerc));
			int tmp2 = (int) (xRoadSpace * (i + xShiftPerc));
			g.drawLine(tmp1, 0, tmp1, yWinLimit);
			g.drawLine(tmp2, 0, tmp2, yWinLimit);
		}
		for (int i = 1; i <= yRoadNum; i++) {
			int tmp1 = (int) (yRoadSpace * (i - yShiftPerc));
			int tmp2 = (int) (yRoadSpace * (i + yShiftPerc));
			g.drawLine(0, tmp1, xWinLimit, tmp1);
			g.drawLine(0, tmp2, xWinLimit, tmp2);
		}
	}
}