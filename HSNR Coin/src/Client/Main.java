package Client;

import java.security.Key;
import java.security.Security;

import com.google.gson.GsonBuilder;


public class Main {

	public static int difficulty = 5;
	public static Wallet walletA;
	public static Wallet walletB;
	public static Transaction genesisTransaction;

	public static void main(String[] args) {

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(Blockchain.blockchain);
		System.out.println(blockchainJson);
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		

		// Create the new wallets
		walletA = new Wallet();
		walletB = new Wallet();
		Wallet coinbase = new Wallet();
		ConnectionClass.makeConnection();
				

		/*
		 * blockchain.add(new Block("Hi im the first block", "0")); blockchain.add(new
		 * Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash));
		 * blockchain.add(new
		 * Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
		 */

		/*
		 * System.out.println(Block.nonce);
		 * 
		 * //die Methode Minblock liegt in der Klasse Block blockchain.add(new
		 * Block("Hi im the first block", "0"));
		 * System.out.println("Trying to Mine block 1... ");
		 * blockchain.get(0).mineBlock(difficulty);
		 * 
		 * System.out.println(Block.nonce);
		 * 
		 * blockchain.add(new
		 * Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash));
		 * System.out.println("Trying to Mine block 2... ");
		 * blockchain.get(1).mineBlock(difficulty);
		 * 
		 * System.out.println(Block.nonce);
		 * 
		 * 
		 * blockchain.add(new
		 * Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
		 * System.out.println("Trying to Mine block 3... ");
		 * blockchain.get(2).mineBlock(difficulty);
		 * 
		 * System.out.println(Block.nonce); System.out.println("\nBlockchain is Valid: "
		 * + isChainValid());
		 */

		/*// Test public and private keys
		System.out.println("Private and public keys:");
		System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		System.out.println(StringUtil.getStringFromKey(walletA.publicKey));
		// Create a test transaction from WalletA to walletB
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		// Verify the signature works and verify it from the public key
		System.out.println("Is signature verified");
		System.out.println(transaction.verifiySignature() + "\nn");*/

		genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 100f, null);
		genesisTransaction.generateSignature(coinbase.privateKey); // manually sign the genesis transaction
		genesisTransaction.transactionId = "0"; // manually set the transaction id
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value,
				genesisTransaction.transactionId)); // manually add the Transactions Output
		Blockchain.UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); // its important to store
																							// our first transaction in
																							// the UTXOs list.

		System.out.println("Creating and Mining Genesis block... ");
		Block genesis = new Block("0");
		genesis.addTransaction(genesisTransaction);
		Blockchain.addBlock(genesis);

		// testing
		Block block1 = new Block(genesis.hash);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
		block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f));
		Blockchain.addBlock(block1);
		blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(Blockchain.blockchain);
		Blockchain.isChainValid();
		System.out.println(blockchainJson);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Block block2 = new Block(block1.hash);
		System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
		block2.addTransaction(walletA.sendFunds(walletB.publicKey, 1000f));
		Blockchain.addBlock(block2);
		// blockchainJson = new
		// GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		Blockchain.isChainValid();
		// System.out.println(blockchainJson);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Block block3 = new Block(block2.hash);
		System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
		block3.addTransaction(walletB.sendFunds(walletA.publicKey, 20));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Blockchain.isChainValid();
		// blockchainJson = new
		// GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		// System.out.println(blockchainJson);

	}

}
