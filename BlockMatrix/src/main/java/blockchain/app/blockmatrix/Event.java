package blockchain.app.blockmatrix;

import java.util.HashMap;

public abstract class Event {
	public static final byte TITLE_PREFIX = 0, TITLE_PROPER = 1, TITLE_SUFFIX = 2;
	protected byte region; //where the title change occurs (prefix, proper, suffix)
	
	protected String modifier; //weapon stat changes
	protected HashMap modStats;
	
	public Event(String modifier, byte region, ConditionReader cr) {
		this.modifier = modifier;
		this.region = region;
	}
	
	//Accessors
	
	public String getModifier() {
		return modifier;
	}
	
	public long getRegion() {
		return region;
	}
	
	public HashMap getModStats() {
		return modStats;
	}
	
	/*every event has
	 * modification to stats
	 * function to stat hashmap (changes the modifications of the stats into a hashmap for easy addition into the weapon's previous stats)
	 * 
	 * a modification to the item name (
	 */
}

class TenKills extends Event {
	public TenKills() {
		super("Sharp", Event.TITLE_PREFIX, new ConditionReader() {
			public void conditionMet() {
				//work on this later
			}
		});
		modStats.put("Damage", 1);
	}
}
