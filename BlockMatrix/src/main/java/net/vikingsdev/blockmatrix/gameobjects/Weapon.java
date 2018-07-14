package net.vikingsdev.blockmatrix.gameobjects;

public class Weapon extends Item {
	private int kills;
	//every weapon has

    public Weapon(String name) {
    	super(name);
    	
        stats.put("Damage", 0);
    }
    
    public int getKills() {
    	return kills;
    }
}
