package net.vikingsdev.blockmatrix.states;

import java.awt.Graphics;

import net.vikingsdev.blockmatrix.Game;
import net.vikingsdev.blockmatrix.ui.UIManager;

public abstract class State {
	protected UIManager uim;
	protected Game game;
	
	private static State activeState = null;
	
	public State(Game game) {
		this.game = game;
		
		uim = new UIManager();
	}
	
	public abstract void init();
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public static State getState() {
		return activeState;
	}
	
	public static void setState(State state) {
		activeState = state;
	}
	
	public UIManager getUIM() {
		return uim;
	}
}
