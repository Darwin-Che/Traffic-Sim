package model.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import model.map.GridMapLoc;
import model.map.MapLoc;
import model.traffic.Traffic;

public class Main {

	public static void main(String args[]) {
		MapLoc map = new GridMapLoc(5, 3);
		Traffic traffic = new Traffic(map);
		View view = new View(traffic, new Dimension(800, 500));
	}

}
