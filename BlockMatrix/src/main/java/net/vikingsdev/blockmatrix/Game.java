package net.vikingsdev.blockmatrix;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.gfx.Assets;
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
		Assets.init();
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
		g.drawImage(Assets.invButtonRest, 0, 0, null);
		g.drawImage(Assets.invButtonHover, 0, 128, null);
		g.drawImage(Assets.tradeButtonRest, 0, 256, null);
		g.drawImage(Assets.tradeButtonHover, 0, 472, null);
		
		// clean up
		buffer.show();
		g.dispose();
	}
	
	public void run() {
		init();
		
		int MAX_FPS = 60;
		double timePerFrame = 1000000000 / MAX_FPS;
		double delta = 0;
		long now;
		long lastTick = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTick) / timePerFrame;
			timer += now - lastTick;
			lastTick = now;
			
			if(delta >= 1) {
				update();
				render();
				ticks++;
				delta--;
			}
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
