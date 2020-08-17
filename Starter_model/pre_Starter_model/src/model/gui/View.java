package model.gui;

import model.map.*;
import model.traffic.Traffic;

import java.awt.Graphics;
import java.util.*;

public class View {

	private Traffic trafficData;

	private Map<Loc, IntersectionView> locToView;

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

	public void drawControl(Graphics g);

}
