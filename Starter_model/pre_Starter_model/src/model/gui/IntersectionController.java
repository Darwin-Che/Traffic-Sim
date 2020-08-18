package model.gui;

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

	private View view;

	@Override
	public void mouseClicked(MouseEvent e) {

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
