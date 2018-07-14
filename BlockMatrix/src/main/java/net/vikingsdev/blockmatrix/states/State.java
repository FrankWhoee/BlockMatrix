package net.vikingsdev.blockmatrix.states;

import java.awt.Graphics;

import net.vikingsdev.blockmatrix.ui.UIManager;

public abstract class State {
	protected UIManager uim;
	
	private static State activeState = null;
	
	public State() {
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
