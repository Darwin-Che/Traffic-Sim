package model.gui;

import java.awt.Color;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import model.map.Loc;
import model.map.Light.LIGHT;

public class IntersectionView extends JButton {

	private Loc loc;
	private Loc locView;
	private int radiusView;
	private Color colorView;

//	private List<IntersectionView> neighbours;
//
//	private List<Arc2D> arcs;
//
//	private List<Color> states;
//
//	private List<Integer> changedIndices;

	private boolean hover;
	private boolean click;

//	private void calcArcs() throws Exception {
//		List<Integer> dir = new ArrayList<Integer>();
//
//		for (IntersectionView iv : neighbours) {
//			Loc to = iv.getLocView();
//			int dx = to.getX() - loc.getX();
//			int dy = to.getY() - loc.getY();
//			dir.add((int) (Math.atan2(dy, dx) / Math.PI * 180));
//		}
//		
//		for (IntersectionView)
//
//	}

	public IntersectionView(Loc tloc, Loc tlocView, int tradiusView, Color tcolorView) {
		loc = tloc;
		locView = tlocView;
		radiusView = tradiusView;
		colorView = tcolorView;
	}

	public Loc getLocView() {
		return locView;
	}

	public Loc getLoc() {
		return loc;
	}
//
//	public void addNeighbour(IntersectionView e) {
//		neighbours.add(e);
//
//	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(colorView);

		g2d.drawOval(locView.getX() - radiusView, locView.getY() - radiusView, 2 * radiusView, 2 * radiusView);

		g2d.dispose();
	}

}
