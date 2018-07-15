package net.vikingsdev.blockmatrix.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import net.vikingsdev.blockmatrix.Game;
import net.vikingsdev.blockmatrix.gameobjects.Item;
import net.vikingsdev.blockmatrix.utils.StringUtil;

public class UIList {
	private Game game;
	private ArrayList<Item> list;
	private Rectangle bounds;
	
	public UIList(int x, int y, int width, int height, Game game) {
		this.game = game;
		
		bounds = new Rectangle(x, y, width, height);
	}
	
	public void init() {
		this.list = game.getPlayer().getInventory();
	}
	
	public void render(Graphics g) {
		// set formatting
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", Font.BOLD, 16));
		
		// loop through list and draw the text
		for(int i = 0; i < list.size(); i++) {
			if(game.getPlayer().getActiveSlot() == i) {
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(bounds.x , bounds.y + (i * 64), bounds.width, bounds.height);
				g.setColor(Color.WHITE);
			}
			
			g.drawRect(bounds.x , bounds.y + (i * 64), bounds.width, bounds.height);
			StringUtil.centre(list.get(i).getName(), bounds.x + (bounds.width / 2), bounds.y + (i * 64) + (bounds.height / 2), g);
		}
	}
}
