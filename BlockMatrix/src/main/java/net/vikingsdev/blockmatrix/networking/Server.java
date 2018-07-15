package net.vikingsdev.blockmatrix.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import net.vikingsdev.blockmatrix.gameobjects.Player;

public class Server {

	private static int uniqueID;
	private ArrayList<ClientThread> clients;
	private int port;
	private boolean keepGoing;
	
	private Player player;
	
	public Server(int port) {
		this.port = port;
	}
	
	public void start() {
		keepGoing = true;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while(keepGoing) {
				System.out.println("Server on port " + port);
				Socket socket = serverSocket.accept();
				
				if(!keepGoing) {
					break;
				}
				ClientThread t = new ClientThread(socket);
				clients.add(t);
				t.start();
			}
			try {
				serverSocket.close();
				for(ClientThread ct : clients) {
					try {
						ct.sInput.close();
						ct.sOutput.close();
						ct.socket.close();
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void stop() {
		keepGoing = false;
		Socket socket;
		
		try {
			socket = new Socket("216.71.221.211", port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void broadcast(String message) {
		System.out.print(message);
		
		for(int i = clients.size(); --i >= 0;) {
			ClientThread ct = clients.get(i);
			if(!ct.writeMsg(message)) {
				clients.remove(i);
			}
		}
	}
	
	synchronized void remove(int id) {
		for(int i = 0; i < clients.size(); ++i) {
			ClientThread ct = clients.get(i);
			if(ct.id == id) {
				clients.remove(i);
				return;
			}
		}
	}
	
	public static void main(String[] args) {
		int portNumber = 1500;
		switch(args.length) {
		case 1:
			try {
				portNumber = Integer.parseInt(args[0]);
			} catch (Exception e) {
				return;
			}
		case 0:
			break;
		default:
			return;
		}
		
		Server server = new Server(portNumber);
		server.start();
	}
	
	class ClientThread extends Thread{
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		
		int id;
		
		String username;
		
		ClientThread(Socket socket){
			id = ++uniqueID;
			this.socket = socket;
			
			try {
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput = new ObjectInputStream(socket.getInputStream());
				
				// username from player object
			} catch (IOException e) {
				return;
			}
		}
		
		public void run() {
			boolean keepGoing = true;
			while(keepGoing) {
				try {
					String string = (String) sInput.readObject();
				} catch (IOException e) {
					break;
				} catch (ClassNotFoundException e2) {
					break;
				}
				
			}
			remove(id);
			close();
		}
		
		
		private void close() {
			try {
				if(sOutput != null) sOutput.close();
			} catch(Exception e) {}
			
			try {
				if(sInput != null) sInput.close();
			} catch (Exception e) {}
			
			try {
				if(socket != null) socket.close();
			}
			catch(Exception e) {}
		}
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
				e.printStackTrace();
			}
			return true;
		}
	}
}


