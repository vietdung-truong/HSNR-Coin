package hsnrCoinServer;

import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.Callable;

import com.google.gson.GsonBuilder;


public class Blockchain{

	public ArrayList<Block> blockchain;
	public static HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //listet alle noch nicht benutzte transaktionen
	public static int difficulty = 5;
    Callable<Void> observer;
    BlockchainDelegate delegate;
    public static Transaction genesisTransaction;
    
    String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
    
    public static float minimumTransaction = 0.1f;

    Blockchain()
    {

        Block genesis = new Block("0");
        blockchain = new ArrayList<Block>();
        blockchain.add(genesis);
        delegate = new BlockchainDelegate() {
            @Override
            public void didChange() {



            }
        };


    }

   /* public void modify(int i, String from, String to, int amount)
    {

        Block changed = blockchain.get(i);
        changed.from = from;
        changed.to = to;
        changed.amount = amount;
        changed.data = String.format("From: %s\nTo: %s\nAmount: %s\n", from, to, amount);
        rehash(i);

    }*/

   /* public void rehash(int i)
    {
        if (i > 0)
        {

            for (int j = i; j < blockchain.size(); j++)
            {

                Block previous = get(j-1);
                Block current = get(j);
                String data = String.format("From: %s\nTo: %s\nAmount: %s\n", current.from, current.to, current.amount);
                current.setCurrentHash(hash(data+previous.currentHash));

            }

        }

    }*/

    public Block get(int index)
    {

        return blockchain.get(index);

    }

    public String getTailHash()
    {

        return blockchain.get(blockchain.size()-1).hash;

    }

    void addBlock(Block newBlock) {
		// TODO Auto-generated method stub
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
    }	
    
    public void append(String from, String to, int amount)
    {

        Block last = new Block(blockchain.get(blockchain.size()-1).hash);
        blockchain.add(last);
        didChange();

    }

    public Block getLast()
    {

        return blockchain.get(blockchain.size()-1);

    }

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

			//taken from StringUtil
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

    public void print()
    {
        int i = 0;
        for (Block b : blockchain)
        {

        	//Hier müssen alle Transaktionen in Block ausgegeben werden
            //System.out.println(String.format("%s--------------\nFrom: %s\nTo: %s\nAmount: %s\nPrevious hash: %s\nCurrent hash: %s\n--------------\n", i, b.from, b.to, b.amount, b.previousHash, b.currentHash));
            i++;

        }

    }

    void didChange()
    {

        delegate.didChange();

    }
    
    public Boolean isChainValid() {//hier werden die Werte von hashes und previoushashes mit den 'calculate' 
		//Werten verglichen. Falls die Werte nicht stimmen, liefert der Vergleich eine negative Antwort
		Block currentBlock;
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		HashMap<String,TransactionOutput> tempUTXOs = new HashMap<String,TransactionOutput>(); //a temporary working list of unspent transactions at a given block state.
		tempUTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0));
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.testHash) ){
				System.out.println(i+": Current Hashes not equal");			
				return false;
			}
			
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("#This block hasn't been mined");
				return false;
			}
			
			//loop thru blockchains transactions:
			TransactionOutput tempOutput;
			for(int t=0; t <currentBlock.transactions.size(); t++) {
				Transaction currentTransaction = currentBlock.transactions.get(t);
				
				if(!currentTransaction.verifiySignature()) {
					System.out.println("#Signature on Transaction(" + t + ") is Invalid");
					return false; 
				}
				if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
					System.out.println("#Inputs are note equal to outputs on Transaction(" + t + ")");
					return false; 
				}
				
				for(TransactionInput input: currentTransaction.inputs) {	
					tempOutput = tempUTXOs.get(input.transactionOutputId);
					
					if(tempOutput == null) {
						System.out.println("#Referenced input on Transaction(" + t + ") is Missing");
						return false;
					}
					
					if(input.UTXO.value != tempOutput.value) {
						System.out.println("#Referenced input Transaction(" + t + ") value is Invalid");
						return false;
					}
					
					tempUTXOs.remove(input.transactionOutputId);
				}
				
				for(TransactionOutput output: currentTransaction.outputs) {
					tempUTXOs.put(output.id, output);
				}
				
				if( currentTransaction.outputs.get(0).recipient != currentTransaction.reciepient) {
					System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
					return false;
				}
				if( currentTransaction.outputs.get(1).recipient != currentTransaction.sender) {
					System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
					return false;
				}
				
			}
			
		}
		System.out.println("Blockchain is valid");
		return true;
	}

}



