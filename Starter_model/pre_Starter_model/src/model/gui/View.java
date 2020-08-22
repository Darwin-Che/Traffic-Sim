package model.gui;

import model.map.*;
import model.traffic.Traffic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;
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
	private JSplitPane stateSplitPane;
	private JPanel controlPanel;
	private JPanel textPanel;

	private JButton step;
	private JButton run;
	private JButton pause;

	public View(Traffic tr, Dimension framedim) {
		xViewMap = (int) (framedim.width * 0.7);
		yViewMap = framedim.height;

		trafficData = tr;

		mapPanel = new JPanel();
		mapPanel.setSize(xViewMap, yViewMap);
		mapPanel.setLayout(null);

		step = new JButton("step");
		step.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trafficData.paused = false;
				trafficData.step = true;
			}
		});
		run = new JButton("run");
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trafficData.paused = false;
				trafficData.step = false;
			}
		});
		pause = new JButton("pause");
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trafficData.paused = true;
				trafficData.step = false;
			}
		});

		controlPanel = new JPanel();
		controlPanel.add(step);
		controlPanel.add(run);
		controlPanel.add(pause);

		textPanel = new JPanel();

		stateSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, controlPanel, textPanel);
		stateSplitPane.setEnabled(false);

		statePanel = new JPanel();
		statePanel.setSize(framedim.width - xViewMap, yViewMap);

		statePanel.add(stateSplitPane);

		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel, statePanel);
		splitpane.setPreferredSize(framedim);
		splitpane.setDividerLocation(xViewMap);
		splitpane.setEnabled(false);

		frame = new JFrame("Traffic Simulator");
		frame.setPreferredSize(framedim);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(splitpane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(true);

		addViewButtons();
//
//		for (Loc k : locToView.keySet()) {
//			System.out.println(k);
//			System.out.println(locToView.get(k).getLocView());
//			System.out.println();
//		}
//
//		redraw();

	}

	public void addViewButtons() {

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

		List<IntersectionView> ivlst = new ArrayList<IntersectionView>();

		for (Loc l : allLoc) {
			Loc vloc = new Loc((int) ((1 + l.getX()) * xRoadSpace), (int) ((1 + l.getY()) * yRoadSpace));
			Color cx = Color.gray;
			if (trafficData.map.isIntersect(l))
				cx = Color.orange;
			IntersectionView iv = new IntersectionView(l, vloc, (xRoadSpace + yRoadSpace) / 10, cx);
			iv.setToolTipText(iv.getLoc().toString());
			ivlst.add(iv);
			mapPanel.add(iv);
		}

		List<IntersectionController> iclst = new ArrayList<IntersectionController>();

		for (IntersectionView iv : ivlst) {
			if (trafficData.map.isIntersect(iv.getLoc())) {
				IntersectionController ic = new IntersectionController(iv.getLoc(), iv.getLocView(),
						trafficData.map.getCross(iv.getLoc()), this, iv);
				iclst.add(ic);
				iv.is = trafficData.map.getCross(iv.getLoc());
			}
		}

//		loadView(ivlst);
//		loadCtrl(iclst);
//		addControl();
	}

	public void loadView(List<IntersectionView> ivlst) {
//		locToView = new TreeMap<Loc, IntersectionView>();
//		for (IntersectionView iv : ivlst) {
//			locToView.put(iv.getLoc(), iv);
//			mapPanel.add(iv);
//		}
	}

	public void loadCtrl(List<IntersectionController> iclst) {
//		locToCtrl = new TreeMap<Loc, IntersectionController>();
//		for (IntersectionController ic : iclst) {
//			locToCtrl.put(ic.getLoc(), ic);
//		}
	}

	public void redraw() {
//		mapPanel.paint(mapPanel.getGraphics());
//		drawRoad(mapPanel.getGraphics());
//		drawIntersection(mapPanel.getGraphics());
//		drawState(statePanel.getGraphics());
	}

	public void addControl() {
//		for (Loc k : locToCtrl.keySet()) {
//			locToView.get(k).addMouseListener(locToCtrl.get(k));
//		}
	}

	public void drawIntersection(Graphics g) {
//		for (IntersectionView iv : locToView.values()) {
//			iv.paintComponent(g);
//		}

	}

	public void drawRoad(Graphics g) {
//		List<Edge> allEdge = trafficData.map.getAllEdge();
//		g.setColor(Color.black);
//		for (Edge edge : allEdge) {
//			int x1 = locToView.get(edge.getFrom()).getLocView().getX();
//			int y1 = locToView.get(edge.getFrom()).getLocView().getY();
//			int x2 = locToView.get(edge.getTo()).getLocView().getX();
//			int y2 = locToView.get(edge.getTo()).getLocView().getY();
//			g.drawLine(x1, y1, x2, y2);
//			g.drawString(Integer.toString(trafficData.getAllUsers(edge).size()) + "/30", (x1 + x2) / 2 - 10,
//					(y1 + y2) / 2);
//		}
	}

	public void drawState(Graphics g) {
	}

}
