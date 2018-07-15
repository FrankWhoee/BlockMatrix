package net.vikingsdev.blockmatrix.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import net.vikingsdev.blockmatrix.gameobjects.Item;
import net.vikingsdev.blockmatrix.utils.StringUtil;

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
			g.drawRect(bounds.x - (bounds.width / 2) , bounds.y + (i * 64), bounds.width, bounds.height);
			StringUtil.centre(list.get(i).getName(), bounds.x + (bounds.height / 2), bounds.y + (i * 64) + (bounds.width / 2), g);
		}
		
		g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		StringUtil.centre("Allah be praised", bounds.x + (bounds.width / 2), bounds.y + (bounds.height / 2), g);
	}
}
