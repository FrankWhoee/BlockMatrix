package net.vikingsdev.blockmatrix.gameobjects;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Item {
	protected String name;
	protected ArrayList<Event> history;
	protected HashMap<String, Integer> stats;
	
	public Item(String name) {
		this.name = name;
		
		history = new ArrayList<Event>();
		stats = new HashMap<String, Integer>();
		
		name = statsToName();
	}
	
	public String statsToName() {
		//unused
		return "Jeremy's large rod of hydration";
	}
	
    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getStats() {
        return stats;
    }

    public ArrayList<Event> getHistory() {
        return history;
    }

    public void addToHistory(Event event){
        history.add(event);
        stats.put("Damage", (int) (stats.get("Damage")) + (int) (event.getModStats().get("Damage")));
    }
	
	/*every item has:
	*string name
	*hashmap stats
	*arraylist<Event> history
	*
	*name and stats depend on history
	*
	*function for turning history into stats
	*/

}
