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
	
}



