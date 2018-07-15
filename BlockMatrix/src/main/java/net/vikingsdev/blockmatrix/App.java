package net.vikingsdev.blockmatrix;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.gameobjects.Trade;
import net.vikingsdev.blockmatrix.gameobjects.Weapon;
import net.vikingsdev.blockmatrix.networking.Peer;

public class App {
	public static void main(String[] args) {	
		
		System.out.println(System.getProperty("user.dir"));
		
		/*try {
			Blockchain.sendFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//add our blocks to the playerChain ArrayList:
		
		Game game = new Game(1280, 720, "BeefyBoi's BlockchainBasher");
		game.start();
		
		// tester code
		Player sender = new Player("Sender");
		Player receiver = new Player("Receiver");
		
		Weapon sendItem = new Weapon("Brutal waraxe of Supreme Jeremius");
		Weapon receiveItem = new Weapon("Master Devito's mighty magnum dong");
		
		sender.getInventory().add(sendItem);
		receiver.getInventory().add(receiveItem);
		
		System.out.println("Sender item: " + sender.getInventory().get(0));
		System.out.println("Receiver item: " + receiver.getInventory().get(0));
		
		Trade trade = new Trade(sender, receiver, 0, 0);
		trade.completeTrade();
		
		System.out.println("Sender item: " + sender.getInventory().get(0));
		System.out.println("Receiver item: " + receiver.getInventory().get(0));
		
	}
}
