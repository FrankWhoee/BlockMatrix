package net.vikingsdev.blockmatrix.gameobjects;

import java.util.HashMap;

public abstract class Event {
	public static final byte TITLE_PREFIX = 0, TITLE_PROPER = 1, TITLE_SUFFIX = 2;
	protected byte region; //where the title change occurs (prefix, proper, suffix		//better practice to make variables private and use set functions but whatever)
	
	protected String modifier; //weapon stat changes
	protected HashMap<String, Integer> modStats;
	
	public Event(String modifier, byte region) {
		this.modifier = modifier;
		this.region = region;
		
		modStats = new HashMap<String, Integer>();
	}
	
	//Accessors
	
	public String getModifier() {
		return modifier;
	}
	
	public long getRegion() {		//region is a byte not a long
		return region;
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

class TenKills extends Event {		//will implement one of the interfaces for conditionMet function to work		//lets make another one for TenClicks to try out the ClickTriggerable interface
	public TenKills() {
		super("Sharp", Event.TITLE_PREFIX);
		
		new ClickTriggerable() {
			public Boolean conditionMet(int clicks) {
				return null;
			}
		};
			/*public void conditionMet( what we put here depends on which interface it implements ) {		//should be boolean not void		//and maybe use protected instead of public
				//what arguments it asks for will depend on which interface TenKills implements (eg. clicks, playTime, kills, dmgDealt (can be anything that is tracked by the weapon(by this i mean stored as a variable in weapon)))
				
				//work on this later
			}*/
		modStats.put("Damage", 1);
	}
}

//a low priority task below
//cuz we want the items to be unique, ideally some of it would also be random, like maybe instead of gaining a "sharp" quality you might instead get like "finely crafted," but mechanically will be the same, and maybe there would be a small chance instead of getting +! dmg, you get +2
