package net.vikingsdev.blockmatrix;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.gameobjects.Trade;
import net.vikingsdev.blockmatrix.gameobjects.Weapon;

public class App {
	public static void main(String[] args) {	
		//add our blocks to the playerChain ArrayList:
		
		Blockchain.playerchain.add(new Block("Genesis Block", "0"));
		System.out.println("Trying to Mine block 1... ");
		Blockchain.playerchain.get(0).mineBlock(Blockchain.difficulty);
		
		System.out.println("Registering player...");
		Blockchain.register("my name tripple gay");
		Player p = Player.toPlayer(Blockchain.playerchain.get(1).getData());
		
		System.out.println("Player's name: " + p.getName());
		System.out.println("Player's id: " + p.getId());

		Game game = new Game(1280, 720, "Meme", p);
		game.start();
		
		// tester code
		Player sender = new Player("Sender");
		Player receiver = new Player("Receiver");
		
		Weapon sendItem = new Weapon("The glory hole of the sender");
		Weapon receiveItem = new Weapon("The magnum dong of the receiver");
		
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
