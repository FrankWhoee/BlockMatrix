package net.vikingsdev.blockmatrix.gameobjects;

public class Trade {

	private Player sender, receiver;
	private int sendIndex, receiveIndex;
	
	public Trade (Player sender, Player receiver, int sendIndex, int receiveIndex) {
		this.sender = sender;
		this.receiver = receiver;
		
		this.sendIndex = sendIndex;
		this.receiveIndex = receiveIndex;
	}
	
	//boolean continue = ask use if they wanna continue;
	//set up server-client
	//while(continue) {		//there'll be an exit button all the time if they just want to bail on the trade except for after they confirm the trade
		//select item, confirm
		//writing(weaponToString(whatever weapon u chose));
		//send ur file, recieve the other person's
		//confirm = ask if you accept their weapon
	
		//if (confirm){
			//player.getInventory.add(stringToWeapon);
			//return
		//}
		//else
			//continue
	//}
	//return;
	
	
	public void completeTrade() {
		Item sendItem = sender.getInventory().get(sendIndex);
		Item receiveItem = receiver.getInventory().get(receiveIndex);
		
		sender.getInventory().set(sendIndex, receiveItem);
		receiver.getInventory().set(receiveIndex, sendItem);
	}
	
}
