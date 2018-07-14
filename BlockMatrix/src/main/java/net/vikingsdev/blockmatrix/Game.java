package net.vikingsdev.blockmatrix;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.gfx.Display;

public class Game implements Runnable{

	private Display display;
	private Player player;
	
	private Thread thread;
	
	public Game(Display display, Player player) {
		this.display = display;
		this.player = player;
	}
	
	private void init() {
		
	}
	
	private void update() {
		
	}
	
	private void render() {
		
	}
	
	public void run() {
		init();
		
		while(true) {
			update();
			render();
		}
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
