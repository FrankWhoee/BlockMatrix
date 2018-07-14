package net.vikingsdev.blockmatrix.ui; 
 
import java.awt.Graphics;

import net.vikingsdev.blockmatrix.gfx.Assets; 
 
public class TradingOverlay extends UIOverlay { 
	public TradingOverlay(int x, int y, int width, int height) { 
		super(x, y, width, height); 
	} 
 
	@Override 
	public void init() {
		uim.addObject(new UIButton(1024, 128, 256, 128, Assets.button[0], new UIListener() {
			//Inventory
			@Override
			public void onClick() {
				
			}
		}));
	} 
 
	@Override 
	public void render(Graphics g) { 
		uim.render(g);
	} 
} 