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
	
	public void completeTrade() {
		Item sendItem = sender.getInventory().get(sendIndex);
		Item receiveItem = receiver.getInventory().get(receiveIndex);
		
		sender.getInventory().set(sendIndex, receiveItem);
		receiver.getInventory().set(receiveIndex, sendItem);
	}
	
}
