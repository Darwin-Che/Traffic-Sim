package main.java.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JPanel;

public class Window {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 630, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(165, 37, 60, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel size_lbl_1 = new JLabel("Graph Size: ");
		size_lbl_1.setBounds(22, 42, 74, 16);
		frame.getContentPane().add(size_lbl_1);
		
		JLabel size_lbl_2 = new JLabel("#Rows");
		size_lbl_2.setBounds(116, 42, 47, 16);
		frame.getContentPane().add(size_lbl_2);
		
		JLabel size_lbl_3 = new JLabel("*  #Cols");
		size_lbl_3.setBounds(237, 42, 60, 16);
		frame.getContentPane().add(size_lbl_3);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(302, 37, 60, 26);
		frame.getContentPane().add(textField_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(403, 37, 221, 435);
		frame.getContentPane().add(panel);
	}
}
