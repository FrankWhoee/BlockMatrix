package net.vikingsdev.blockmatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import java.security.*;
import java.security.interfaces.RSAPublicKey;
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
	
	private static final String privateKeyString = 
			"MIICWgIBAAKBgGpYA60Dbe1MGCYDZo3q9KccjX4X9N1TGJjkE5uDGs8tBoK4ntZv" + 
			"rwfIerBZdx6eaARSOySxlaQTuukKgj/ZCNI3otwL9KVKOqRceRRHgl/49YPdMonR" + 
			"zHippGaEx5HMMT1ITK3Bx1f5qY4ogsXk5qm6U490YYvPgbaVsBtos/azAgMBAAEC" + 
			"gYA/hTcc3xGITo2WFy3o01EziICTsueWVA47NPDpURRwb6qV5oUp/SgFdCCkuavH" + 
			"ZEMpYZzmPBTwHsDkdlx6mr8DWW1faIWpyQZCMJz8YtKx1VCTJMMTQ757WhGD18FY" + 
			"DKiFVDRT2ZZYEah9aLYPYa6AYgYIPCtvzd5QDddnMf6ioQJBAMfdEUJpWRzGTr//" + 
			"mw6xCz4Hoev1VBj8ljnQ0nk82UY6SQYbZz6+TDS8m+R8ks9V+y/cqRyOD1NNpT2i" + 
			"dUM1i6kCQQCINoec7IAfsd1Db7kTeCpd0MCK08PL3sLzlMJgZGm4idIusY5otJ8o" + 
			"wXogAeiw8oOcgtblKZz9224j4j7MWMj7AkAG0/+l9DFuMTw5hQMIInZO3TXj+NKx" + 
			"s9dyDDdUmwaVRqJ+CeuiEiBKYPM2gCcH3FkjjndcmWHep7VwgJ9e93JZAkBp8N9w" + 
			"8ZCFFjVdadushN2OsfnO//1c5xkBkkXL6s0/NhI/NuHoFfNkI3b0xgdQ+I3cgPba" + 
			"rY7o8m2rgyAMl1FZAkBTW7afGlSF5jeUmfW21eWxTl+dlDyYhJi6RuIRhy1/ZrLM" + 
			"rx/aT0O8FQYTo1xiKgM2cNrVvoeY03WKbpgurODp";
	private static PrivateKey privateKey;
	private static final String publicKeyString =  
			"MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgGpYA60Dbe1MGCYDZo3q9KccjX4X" + 
			"9N1TGJjkE5uDGs8tBoK4ntZvrwfIerBZdx6eaARSOySxlaQTuukKgj/ZCNI3otwL" + 
			"9KVKOqRceRRHgl/49YPdMonRzHippGaEx5HMMT1ITK3Bx1f5qY4ogsXk5qm6U490" + 
			"YYvPgbaVsBtos/azAgMBAAE=";
	private static PublicKey publicKey;
	static {
	    try 
	    {
	    	java.security.Security.addProvider(
	    	         new org.bouncycastle.jce.provider.BouncyCastleProvider()
	    	);
	    	
	        KeyFactory kf = KeyFactory.getInstance("RSA");

	        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString));
	        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

	        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString));
	        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

	        publicKey = pubKey;
	        privateKey = privKey;
	        
	        System.out.println(privKey);
	        System.out.println(pubKey);

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
	
	public static byte[][] splitBytes(final byte[] data, final int chunkSize)
	{
	  final int length = data.length;
	  final byte[][] dest = new byte[(length + chunkSize - 1)/chunkSize][];
	  int destIndex = 0;
	  int stopIndex = 0;

	  for (int startIndex = 0; startIndex + chunkSize <= length; startIndex += chunkSize)
	  {
	    stopIndex += chunkSize;
	    dest[destIndex++] = Arrays.copyOfRange(data, startIndex, stopIndex);
	  }

	  if (stopIndex < length)
	    dest[destIndex] = Arrays.copyOfRange(data, stopIndex, length);

	  return dest;
	}
	
	public static void save() {
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(playerchain);
		byte[][] encrypted = null;
		try {
			System.out.println(publicKey.getEncoded());
			encrypted = splitBytes(blockchainJson.getBytes(),75);
			for(int i = 0; i < encrypted.length; i++){
				encrypted[i] = CryptoUtil.encrypt(publicKey, encrypted[i]);
			}
			//encrypted = 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File Old = new File("../Save/save.json");
		Old.delete();
		File New = new File("../Save/save.json");
		try {
			PrintWriter out = new PrintWriter(New);
			for(int c = 0; c < encrypted.length; c++) {
				for(int r = 0; r < encrypted[c].length;r++) {
					out.print(encrypted[c][r]);
				}
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error occured while writing to " + New.getPath() + ". File not found.");
		}
	}
}
