package net.thebitspud.chatapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * The server that can be run as either a standalone console application or a GUI
 */

public class Server {
	// a unique ID for each connection
	private static int uniqueID;
	private ArrayList<ClientThread> clients;

	private ServerGUI serverGUI;
	
	private SimpleDateFormat sdf;
	private int port;
	
	private boolean keepGoing; //Keeps the thread running
	
	public Server(int port) {
		this(port, null);
	}
	
	public Server(int port, ServerGUI serverGUI) {
		this.serverGUI = serverGUI;
		this.port = port;
		
		sdf = new SimpleDateFormat("HH:mm:ss");
		clients = new ArrayList<ClientThread>();
	}
	
	public void start() {
		keepGoing = true;
		/* create a socket server and wait for connection requests */
		try{
			ServerSocket serverSocket = new ServerSocket(port);

			while(keepGoing) {
				display("Server waiting for Clients on port " + port + ".");
				
				Socket socket = serverSocket.accept();
				
				if(!keepGoing) break;
				
				ClientThread ct = new ClientThread(socket);
				clients.add(ct);
				ct.start();
			}
			
			//Stop
			
			try{
				serverSocket.close();
				for(ClientThread ct : clients) {
					
					try {
						ct.sInput.close();
						ct.sOutput.close();
						ct.socket.close();
					}catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				display("Exception closing the server and clients: " + e);
			}
		}
		
		catch (IOException e) {
			e.printStackTrace();
            String msg = sdf.format(new Date()) + " Exception on Server Socket: " + e + "\n";
			display(msg);
		}
	}
	
    /*
     * For the GUI to stop the server
     */
	
	protected void stop() {
		keepGoing = false;
		Socket socket;
		
		// connect to myself as Client to exit statement 
		// Socket socket = serverSocket.accept();
		
		try {
			socket = new Socket("216.71.221.211", port);
			
			/*try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}*/
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Display an event (not a message) to the console or the GUI
	 */
	
	private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
		if(serverGUI == null) System.out.println(time);
		else serverGUI.appendEvent(time + "\n");
	}
	
	/*
	 *  to broadcast a message to all Clients
	 */
	
	private synchronized void broadcast(String message) {
		// add HH:mm:ss and \n to the message
		String time = sdf.format(new Date());
		String messageLf = time + " " + message + "\n";
		// display message on console or GUI
		if(serverGUI == null)
			System.out.print(messageLf);
		else
			serverGUI.appendRoom(messageLf);     // append in the room window
		
		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected
		for(int i = clients.size(); --i >= 0;) {
			ClientThread ct = clients.get(i);
			// try to write to the Client if it fails remove it from the list
			if(!ct.writeMsg(messageLf)) {
				clients.remove(i);
				display("Disconnected Client " + ct.username + " removed from list.");
			}
		}
	}

	// for a client who logs off using the LOGOUT message
	
	synchronized void remove(int id) {
		// scan the array list until we found the Id
		for(int i = 0; i < clients.size(); ++i) {
			ClientThread ct = clients.get(i);
			// found it
			if(ct.id == id) {
				clients.remove(i);
				return;
			}
		}
	}
	
	/*
	 *  To run as a console application just open a console window and: 
	 * > java Server
	 * > java Server portNumber
	 * If the port number is not specified 1500 is used
	 */
	
	public static void main(String[] args) {
		// start server on port 1500 unless a PortNumber is specified 
		int portNumber = 1500;
		switch(args.length) {
			case 1:
				try {
					portNumber = Integer.parseInt(args[0]);
				}
				catch(Exception e) {
					System.out.println("Invalid port number.");
					System.out.println("Usage is: > java Server [portNumber]");
					return;
				}
			case 0:
				break;
			default:
				System.out.println("Usage is: > java Server [portNumber]");
				return;
				
		}
		// create a server object and start it
		Server server = new Server(portNumber);
		server.start();
	}

	/** One instance of this thread will run for each client */
	
	class ClientThread extends Thread {
		// the socket where to listen/talk
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		// my unique id (easier for deconnection)
		int id;
		// the Username of the Client
		String username;
		// the only type of message a will receive
		Message cm;
		// the date I connect
		String date;

		// Constructor
		ClientThread(Socket socket) {
			// a unique id
			id = ++uniqueID;
			this.socket = socket;
			/* Creating both Data Stream */
			System.out.println("Thread trying to create Object Input/Output Streams");
			try
			{
				// create output first
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput  = new ObjectInputStream(socket.getInputStream());
				// read the username
				username = (String) sInput.readObject();
				display(username + " just connected.");
			}
			catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}
			// have to catch ClassNotFoundException
			// but I read a String, I am sure it will work
			catch (ClassNotFoundException e) {
			}
            date = new Date().toString() + "\n";
		}

		// what will run forever
		public void run() {
			// to loop until LOGOUT
			boolean keepGoing = true;
			while(keepGoing) {
				// read a String (which is an object)
				try {
					cm = (Message) sInput.readObject();
				}
				catch (IOException e) {
					display(username + " Exception reading Streams: " + e);
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}
				// the messaage part of the Message
				String message = cm.getMessage();

				// Switch on the type of message receive
				switch(cm.getType()) {

				case Message.MESSAGE:
					broadcast(username + ": " + message);
					break;
				case Message.LOGOUT:
					display(username + " disconnected with a LOGOUT message.");
					keepGoing = false;
					break;
				case Message.USERLIST:
					writeMsg("List of the users connected at " + sdf.format(new Date()) + "\n");
					// scan al the users connected
					for(int i = 0; i < clients.size(); ++i) {
						ClientThread ct = clients.get(i);
						writeMsg((i+1) + ") " + ct.username + " since " + ct.date);
					}
					break;
				}
			}
			// remove myself from the arrayList containing the list of the
			// connected Clients
			remove(id);
			close();
		}
		
		// try to close everything
		private void close() {
			// try to close the connection
			try {
				if(sOutput != null) sOutput.close();
			}
			catch(Exception e) {}
			try {
				if(sInput != null) sInput.close();
			}
			catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			}
			catch (Exception e) {}
		}

		/*
		 * Write a String to the Client output stream
		 */
		private boolean writeMsg(String msg) {
			// if Client is still connected send the message to it
			if(!socket.isConnected()) {
				close();
				return false;
			}
			// write the message to the stream
			try {
				sOutput.writeObject(msg);
			}
			// if an error occurs, do not abort just inform the user
			catch(IOException e) {
				display("Error sending message to " + username);
				display(e.toString());
			}
			return true;
		}
	}
}
