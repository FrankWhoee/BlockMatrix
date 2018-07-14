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
        stats.put("Damage", 0);
    }

    public String getName() {
        return name;
    }

    public HashMap getStats() {
        return stats;
    }

    public ArrayList<Event> getHistory() {
        return history;
    }

    public void addToHistory(Event event){
        history.add(event);
        stats.put("Damage", stats.get("Damage"));
    }
}
