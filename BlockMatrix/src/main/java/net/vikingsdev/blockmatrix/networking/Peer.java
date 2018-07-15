package net.vikingsdev.blockmatrix.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import net.vikingsdev.blockmatrix.Game;

public class Peer {

	private String ip = "localhost";
	private int port = 4164;
	private Scanner scanner = new Scanner(System.in);
	
	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;
	
	private ServerSocket serverSocket;
	
	private Game game;
	
	private boolean host = true;
	private boolean accepted = false;
	
	public Peer() throws Exception {
		update();
	}
	
	public void update() {
		if(!host && !accepted) {
			listenForServerRequest();
		}
	}
	
	private void listenForServerRequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
			System.out.println("Accepted");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean connect() {
		try {
			socket = new Socket(ip, port);
			dos = new DataOutputStream(socket.getOutputStream());
			dos.flush();
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
		} catch(IOException e) {
			System.out.println("Unable to connect to server. Starting server");
			return false;
		}
		System.out.println("Connected");
		return true;
	}
	
	private void initializeServer() {
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
		} catch(Exception e) {
			e.printStackTrace();
		}
		host = false;
	}
}
