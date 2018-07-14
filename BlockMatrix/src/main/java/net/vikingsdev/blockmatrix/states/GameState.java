package net.vikingsdev.blockmatrix.states;

import java.awt.Color;
import java.awt.Graphics;

import net.vikingsdev.blockmatrix.Game;
import net.vikingsdev.blockmatrix.gfx.Assets;
import net.vikingsdev.blockmatrix.ui.*;

public class GameState extends State {
	private UIOverlay inventory, settings, trading;
	
	public GameState(Game game) {
		super(game);
		
		inventory = new InventoryOverlay(720, 0, 560, 720, game);
		settings = new SettingsOverlay(720, 0, 560, 720, game);
		trading = new TradingOverlay(720, 0, 560, 720, game);
	}

	@Override
	public void init() {
		inventory.init();
		settings.init();
		trading.init();
		
		uim.addObject(new UIButton(1024, 128, 256, 128, Assets.button[0], new UIListener() {
			//Inventory
			@Override
			public void onClick() {
				game.getMouse().setUIM(inventory.getUIM());
				inventory.setActive(true);
			}
		}));
		
		uim.addObject(new UIButton(1024, 256, 256, 128, Assets.button[1], new UIListener() {
			//Trading
			@Override
			public void onClick() {
				game.getMouse().setUIM(trading.getUIM());
				trading.setActive(true);
			}
		}));
		
		uim.addObject(new UIButton(192, 448, 256, 128, Assets.button[2], new UIListener() {
			//Wage jihad on the infidels inshallah
			@Override
			public void onClick() {
				//unused
			}
		}));
		
		/* game.getMouse().setUIM(settings.getUIM());
				settings.setActive(true); */
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.eventBackground,768,0,null);
		g.drawImage(Assets.forestBG,0,32,null);
		g.drawImage(Assets.orc,128,32,null);
		g.drawImage(Assets.statsBackground, 832, 384, null);
		
		g.setColor(Color.GRAY);
		g.fillRect(42, 69, 550, 12);
		
		if(settings.isActive()) settings.render(g);
		else if(inventory.isActive()) inventory.render(g);
		else if(trading.isActive()) trading.render(g);
		else uim.render(g);
	}
}
