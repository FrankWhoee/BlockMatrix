package net.vikingsdev.blockmatrix.ui; 
 
import java.awt.Graphics; 
import java.awt.Rectangle;

import net.vikingsdev.blockmatrix.Game; 
 
public abstract class UIOverlay { 
	protected UIManager uim;
	protected Game game;
	protected boolean active; 
	protected Rectangle bounds; 
   
	public UIOverlay(int x, int y, int width, int height, Game game) { 
		this.game = game;
		
		bounds = new Rectangle(x, y, width, height); 
		
		active = false;
		uim = new UIManager();
	} 
   
	public abstract void init(); 
	public abstract void render(Graphics g); 
	
	//Accessors and mutators

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public UIManager getUIM() {
		return uim;
	}

	public Rectangle getBounds() {
		return bounds;
	}
} 