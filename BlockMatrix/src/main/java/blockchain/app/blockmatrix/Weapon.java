package blockchain.app.blockmatrix;

import java.util.ArrayList;
import java.util.HashMap;

public class Weapon extends Item{
	//every weapon has

    private String name;
    private HashMap stats = new HashMap();
    private ArrayList<Event> history = new ArrayList<Event>();

    public Weapon(String name) {
        this.name = name;
    }
}
