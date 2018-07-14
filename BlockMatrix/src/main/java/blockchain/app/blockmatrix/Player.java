package blockchain.app.blockmatrix;

import java.util.ArrayList;

//only gui (front end) is public
public class Player {

    public Player(String name) {
        this.name = name;
    }

    private String name;
	private ArrayList<Item> Inventory = new ArrayList<Item>();

    public String getName() {
        return name;
    }

    public ArrayList<Item> getInventory() {
        return Inventory;
    }


    //get name
	//get inventory
	
}
