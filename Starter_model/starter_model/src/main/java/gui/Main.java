package main.java.gui;

import java.awt.Color;

import javax.swing.JFrame;

public class Main {

	public static void main() {
		JFrame obj = new JFrame();
		
		MapPanel mapPanel = new MapPanel();
		
		obj.setBounds(10, 10, 905, 700);
		obj.add(mapPanel);
		obj.setBackground(Color.BLACK);
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.setVisible(true);
	}
	
}
