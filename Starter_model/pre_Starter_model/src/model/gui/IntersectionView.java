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
import model.map.Route;
import model.map.Edge;
import model.map.Intersection;
import model.map.Light;
import model.map.Light.LIGHT;

public class IntersectionView extends JButton {

	private Loc loc;
	private Loc locView;
	public double radiusView;
	public Color colorView;
	public Intersection is;

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

	public IntersectionView(Loc tloc, Loc tlocView, double tradiusView, Color tcolorView) {
		loc = tloc;
		locView = tlocView;
		radiusView = tradiusView;
		colorView = tcolorView;
		is = null;
	}

	public Loc getLocView() {
		return locView;
	}

	public Loc getLoc() {
		return loc;
	}

//	public void addNeighbour(IntersectionView e) {
//		neighbours.add(e);
//
//	}

	@Override
	public void paintComponent(Graphics g) {
		// super.paintComponent(g);
		g.setColor(colorView);

		g.fillOval((int) (locView.getX() - radiusView), (int) (locView.getY() - radiusView), (int) (2 * radiusView),
				(int) (2 * radiusView));
		
		if (is == null) return;
		Map<Route, Light> tmp = is.getAllLight();
		if (tmp.isEmpty()) return;
		// create horizontal route
		Route r = null;
		Loc left = new Loc(loc.getX() - 1, loc.getY());
		Loc right = new Loc(loc.getX() + 1, loc.getY());
		Edge e1 = new Edge("", left, loc, 1);
		Edge e2 = new Edge("", loc, right, 1);
		try {
			r = new Route(e1, e2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		g.setColor(Color.green);
		if (tmp.get(r).getStatus() == Light.LIGHT.GREEN) {
			g.drawLine((int) (locView.getX() - radiusView), locView.getY(), (int) (locView.getX() + radiusView), locView.getY());
		} else {
			g.drawLine(locView.getX(), (int) (locView.getY() - radiusView), locView.getX(),(int) (locView.getY() + radiusView) );
		}
	}

	@Override
	public boolean contains(int x, int y) {
		Shape shape = new Ellipse2D.Float((int) (locView.getX() - radiusView), (int) (locView.getY() - radiusView),
				(int) (2 * radiusView), (int) (2 * radiusView));
		return shape.contains(x, y);
	}

}
