package net.vikingsdev.blockmatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.bouncycastle.*;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemWriter;

import net.vikingsdev.blockmatrix.gameobjects.Player;
import net.vikingsdev.blockmatrix.utils.CryptoUtil;
import net.vikingsdev.blockmatrix.utils.OSUtil;
import net.vikingsdev.blockmatrix.utils.StringUtil;

public class Blockchain {
	public static ArrayList<Block> playerchain = new ArrayList<Block>();
	public static int difficulty = 5;
	private static JsonObject localSave;
	
	private static final String privateKeyString = "-----BEGIN RSA PRIVATE KEY-----\n" + 
			"MIICWgIBAAKBgGpYA60Dbe1MGCYDZo3q9KccjX4X9N1TGJjkE5uDGs8tBoK4ntZv\n" + 
			"rwfIerBZdx6eaARSOySxlaQTuukKgj/ZCNI3otwL9KVKOqRceRRHgl/49YPdMonR\n" + 
			"zHippGaEx5HMMT1ITK3Bx1f5qY4ogsXk5qm6U490YYvPgbaVsBtos/azAgMBAAEC\n" + 
			"gYA/hTcc3xGITo2WFy3o01EziICTsueWVA47NPDpURRwb6qV5oUp/SgFdCCkuavH\n" + 
			"ZEMpYZzmPBTwHsDkdlx6mr8DWW1faIWpyQZCMJz8YtKx1VCTJMMTQ757WhGD18FY\n" + 
			"DKiFVDRT2ZZYEah9aLYPYa6AYgYIPCtvzd5QDddnMf6ioQJBAMfdEUJpWRzGTr//\n" + 
			"mw6xCz4Hoev1VBj8ljnQ0nk82UY6SQYbZz6+TDS8m+R8ks9V+y/cqRyOD1NNpT2i\n" + 
			"dUM1i6kCQQCINoec7IAfsd1Db7kTeCpd0MCK08PL3sLzlMJgZGm4idIusY5otJ8o\n" + 
			"wXogAeiw8oOcgtblKZz9224j4j7MWMj7AkAG0/+l9DFuMTw5hQMIInZO3TXj+NKx\n" + 
			"s9dyDDdUmwaVRqJ+CeuiEiBKYPM2gCcH3FkjjndcmWHep7VwgJ9e93JZAkBp8N9w\n" + 
			"8ZCFFjVdadushN2OsfnO//1c5xkBkkXL6s0/NhI/NuHoFfNkI3b0xgdQ+I3cgPba\n" + 
			"rY7o8m2rgyAMl1FZAkBTW7afGlSF5jeUmfW21eWxTl+dlDyYhJi6RuIRhy1/ZrLM\n" + 
			"rx/aT0O8FQYTo1xiKgM2cNrVvoeY03WKbpgurODp\n" + 
			"-----END RSA PRIVATE KEY-----";
	private static PrivateKey privateKey;
	private static final String publicKeyString = "-----BEGIN PUBLIC KEY-----\n" + 
			"MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgGpYA60Dbe1MGCYDZo3q9KccjX4X\n" + 
			"9N1TGJjkE5uDGs8tBoK4ntZvrwfIerBZdx6eaARSOySxlaQTuukKgj/ZCNI3otwL\n" + 
			"9KVKOqRceRRHgl/49YPdMonRzHippGaEx5HMMT1ITK3Bx1f5qY4ogsXk5qm6U490\n" + 
			"YYvPgbaVsBtos/azAgMBAAE=\n" + 
			"-----END PUBLIC KEY-----";
	private static PublicKey publicKey;
	static {
	    KeyFactory kf;
	    try {
	        kf = KeyFactory.getInstance("RSA");
	        byte[] encodedPv = Base64.getDecoder().decode(privateKeyString);
	        PKCS8EncodedKeySpec keySpecPv = new PKCS8EncodedKeySpec(encodedPv);
	        privateKey = kf.generatePrivate(keySpecPv);

	        System.out.println(publicKeyString);
	        byte[] encodedPb = Base64.getDecoder().decode(publicKeyString);
	        System.out.println(encodedPb);
	        X509EncodedKeySpec keySpecPb = new X509EncodedKeySpec(encodedPb);
	        publicKey = kf.generatePublic(keySpecPb);

	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void register(String name) {
		Player newPlayer = new Player(name);
		playerchain.add(new Block(newPlayer.toJsonString(), playerchain.get(playerchain.size()-1).hash));
		playerchain.get(playerchain.size()-1).mineBlock(difficulty);

	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		
		//loop through playerchain to check hashes:
		for(int i=1; i < playerchain.size(); i++) {
			currentBlock = playerchain.get(i);
			previousBlock = playerchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}
	
	public static boolean parseLocalJson() {
		JsonParser parser = new JsonParser();
        //Read and parse JSON file
		try {
			String encrypted = StringUtil.readFile("../Save/save.json", StandardCharsets.UTF_8);
			byte[] decrypted = CryptoUtil.decrypt(privateKey, encrypted.getBytes());
			String decoded = new String(decrypted,StandardCharsets.UTF_8);
			JsonElement obj = parser.parse(decoded);
			localSave = obj.getAsJsonObject();
			
			Gson gson = new Gson();
			playerchain = gson.fromJson(decoded, new TypeToken<ArrayList<Block>>(){}.getType());
			
			return true;
			
			
		} catch (Exception e) {
			return false;
		}
        
	}

	
	public static void save() {
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(playerchain);
		byte[] encrypted = null;
		try {
			System.out.println(publicKey.getEncoded());
			 encrypted = CryptoUtil.encrypt(publicKey, blockchainJson.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File Old = new File("../Save/save.json");
		Old.delete();
		File New = new File("../Save/save.json");
		try {
			PrintWriter out = new PrintWriter(New);
			out.println(encrypted);
			out.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error occured while writing to " + New.getPath() + ". File not found.");
		}
	}
}
