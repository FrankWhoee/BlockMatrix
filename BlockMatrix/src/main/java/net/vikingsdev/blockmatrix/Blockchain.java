package net.vikingsdev.blockmatrix;

import java.util.ArrayList;

import net.vikingsdev.blockmatrix.gameobjects.Player;

public class Blockchain {
	public static ArrayList<Block> playerchain = new ArrayList<Block>();
	public static int difficulty = 5;
	
	
	
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
	
	public static void parseLocalJson() {
		
	}
}
