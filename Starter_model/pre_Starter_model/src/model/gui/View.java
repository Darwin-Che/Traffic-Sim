package model.gui;

import model.map.*;
import model.traffic.Traffic;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class View {

	private Traffic trafficData;

	private Map<Loc, IntersectionView> locToView;

	private Map<Loc, IntersectionController> locToCtrl;

	private JFrame frame;

	private JSplitPane splitpane;

	private JPanel mapPanel;

	private JPanel statePanel;

	public View(Traffic tr) {
		trafficData = tr;

		mapPanel = new JPanel();

		statePanel = new JPanel();

		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel, statePanel);
		splitpane.setDividerLocation(0.7);
		splitpane.setEnabled(false);

		frame = new JFrame("Traffic Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(splitpane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public void loadView(List<IntersectionView> ivlst) {
		locToView = new HashMap<Loc, IntersectionView>();
		for (IntersectionView iv : ivlst) {
			locToView.put(iv.getLoc(), iv);
		}
	}

	public void loadCtrl(List<IntersectionController> iclst) {
		locToCtrl = new HashMap<Loc, IntersectionController>();
		for (IntersectionController ic : iclst) {
			locToCtrl.put(ic.getLoc(), ic);
		}
	}

	public void redraw() {
		drawRoad(mapPanel.getGraphics());
		drawIntersection(mapPanel.getGraphics());
		drawState(statePanel.getGraphics());
	}

	public void addControl() {
		for (Loc k : locToView.keySet()) {
			locToView.get(k).addMouseListener(locToCtrl.get(k));
		}
	}

	public void drawIntersection(Graphics g) {
		for (IntersectionView iv : locToView.values()) {
			iv.update(g);
		}
	}

	public void drawRoad(Graphics g) {
		List<Loc[]> allroute = trafficData.map.getAllRoute();
		for (Loc[] l : allroute) {
			g.drawLine(l[1].getX(), l[1].getY(), l[2].getX(), l[2].getY());
		}
	}

	public void drawState(Graphics g) {

	}

}
