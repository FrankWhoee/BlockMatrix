package net.vikingsdev.blockmatrix;


import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.vikingsdev.blockmatrix.utils.StringUtil;

public class Block {
	
	public String hash;
	public String previousHash; 
	private String data; //our data will be a simple message.
	private long timeStamp; //as number of milliseconds since 1/1/1970.
	private int nonce;
	
	//Block Constructor.  
	public Block(String data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}
	

	
	
	public Block(String hash, String previousHash, String data, long timeStamp, int nonce) {
		super();
		this.hash = hash;
		this.previousHash = previousHash;
		this.data = data;
		this.timeStamp = timeStamp;
		this.nonce = nonce;
	}




	public String getData() {
		return data;
	}
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public static Block fromJson(String json) {
		
//		public String hash;
//		public String previousHash; 
//		private String data; //our data will be a simple message.
//		private long timeStamp; //as number of milliseconds since 1/1/1970.
//		private int nonce;
		
		Gson gson = new Gson();

    	JsonParser jp = new JsonParser();
    	JsonObject jo = jp.parse(json).getAsJsonObject();
    	
    	String hash = jo.get("hash").getAsString();
    	String previousHash = jo.get("previousHash").getAsString();
    	String data = jo.get("data").getAsString();
    	Long timeStamp = jo.get("timeStamp").getAsLong();
    	int nonce = jo.get("nonce").getAsInt();
    	
    	Block output = new Block(hash,previousHash,data,timeStamp,nonce);

    	
    	return output;
	}
	
	
	//Calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				data 
				);
		return calculatedhash;
	}
	
	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
}