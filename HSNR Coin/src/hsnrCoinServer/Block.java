package hsnrCoinServer;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;

public class Block {
    
	public String hash;
	public String previousHash;
    //public String merkleRoot; //Block beinhaltet merkleroot, bedeutet fusioniertes hash von allen hashes von transaktionen
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
   
	
	private long timeStamp;
	static int nonce;
	String testHash;

    Block(String previousHash)
    {

    	//here brauchen wir eine Parsemethode, die durch alle Transactions im Block durchgeht
       /* this.from = from;
        this.to = to;
        this.amount = amount;
        data = String.format("From: %s\nTo: %s\nAmount: %s\n", from, to, amount);*/
        this.previousHash = previousHash;
        
        this.timeStamp = new Date().getTime();
        hash =  calculateHash();

    }

    public Block() {
		// TODO Auto-generated constructor stub
	}

	public String calculateHash() {
    	//diese Methode nimmt die u. g. Variablen und verschlüssselt sie durch SHA-256 in ein hash für ein Block
    		String calculatedHash = StringUtil.applySha256(
    			previousHash +
    			Long.toString(timeStamp)+
    			Integer.toString(nonce));
    		testHash = calculatedHash;
    		return calculatedHash;
    	}
    
    public void mineBlock(int difficulty) {
		//merkleRoot = StringUtil.getMerkleRoot(transactions);
		String target = new String(new char[difficulty]).replace('\0', '0'); //kreiert eine substring in der Länge von  difficulty und tauscht alle Zeichen durch 0
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
	
		}
		System.out.println("block mined: " + hash);
	}
    
    public boolean addTransaction(Transaction transaction) {
		//genesis block werden ignoriert
		if(transaction == null) return false;		
		if((previousHash != "0")) {//falls es sich um das 2 block handelt, wird automatisch addiert
			if((transaction.processTransaction() != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}
	
}

    //Muss in die Klasse Transaction rein
   /* public void setAmount(int amount)
    {

        this.amount = amount;

    }

    public void setFrom(String from)
    {

        this.from = from;

    }

    public void setTo(String to)
    {

        this.to = to;

    }

    public void setCurrentHash(String currentHash)
    {

        this.hash = currentHash;

    }

    public void setPreviousHash(String previousHash)
    {

        this.previousHash = previousHash;

    }*/

    /*public void print()
    {

        System.out.println(String.format("from:%s,to:%s,amount:%s,previousHash:%s,currentHash:%s", from, to, amount, previousHash, hash));

    }

}
*/