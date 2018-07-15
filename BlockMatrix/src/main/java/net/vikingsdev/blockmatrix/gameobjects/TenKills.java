package net.vikingsdev.blockmatrix.gameobjects;


class TenKills extends Event implements KillTriggerable {		//will implement one of the interfaces for conditionMet function to work		//lets make another one for TenClicks to try out the ClickTriggerable interface
	int killsReq = 10;
	public TenKills() {
		super("Sharp", Event.TITLE_PREFIX);
		super.name = "TenKills";
		modStats.put("Damage", 1);
	}

	public boolean conditionMet(int kills) {
		if(kills >= killsReq)
			return true;
		else
			return false;
	}
}

