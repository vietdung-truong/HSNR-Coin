import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;

public class StringUtil {

	//hier wird Algorithmus SHA256 benutzt, um ein String in Hash zu verwandeln
	public static String applySha256(String input) {
		try {
			MessageDigest message = MessageDigest.getInstance("SHA-256");
			byte[] hash = message.digest(input.getBytes("UTF-8"));
			// es wurde ein String 'input' eingegeben, dieser input wurde in Bytes verwandelt und Durch Sha256 in 'hash' verschlüsselt
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				//0xff ist gleich 256
				if (hex.length() == 1) {
					hexString.append('0');
				} else {
					hexString.append(hex);
				}
			}
			return hexString.toString();

			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	public static String getStringFromKey(Key key) {
		//wird zu Verschlüsselung von TransactionID benutzt
		// TODO Auto-generated method stub
		//Entkodierung durch Base 64
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}
	
	public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
		//verwendet Private key dafür, um ein Signature zu erstellen
		Signature dsa;
		byte[] output = new byte[0];
		try {
			dsa = Signature.getInstance("ECDSA", "BC");
			dsa.initSign(privateKey);
			byte[] strByte = input.getBytes();
			dsa.update(strByte);
			byte[] realSig = dsa.sign();
			output = realSig;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return output;
	}
	public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
		try {
			//verwendet public key dafür, um die Validität von Hash zu prüfen
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(data.getBytes());
			return ecdsaVerify.verify(signature);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//diese Methode kreiert merkleroot. Solange es mehr als 1 Transaktion gibt, wird ein weiteres tree level erstellt und die hashes von den TRansaktionen durch SHA verschlüsselt und fusioniert
	public static String getMerkleRoot(ArrayList<Transaction> transactions) {
			int count = transactions.size();
			ArrayList<String> previousTreeLayer = new ArrayList<String>();
			for(Transaction transaction : transactions) {
				previousTreeLayer.add(transaction.transactionId);
			}
			ArrayList<String> treeLayer = previousTreeLayer;
			while(count > 1) {
				treeLayer = new ArrayList<String>();
				for(int i=1; i < previousTreeLayer.size(); i++) {
					treeLayer.add(applySha256(previousTreeLayer.get(i-1) + previousTreeLayer.get(i)));
				}
				count = treeLayer.size();
				previousTreeLayer = treeLayer;
			}
			String merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
			return merkleRoot;
		}
}
