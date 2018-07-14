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


}

//events



//NEED TO ADD MORE EVENTS
//a low priority task below
//cuz we want the items to be unique, ideally some of it would also be random, like maybe instead of gaining a "sharp" quality you might instead get like "finely crafted," but mechanically will be the same, and maybe there would be a small chance instead of getting +! dmg, you get +2
