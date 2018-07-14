package net.vikingsdev.blockmatrix;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.gfx.Display;

public class Game implements Runnable{

	// display parameters
	private int width, height;
	private String title;
	
	// game objects
	private Display display;
	private Player player;
	
	// rendering
	private BufferStrategy buffer;
	private Graphics g;
	
	private BufferedImage image;
	
	private Thread thread;
	
	private boolean running;
	
	public Game(int width, int height, String title, Player player) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.player = player;
	}
	
	private void init() {
		display = new Display(width, height, title);
	}
	
	private void update() {
		
	}
	
	private void render() {
		buffer = display.getCanvas().getBufferStrategy();
		if(buffer == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = buffer.getDrawGraphics();
		
		// render zone
		g.drawRect(0, 0, 50, 50);
		// clean up
		buffer.show();
		g.dispose();
	}
	
	public void run() {
		init();
		
		while(running) {
			update();
			render();
		}
		
		stop();
	}
	
	public synchronized void start() {
		if(running) return;
		
		running = true;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running) return;
		
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
