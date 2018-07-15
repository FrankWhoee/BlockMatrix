package net.vikingsdev.blockmatrix.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	//I/O stuff
	
	private ObjectInputStream sInput;
	private ObjectOutputStream sOutput;
	private Socket socket;
	
	//Server stuff
	
	private String server, username;
	private int port;

	public Client(String server, int port, String username) {
		this.server = server;
		this.port = port;
		this.username = username;
		System.out.print("Client created at port " + port + " and address " + server + ". Client username is " + username);
	}
	
	public boolean start() {
		//Attempt establishing a connection to the server
		
		try{
			socket = new Socket(server, port);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		String msg = "Connection accepted at " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);
	
		//Creating I/O data streams
		
		try{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}catch(IOException e) {
			e.printStackTrace();
			return false;
		}

		//Creating a Thread to listen from the server 
		
		new ListenFromServer().start();
		
		//Sending our username to the server
		
		/*try{
			sOutput.writeObject(username);
		}catch (IOException e) {
			e.printStackTrace();
			disconnect();
			return false;
		}*/
		
		//Return successful!
		
		return true;
	}

	//Sending messages to the console
	
	private void display(String msg) {
		System.out.println(msg);
	}
	
	//Messaging the server
	
	public void sendFile(String file) {
		try {
			sOutput.writeObject(file);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	//Close the I/O streams and disconnect from the server
	
	private void disconnect() {
		try{ 
			if(sInput != null) sInput.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try{
			if(sOutput != null) sOutput.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
        try{
			if(socket != null) socket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int portNumber = Server.DEFAULT_PORT;
		String serverAddress = "localhost";
		String userName = "Anonymous";

		//Switch depending on the number of input arguments
		
		switch(args.length) {
			// > javac Client [username] [portNumber] [serverAddress]
			case 3:
				serverAddress = args[2];
			// > javac Client [username] [portNumber]
			case 2:
				try {
					portNumber = Integer.parseInt(args[1]);
				}catch(Exception e) {
					System.out.println("Invalid port number.");
					System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
					
					e.printStackTrace();
					return;
				}
			// > javac Client [username]
			case 1: 
				userName = args[0];
			// > java Client
			case 0:
				break;
			//Invalid number of arguments
			default:
				System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
			return;
		}
		
		Client client = new Client(serverAddress, portNumber, userName);
		
		//Test the connection to the server
		
		if(!client.start()) return;
		
		//Waiting for messages in an infinite loop (attach to main gameloop eventually)
		
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			System.out.print("> ");
			// read message from user
			String file = scan.nextLine();
			// logout if message is LOGOUT
			if(file.equalsIgnoreCase("LOGOUT")) {
				client.sendFile("LOGOUT");
				// break to do the disconnect
				break;
			}
			// message WhoIsIn
			else if(file.equalsIgnoreCase("WHOISIN")) {
				client.sendFile("USERLIST");				
			}
			else {				// default to ordinary message
				client.sendFile("MESSAGE");
			}
		}		
		//Disconnect the client from the server
		
		scan.close();
		client.disconnect();	
	}

	public void update() {
		
	}
	
	//A built in class that monitors server input
	
	class ListenFromServer extends Thread {
		public void run() {
			while(true) {
				try {
					String file = (String) sInput.readObject();
						System.out.print("> ");
						System.out.println(file);
				}catch(IOException e) {
					e.printStackTrace();
					display("Server has close the connection: " + e);
					
					break;
				}catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

