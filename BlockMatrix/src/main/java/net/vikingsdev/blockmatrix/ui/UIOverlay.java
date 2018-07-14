package net.vikingsdev.blockmatrix.ui; 
 
import java.awt.Graphics; 
import java.awt.Rectangle; 
 
public abstract class UIOverlay { 
	protected UIManager uim; 
	protected boolean active; 
	protected Rectangle bounds; 
   
	public UIOverlay(int x, int y, int width, int height) { 
		bounds = new Rectangle(x, y, width, height); 
		
		active = false;
		uim = new UIManager();
	} 
   
	public abstract void init(); 
	public abstract void render(Graphics g); 
   
	public void tick() { 
		uim.tick(); 
	 } 
} 