package gui;

import java.awt.Color;

import javax.swing.JFrame;

public class Main {

	public static void main(String args[]) {
		JFrame obj = new JFrame();
		
		MapPanel mapPanel = new MapPanel();
		
		obj.setBounds(10, 10, 1200, 900);
		obj.add(mapPanel);
		obj.setBackground(Color.BLACK);
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.setVisible(true);
	}
	
}

