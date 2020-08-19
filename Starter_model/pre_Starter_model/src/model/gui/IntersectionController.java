package model.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import model.map.Intersection;
import model.map.Loc;

public class IntersectionController implements MouseListener {

	private Loc loc;
	private Loc locView;

	private Intersection is;

	private IntersectionView iv;
	
	private View view;

	IntersectionController(Loc loc, Loc locView, Intersection is, View view, IntersectionView iv) {
		this.loc = loc;
		this.locView = locView;
		this.is = is;
		this.view = view;
		this.iv = iv;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Button " + loc + " was pressed.");
		iv.colorView = Color.black;
		is.change();
		view.redraw();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public Loc getLocView() {
		return locView;
	}

	public Loc getLoc() {
		return loc;
	}

}
