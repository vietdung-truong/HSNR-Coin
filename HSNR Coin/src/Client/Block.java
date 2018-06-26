package Client;

import java.util.ArrayList;
import java.util.Date;

public class Block {

	public String hash;
	public String previousHash;
	public String merkleRoot; // Block beinhaltet merkleroot, bedeutet fusioniertes hash von allen hashes von
								// transaktionen
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	public long timeStamp;
	static int nonce;
	public String testHash;

	public Block(String previousHash) {
		super();
		this.previousHash = previousHash;

		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); // siehe unten. Durch diese Methode wird das Hash für den Block kalkuliert
	}

	public String calculateHash() {
		// diese Methode nimmt die u. g. Variablen und verschlüssselt sie durch SHA-256
		// in ein hash für ein Block
		String calculatedHash = StringUtil
				.applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + merkleRoot);
		testHash = calculatedHash;
		return calculatedHash;
	}

	public void mineBlock(int difficulty) {
		merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = new String(new char[difficulty]).replace('\0', '0'); // kreiert eine substring in der Länge von
																				// difficulty und tauscht alle Zeichen
																				// durch 0
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();

		}
		System.out.println("block mined: " + hash);
	}

	// überprüft und addiert Transaktionen ins Block
	public boolean addTransaction(Transaction transaction) {
		// genesis block werden ignoriert
		if (transaction == null)
			return false;
		if ((previousHash != "0")) {// falls es sich um das 2 block handelt, wird automatisch addiert
			if ((transaction.processTransaction() != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}

}
