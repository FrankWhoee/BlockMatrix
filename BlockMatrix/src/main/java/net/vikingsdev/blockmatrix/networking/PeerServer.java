package net.vikingsdev.blockmatrix.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerServer {

	private final int port = 4164;
	
	public PeerServer() throws Exception {
		run();
	}
	
	public void run() throws Exception{
		ServerSocket serverSocket = new ServerSocket(port);
		Socket clientSocket = serverSocket.accept();
	}
	
}
