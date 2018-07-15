package net.vikingsdev.blockmatrix.gameobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AvailableEvents {
	private ArrayList<ClickTriggerable> availableClickTriggerable = new ArrayList<>();
	private ArrayList<KillTriggerable> availableKillTriggerable = new ArrayList<>(Arrays.asList(new TenKills()));
	private HashMap<String,Event> availableByName = new HashMap<String,Event>();
	
	public AvailableEvents() {
		availableByName.put("TenKills", new TenKills());
	}
	
	public HashMap<String,Event> getAvailableByName(){
		return availableByName;
	}
	
	public Event getEventByName(String key) {
		return availableByName.get(key);
	}
	
	public ArrayList<ClickTriggerable> getAvailableClickTriggerable(){
		return availableClickTriggerable;
	}
	
	public ArrayList<KillTriggerable> getAvailableKillTriggerable(){
		return availableKillTriggerable;
	}
}
