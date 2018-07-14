package net.vikingsdev.blockmatrix;

import java.util.ArrayList;

import com.google.gson.GsonBuilder;

import net.vikingsdev.blockmatrix.gameobjects.Player;

public class App {
	public static ArrayList<Block> playerchain = new ArrayList<Block>();
	public static int difficulty = 5;

	public static void main(String[] args) {	
		//add our blocks to the playerChain ArrayList:
		
		playerchain.add(new Block("Genesis Block", "0"));
		System.out.println("Trying to Mine block 1... ");
		playerchain.get(0).mineBlock(difficulty);
		
		System.out.println("Registering player...");
		register("my name tripple gay");
		Player p = Player.toPlayer(playerchain.get(1).getData());
		
		System.out.println("Player's name: " + p.getName());
		System.out.println("Player's id: " + p.getId());

		Game game = new Game(1280, 720, "Meme", p);
		game.start();
		
		String playerchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(playerchain);
		System.out.println("\nThe block chain: ");
		System.out.println(playerchainJson);
	}
	
	public static void register(String name) {
		Player newPlayer = new Player(name);
		playerchain.add(new Block(newPlayer.toJsonString(), playerchain.get(playerchain.size()-1).hash));
		playerchain.get(playerchain.size()-1).mineBlock(difficulty);
		
	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		
		//loop through playerchain to check hashes:
		for(int i=1; i < playerchain.size(); i++) {
			currentBlock = playerchain.get(i);
			previousBlock = playerchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}
}
