package net.vikingsdev.blockmatrix.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import net.vikingsdev.blockmatrix.gameobjects.Item;

public class UIList {
	private ArrayList<Item> list;
	private Rectangle bounds;
	
	public UIList(int x, int y, int width, int height, ArrayList<Item> list) {
		this.list = list;
		
		bounds = new Rectangle(x, y, width, height);
	}
	
	public void init() {
		
	}
	
	public void render(Graphics g) {
		// set formatting
		g.setFont(new Font("CourierNew", Font.PLAIN, 14));
		g.setColor(Color.WHITE);
		
		// loop through list and draw the text
		for(int i = 0; i < list.size(); i++) {
			g.drawRect(bounds.x - (bounds.width / 2) , bounds.y + i * 64, bounds.width, bounds.height);
			g.drawString(list + "", bounds.x , bounds.y + i * 64 + (bounds.height/2));
		}
		
		g.drawRect(bounds.x - (bounds.width / 2) , bounds.y, bounds.width, bounds.height);
		g.drawString(list + "", bounds.x , bounds.y + (bounds.height/2));
	}
}
