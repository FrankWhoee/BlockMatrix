package net.vikingsdev.blockmatrix.states;

import java.awt.Color;
import java.awt.Graphics;

import net.vikingsdev.blockmatrix.Game;
import net.vikingsdev.blockmatrix.gfx.Assets;
import net.vikingsdev.blockmatrix.ui.UIButton;
import net.vikingsdev.blockmatrix.ui.UIListener;

public class GameState extends State {
/*	private Game game;
	
	public GameState(Game game) {
		this.game = game;
	}
*/
	@Override
	public void init() {
		uim.addObject(new UIButton(100, 100, 256, 128, Assets.button[0], new UIListener() {
			//Inven
			@Override
			public void onClick() {
				System.out.println("You're have tripple gay");
			}
		}));
	}

	@Override
	public void tick() {
		//probably not needed but I'm keeping it for now
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(42, 69, 420, 12);
		
		uim.render(g);
	}
}
