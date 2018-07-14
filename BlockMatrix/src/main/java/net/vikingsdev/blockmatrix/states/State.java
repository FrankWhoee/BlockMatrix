package net.vikingsdev.blockmatrix.states;

import java.awt.Graphics;

public abstract class State {

	private static State currentState = null;
	public static void setState(State state) {
		currentState = state;
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
	
}
