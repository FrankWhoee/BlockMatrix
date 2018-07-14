package net.vikingsdev.blockmatrix.gameobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Event {
	public static final byte TITLE_PREFIX = 0, TITLE_PROPER = 1, TITLE_SUFFIX = 2;
	protected byte region; //where the title change occurs (prefix, proper, suffix		//better practice to make variables private and use set functions but whatever)
	
	protected String modifier; //weapon stat changes
	protected HashMap<String, Integer> modStats;
	
	//lol will find a more efficient way to do this later
	public ArrayList<ClickTriggerable> availableClickTriggerable = new ArrayList<>();
	public ArrayList<KillTriggerable> availableKillTriggerable = new ArrayList<>(Arrays.asList(new TenKills()));
	
	public Event() {	
	}
	
	public Event(String modifier, byte region) {
		this.modifier = modifier;
		this.region = region;
		
		modStats = new HashMap<String, Integer>();
	}
	
	//Accessors
	
	public String getModifier() {
		return modifier;
	}
	
	public int getRegion() {		//region is a byte not a long
		return (int)region;
	}
	
	public HashMap<String, Integer> getModStats() {
		return modStats;
	}


	
	//comment below is outdated
	/*every event has
	 * modification to stats
	 * function to stat hashmap (changes the modifications of the stats into a hashmap for easy addition into the weapon's previous stats)
	 * 
	 * a modification to the item name (
	 */
}