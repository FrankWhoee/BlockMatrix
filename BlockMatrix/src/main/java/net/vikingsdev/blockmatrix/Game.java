package net.vikingsdev.blockmatrix;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.gameobjects.TradeSequence;
import net.vikingsdev.blockmatrix.gameobjects.Weapon;
import net.vikingsdev.blockmatrix.gfx.Assets;
import net.vikingsdev.blockmatrix.gfx.Display;
import net.vikingsdev.blockmatrix.input.KeyManager;
import net.vikingsdev.blockmatrix.input.MouseManager;
import net.vikingsdev.blockmatrix.networking.Client;
import net.vikingsdev.blockmatrix.networking.Server;
import net.vikingsdev.blockmatrix.states.*;

import javax.swing.*;

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
	
	private GameState gameState;
	
	//input
	
	private KeyManager keyboard;
	private MouseManager mouse;
	
	private Client client;
	
	public ArrayList<Player> tradePlayers = new ArrayList<Player>();
	
	public Game(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		
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
	    
	    Blockchain.playerchain.add(new Block("Genesis Block", "0"));
		System.out.println("Trying to Mine block 1... ");
		Blockchain.playerchain.get(0).mineBlock(Blockchain.difficulty);
		
		System.out.println("Registering player...");
		Blockchain.register("TrippleNipple");
		player = Player.toPlayer(Blockchain.playerchain.get(1).getData());
		
		System.out.println("Player's name: " + player.getName());
		System.out.println("Player's id: " + player.getId());

		Blockchain.save();
		
		this.player.getInventory().add(new Weapon("Dagger"));
	}
	
	private void init() {
		// init player chain
		
		// start init Client
		int portNumber = Server.DEFAULT_PORT;
		String serverAddress = "localhost";
		String userName = player.getName();
		client = new Client(serverAddress, portNumber, userName);
		if(!client.start()) return;
		// end init Client
		
		Assets.init();
		
		player.getInventory().add(new Weapon("Sword"));
		player.getInventory().add(new Weapon("Spear"));
		player.getInventory().add(new Weapon("Shiv"));
		player.getInventory().add(new Weapon("Scimitar"));
		player.setActiveSlot(0);
		gameState.init();
	}
	
	public void update() {
		//game updates code
		
		if(State.getState() == null) {
			State.setState(gameState);
			mouse.setUIM(gameState.getUIM());
		}else State.getState().tick();
		
		//client.update();
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
		
		g.drawImage(Assets.gameBG, 0, 0, null);
		
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
		int timeMax = 10000;
		int frames = 0;
		ArrayList<Player> allPlayers;
		Player tradePartner;

		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTick) / timePerFrame;
			lastTick = now;
			
			if(delta >= 1) {
				update();
				render();
				delta--;
				frames++;
			}
			
			if(frames > timeMax) {
				Blockchain.mineLastBlock();
				/// check for trades
				
				/*INSERT FUNCTION TO UPDATE CHAIN*/
				TradeSequence sequence = new TradeSequence();
				allPlayers = Blockchain.getAllPlayers();
				for(Player p : allPlayers) {
					if(p.trade) {
						this.tradePlayers.add(p);
					}
				}
				if(tradePlayers.size()>1 && player.validTrade(tradePlayers)) {
					tradePartner = player.playerToTradeWith(tradePlayers);
					int mySelected;
					boolean go = getYesOrNoBool("Do you want to initiate a trade?");
					String sendThisTxt = System.getProperty("user.dir") + "/Communication/ToSend/Message.txt";
					//set up client-client connection
					
					while(go) {		//there'll be an exit button all the time if they just want to bail on the trade except for after they confirm the trade
						//select item, confirm
						mySelected = getIntInRange(1,player.getInventory().size(),"Which item in your inventory do you want to trade?") -1;
						Weapon selectedWeapon = (Weapon)(player.getInventory().get(mySelected)); //whatever weapon you choose
						sequence.writing(selectedWeapon.toJson());
						
						
						//send ur file, recieve the other person's
						App.objMsgCh.sendMessage(tradePartner.getId() + "").addFile(sequence.writeAndGetFile(selectedWeapon.toJson()));
						
						
						if (getYesOrNoBool("Do you want accept the trade?")){
							player.getInventory().add(Weapon.fromJson(sequence.readToString()));
							player.getInventory().remove(mySelected);
							player.setActiveSlot(0);
						}
						else
							continue;
					}
				}
				/*
				Blockchain.read();
				/// add trades to blockchain
				frames = 0;
				*/
			}
		}
		
		stop();
	}
	
    public int getIntInRange(int a, int b, String textToAsk){
        int input;
        while (true){
                input=getIntNoError(textToAsk);
                if(a <= input && input <= b)
                    return input;
        }
    }
    
    public int getIntNoError(String textToAsk){
        while (true){
            try{
                return getInteger(textToAsk);
            }catch(Exception e){
            }
        }
    }
	
    public int getInteger(String text){
        String s = JOptionPane.showInputDialog(null,text);
        int x = Integer.parseInt(s);
        return(x);
   }
	
    public String getString(String text){
        String s = JOptionPane.showInputDialog(null,text);
        return (s);
   }
    
    public Boolean getYesOrNoBool(String textToAsk){
        String input;
        while (true){
            try{
                input = getString(textToAsk);
                if (input.equals("yes"))
                    return true;
                else if (input.equals("no"))
                    return false;
            }catch(Exception e){
            }
        }
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
	
	public GameState getGameState() {
		return gameState;
	}
}
