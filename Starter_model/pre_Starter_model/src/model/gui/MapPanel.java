package model.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Map;

import javax.swing.JPanel;

import model.map.Loc;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = 2518184280997213007L;

	public Map<Loc, IntersectionView> locToView;
	
	@Override
	public void paintComponent(Graphics g) {
		
		
		super.paintComponent(g);
	}
}