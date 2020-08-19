package model.gui;

import java.awt.Color;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
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
	public Color colorView;

//	private List<IntersectionView> neighbours;
//
//	private List<Arc2D> arcs;
//
//	private List<Color> states;
//
//	private List<Integer> changedIndices;

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
		// super.paintComponent(g);
		g.setColor(colorView);
		
		g.fillOval(locView.getX() - radiusView, locView.getY() - radiusView, 2 * radiusView, 2 * radiusView);

	}
	
	@Override
	public boolean contains(int x, int y) {
		Shape shape = new Ellipse2D.Float(locView.getX() - radiusView, locView.getY() - radiusView, 2 * radiusView, 2 * radiusView);
		return shape.contains(x, y);
	}

}
