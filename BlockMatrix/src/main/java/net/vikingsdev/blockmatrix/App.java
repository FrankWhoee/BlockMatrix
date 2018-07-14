package net.vikingsdev.blockmatrix;

import net.vikingsdev.blockmatrix.gameobjects.Player;

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

	}
}
