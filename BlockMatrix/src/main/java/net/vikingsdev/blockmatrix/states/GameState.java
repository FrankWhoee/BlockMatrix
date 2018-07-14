package net.vikingsdev.blockmatrix.states;

import java.awt.Color;
import java.awt.Graphics;

import net.vikingsdev.blockmatrix.Game;
import net.vikingsdev.blockmatrix.gfx.Assets;
import net.vikingsdev.blockmatrix.ui.UIButton;
import net.vikingsdev.blockmatrix.ui.UIListener;

public class GameState extends State {
	public GameState(Game game) {

	}

	@Override
	public void init() {
		uim.addObject(new UIButton(1024, 128, 256, 128, Assets.button[0], new UIListener() {
			//Inventory
			@Override
			public void onClick() {
				
			}
		}));
		
		uim.addObject(new UIButton(1024, 256, 256, 128, Assets.button[1], new UIListener() {
			//Trading
			@Override
			public void onClick() {
				
			}
		}));
		
		uim.addObject(new UIButton(192, 448, 256, 128, Assets.button[2], new UIListener() {
			//Wage jihad on the infidels inshallah
			@Override
			public void onClick() {
				
			}
		}));
	}

	@Override
	public void tick() {
		uim.tick();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.eventBackground,768,0,null);
		g.drawImage(Assets.forestBG,0,32,null);
		g.drawImage(Assets.orc,128,32,null);
		g.drawImage(Assets.statsBackground, 832, 384, null);
		
		g.setColor(Color.GRAY);
		g.fillRect(42, 69, 550, 12);
		
		uim.render(g);
	}
}
