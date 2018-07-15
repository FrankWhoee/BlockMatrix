package net.vikingsdev.blockmatrix.ui; 
 
import java.awt.Color;
import java.awt.Graphics;

import net.vikingsdev.blockmatrix.Game;
import net.vikingsdev.blockmatrix.gfx.Assets; 
 
public class TradingOverlay extends UIOverlay { 
	public TradingOverlay(int x, int y, int width, int height, Game game) { 
		super(x, y, width, height, game);
	} 
 
	@Override 
	public void init() {
		uim.addObject(new UIButton(732, 12, 64, 64, Assets.button[7], new UIListener() {
			//Back
			@Override
			public void onClick() {
				setActive(false);
				game.getMouse().setUIM(game.getGameState().getUIM());
			}
		}));
		
		uim.addObject(new UIButton(720 - 128, 592 - 128, 256, 128, Assets.button[5], new UIListener() {
			// accept button
			@Override
			public void onClick() {
				game.getPlayer().setActiveSlot(-1);
			}
		}));  
		
		uim.addObject(new UIButton(720 - 128, 592, 256, 128, Assets.button[6], new UIListener() {
			// trade button
			@Override
			public void onClick() {
				setActive(false);
				game.getMouse().setUIM(game.getGameState().getUIM());
			}
		}));  
	} 
 
	@Override 
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		uim.render(g);
	} 
} 