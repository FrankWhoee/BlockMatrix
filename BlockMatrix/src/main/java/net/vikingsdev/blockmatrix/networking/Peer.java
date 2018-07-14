package net.vikingsdev.blockmatrix.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Peer {
	
	public Peer() {
		int port = 4164;
		try (
				ServerSocket serverSocket = new ServerSocket(port);
				Socket clientSocket = serverSocket.accept();
			){
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
