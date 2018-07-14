package net.vikingsdev.blockmatrix.ui; 
 
import java.awt.Color;
import java.awt.Graphics;

import net.vikingsdev.blockmatrix.Game;
import net.vikingsdev.blockmatrix.gfx.Assets; 
 
public class SettingsOverlay extends UIOverlay { 
	public SettingsOverlay(int x, int y, int width, int height, Game game) { 
		super(x, y, width, height, game); 
	} 
 
	@Override 
	public void init() { 
		uim.addObject(new UIButton(1024, 128, 256, 128, Assets.button[0], new UIListener() {
			//Back
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
