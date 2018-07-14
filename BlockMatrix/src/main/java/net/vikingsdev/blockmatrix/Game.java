package net.vikingsdev.blockmatrix;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.gfx.Assets;
import net.vikingsdev.blockmatrix.gfx.Display;
import net.vikingsdev.blockmatrix.input.KeyManager;
import net.vikingsdev.blockmatrix.input.MouseManager;
import net.vikingsdev.blockmatrix.states.*;

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
	
	//gameloop
	
	private Thread thread;
	private boolean running;
	
	public static final int MAX_FPS = 60;
	
	//states
	
	private State gameState;
	
	//input
	
	private KeyManager keyboard;
	private MouseManager mouse;
	
	public Game(int width, int height, String title, Player player) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.player = player;
		
	    keyboard = new KeyManager();
	    mouse = new MouseManager();
	 
		display = new Display(width, height, title);
		
		gameState = new GameState(this);
	 
	    display.getFrame().addKeyListener(keyboard);
	    display.getFrame().addMouseListener(mouse);
	    display.getFrame().addMouseMotionListener(mouse);
	    display.getFrame().addMouseWheelListener(mouse);
	 
	    display.getCanvas().addKeyListener(keyboard);
	    display.getCanvas().addMouseListener(mouse);
	    display.getCanvas().addMouseMotionListener(mouse);
	    display.getCanvas().addMouseWheelListener(mouse);
	}
	
	private void init() {
		Assets.init();
		
		gameState.init();
	}
	
	private void update() {
		//game updates code
		
		if(State.getState() == null) {
			State.setState(gameState);
			mouse.setUIM(gameState.getUIM());
		}else State.getState().tick();
	}
	
	private void render() {
		// set up rendering object
		buffer = display.getCanvas().getBufferStrategy();
		
		if(buffer == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = buffer.getDrawGraphics();
		
		g.clearRect(0, 0, width, height);
		
		// render zone
		
		if(State.getState() != null) State.getState().render(g);
		
		// clean up
		buffer.show();
		g.dispose();
	}
	
	public void run() {
		init();
		
		// game tick limiter
		double timePerFrame = 1000000000 / MAX_FPS;
		double delta = 0;
		long now;
		long lastTick = System.nanoTime();
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTick) / timePerFrame;
			lastTick = now;
			
			if(delta >= 1) {
				update();
				render();
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
	
	//Accessors
	
	public Player getPlayer() {
		return player;
	}
	
	public MouseManager getMouse() {
		return mouse;
	}
	
	public State getGameState() {
		return gameState;
	}
}
