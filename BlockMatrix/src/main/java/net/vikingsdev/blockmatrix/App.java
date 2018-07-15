package net.vikingsdev.blockmatrix;

import java.io.IOException;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.gameobjects.Trade;
import net.vikingsdev.blockmatrix.gameobjects.Weapon;
import net.vikingsdev.blockmatrix.networking.Client;

public class App {
	public static void main(String[] args) {	

		
		// tester
		Client client1 = new Client("localhost", 1500, "BeefyBoi");
		Client client2 = new Client("localhost", 1500, "BlockchainBuster");

		Block bl = new Block("Yeet", "Yeet");
		System.out.println("Sent yeet block, " + bl.hash);
		try {
			byte index = 0;
			client1.send(index, bl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		System.out.println(Blockchain.toJson());
		
	}
}
