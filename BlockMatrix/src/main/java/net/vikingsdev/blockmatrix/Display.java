package net.vikingsdev.blockmatrix;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {

	private JFrame frame;
	private String title;
	private int width, height;
	private Dimension size;
	
	public Display(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.size = new Dimension(width, height);
		
	}
	
	public void init() {
		frame = new JFrame(title);
		frame.setSize(size);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	
}
