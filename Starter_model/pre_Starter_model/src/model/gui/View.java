package model.gui;

import model.map.*;
import model.traffic.Traffic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class View {

	private int xViewMap;
	private int yViewMap;

	private Traffic trafficData;

	private Map<Loc, IntersectionView> locToView;

	private Map<Loc, IntersectionController> locToCtrl;

	private JFrame frame;

	private JSplitPane splitpane;

	private JPanel mapPanel;

	private JPanel statePanel;

	public View(Traffic tr, Dimension framedim) {
		xViewMap = (int) (framedim.width * 0.7);
		yViewMap = framedim.height;

		trafficData = tr;

		mapPanel = new JPanel();
		mapPanel.setSize(xViewMap, yViewMap);

		statePanel = new JPanel();
		statePanel.setSize(framedim.width - xViewMap, yViewMap);

		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel, statePanel);
		splitpane.setPreferredSize(framedim);
		splitpane.setDividerLocation(xViewMap);
		// splitpane.setEnabled(false);

		frame = new JFrame("Traffic Simulator");
		frame.setPreferredSize(framedim);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(splitpane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		addViewButtons();
		drawIntersection(mapPanel.getGraphics());
		drawRoad(mapPanel.getGraphics());

		for (Loc k : locToView.keySet()) {
			System.out.println(k);
			System.out.println(locToView.get(k).getLocView());
			System.out.println();
		}

	}

	public void addViewButtons() {

		List<IntersectionView> ivlst = new ArrayList<IntersectionView>();

		List<Loc> allLoc = trafficData.map.getAllLoc();
		int xRoadNum = 0, yRoadNum = 0;
		for (Loc l : allLoc) {
			if (l.getX() > xRoadNum)
				xRoadNum = l.getX();
			if (l.getY() > yRoadNum)
				yRoadNum = l.getY();
		}
		int xRoadSpace = xViewMap / (2 + xRoadNum);
		int yRoadSpace = yViewMap / (2 + yRoadNum);

		for (Loc l : allLoc) {
			Loc vloc = new Loc((int) ((1 + l.getX()) * xRoadSpace),(int) ((1 + l.getY()) * yRoadSpace));
			Color cx = ((l.getX() + l.getY()) % 2 == 0) ? Color.green : Color.red;
			IntersectionView iv = new IntersectionView(l, vloc, (xRoadSpace + yRoadSpace) / 10, cx);
			ivlst.add(iv);
			mapPanel.add(iv);
		}

		loadView(ivlst);

		System.out.println();
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
			iv.paintComponent(g);
		}

	}

	public void drawRoad(Graphics g) {
		List<Edge> allEdge = trafficData.map.getAllEdge();
		for (Edge edge : allEdge) {
			System.out.println(edge);
//			g.drawLine(locToView.get(l[0]).getLocView().getX(), locToView.get(l[0]).getLocView().getY(),
//					locToView.get(l[1]).getLocView().getX(), locToView.get(l[1]).getLocView().getY());
		}
	}

	public void drawState(Graphics g) {

	}

}
