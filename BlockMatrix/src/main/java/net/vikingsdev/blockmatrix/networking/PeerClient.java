package net.vikingsdev.blockmatrix.networking;

import java.net.Socket;

public class PeerClient {

	public PeerClient() throws Exception {
		run();
	}
	
	public void run() throws Exception {
		Socket socket = new Socket("127.0.0.1",4164);
	}
	
}
