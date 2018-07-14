package net.vikingsdev.blockmatrix.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import net.vikingsdev.blockmatrix.gameobjects.Item;

public class UIList {

	private ArrayList<Item> list;
	private int x, y, width, height;
	
	public UIList(int x, int y, int width, int height, ArrayList<Item> list) {
		this.list = list;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void init() {
		
	}
	
	public void render(Graphics g) {
		// set formatting
		g.setFont(new Font("CourierNew", Font.PLAIN, 14));
		g.setColor(Color.WHITE);
		
		// loop through list and draw the text
		for(int i = 0; i < list.size(); i++) {
			g.drawString(list.get(i).toString(),x,y + (height/2));
			y += 64;
		}
	}
	
}
