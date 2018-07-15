package net.vikingsdev.blockmatrix.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramSocket;
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
		
	}
}
