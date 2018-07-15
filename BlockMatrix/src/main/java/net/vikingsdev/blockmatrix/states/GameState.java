package net.vikingsdev.blockmatrix.states;

import java.awt.Color;
import java.awt.Graphics;

import net.vikingsdev.blockmatrix.Game;
import net.vikingsdev.blockmatrix.gfx.Assets;
import net.vikingsdev.blockmatrix.ui.*;

public class GameState extends State {
	private UIOverlay inventory, settings, trading;
	private int currHealth;
	
	public GameState(Game game) {
		super(game);
		currHealth = 10;
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
			public void onClick() {		//attack
				dealDamage();
			}
		}));
		
		/* game.getMouse().setUIM(settings.getUIM());
				settings.setActive(true); */
	}
	
	//stuff for the attack button
	private void dealDamage() {
		if(super.game.getPlayer().getInventory().size()<1) {
			System.out.println("You don't have a weapon!");
		}
		else{
		int damage = super.game.getPlayer().getWeapon().getStats().get("Damage");
		int addKills = damage/10;
		if(damage - (addKills*10)>=currHealth){
			addKills++;
			currHealth = currHealth + 10 - damage;
		}
		else
			currHealth -= damage;
		super.game.getPlayer().getWeapon().addKills(addKills);
		super.game.getPlayer().getWeapon().addClick();
		super.game.getPlayer().getWeapon().update();
		}
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
		
		// stats rendering
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(32, 70, 540, 20);
		
		g.setColor(Color.RED);
		g.fillRect(34, 72, 536, 16);
		
		if(settings.isActive()) settings.render(g);
		else if(inventory.isActive()) inventory.render(g);
		else if(trading.isActive()) trading.render(g);
		else uim.render(g);
	}
}
