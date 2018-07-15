package net.thebitspud.chatapp;

import java.io.Serializable;

/*
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the Server. 
 * When talking from a Java Client to a Java Server it is a lot easier to pass Java objects, as there is no 
 * need to count bytes or to wait for a line feed at the end of the frame
 */

public class Message implements Serializable {
	protected static final long serialVersionUID = 1112122200L;

	//The different types of message sent by the Client
	//USERLIST to receive the list of the users connected
	//MESSAGE an ordinary message
	//LOGOUT to disconnect from the Server
	
	public static final int USERLIST = 0, MESSAGE = 1, LOGOUT = 2;
	private int type;
	private String message;
	
	public Message(int type, String message) {
		this.type = type;
		this.message = message;
	}
	
	//Accessors
	
	public int getType() {
		return type;
	}
	
	public String getMessage() {
		return message;
	}
}

