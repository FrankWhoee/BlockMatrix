package net.vikingsdev.blockmatrix.gfx;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {

	private JFrame frame;
	private Canvas canvas;
	
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		canvas = new Canvas();
		canvas.setPreferredSize(size);
		canvas.setMaximumSize(size);
		canvas.setMinimumSize(size);
		
		frame.add(canvas);
		frame.pack();
	}
	
}
