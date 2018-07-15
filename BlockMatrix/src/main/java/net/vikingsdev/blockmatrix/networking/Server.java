package net.vikingsdev.blockmatrix.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server {
	private static int uniqueID; //Server ID
	private ArrayList<ClientThread> clients;
	
	private ServerGUI serverGUI;
	private SimpleDateFormat sdf;
	
	public static final int DEFAULT_PORT = 1500;
	private int port;
	
	private boolean keepGoing; //Server will run until the thread is stopped
	
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
		//System.out.println("gay started");
		try{
			ServerSocket serverSocket = new ServerSocket(port);

			while(keepGoing) {
				display("Server waiting for Clients on port " + port + ".");
				
				Socket socket = serverSocket.accept();
				
				if(!keepGoing) break;
				
				ClientThread ct = new ClientThread(socket);
				clients.add(ct);
				ct.start();
				
				// receive input
				byte objType = -1;
				try {
					objType = ct.sInput.readByte();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*
				 * byte Object Type: The type of data being put through and how it should be handled
				 * 0 - Block
				 * 1 - Weapon
				 */
				switch(objType) {
				case 0:
					// block
					System.out.println("Block received mf0");
					
					try {
						broadcast(objType, ct.sInput.readObject());
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 1:
					// weapon
					System.out.println("Block received mf0");
					break;
				case -1:
					System.out.println("Block received mf-1");
					break;
				}
				
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
	
	protected void stop() {
		keepGoing = false;
		Socket socket;
		
		try {
			socket = new Socket("localhost", port);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
		if(serverGUI == null) System.out.println(time);
		else serverGUI.appendEvent(time + "\n");
	}
	
	private synchronized void broadcast(byte index, Object object) {
		for(int i = clients.size(); --i >= 0;) {
			ClientThread ct = clients.get(i);
			if(!ct.send(index, object)) {
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
		int portNumber = DEFAULT_PORT;
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
				System.out.println("yah yeet");
				try{
					String str = (String) sInput.readObject();
				}catch (IOException e) {
					e.printStackTrace();
					break;
				}catch (ClassNotFoundException e) {
					e.printStackTrace();
					break;
				}
				
				// receive input
				byte objType = -1;
				try {
					objType = sInput.readByte();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*
				 * byte Object Type: The type of data being put through and how it should be handled
				 * 0 - Block
				 * 1 - Weapon
				 */
				switch(objType) {
				case 0:
					// block
					System.out.println("Block received mf");
					try {
						broadcast(objType, sInput.readObject());
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 1:
					// weapon
					System.out.println("Block received mf");
					break;
				case -1:
					System.out.println("Block received mf");
					break;
				default:
					System.out.println("gay af");
					break;
				}
				
			}
			
			remove(id);
			//close();
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
		
		private boolean send(byte index, Object object) {
			// if Client is still connected send the message to it
			if(!socket.isConnected()) {
				close();
				return false;
			}
			// write the message to the stream
			try {
				sOutput.writeObject(object);
			}
			// if an error occurs, do not abort just inform the user
			catch(IOException e) {
				e.printStackTrace();
			}
			return true;
		}
	}
}


