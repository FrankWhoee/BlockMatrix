package blockchain.app.blockmatrix;

public class Event {
	private String description;
	private long date;
	
	public Event(String desc, long date) {
		this.description = desc;
		this.date = date;
	}
	
	public String getDescription() {
		return description;
	}
	public long getDate() {
		return date;
	}
	
	/*every event has
	 * modification to stats
	 * function to stat hashmap (changes the modifications of the stats into a hashmap for easy addition into the weapon's previous stats)
	 * 
	 * a modification to the item name (
	 */
}
