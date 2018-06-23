import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.*;

public class Wallet {

	public PrivateKey privateKey;
	public PublicKey publicKey;
	
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public HashMap<String, TransactionOutput> getUTXOs() {
		return UTXOs;
	}

	public void setUTXOs(HashMap<String, TransactionOutput> uTXOs) {
		UTXOs = uTXOs;
	}

	
	
	public HashMap<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>(); //filtert Einträge aus der UTXO von Main und speichert nur unsere UTXO
	
	public Wallet() {//Konstruktor
		super();
		generateKeyPair();
	}
	
	public void generateKeyPair() {
		try{
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			//bereitet die Instances für Keygenerating vor
			keyGen.initialize(ecSpec, random);
			KeyPair keyPair = keyGen.generateKeyPair();
			privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		
	}
	
	public float getBalance() {
		float total = 0;	
        for (Map.Entry<String, TransactionOutput> item: Main.UTXOs.entrySet()){ //für jeden Eintrag aus Main. UTXO
        	TransactionOutput UTXO = item.getValue();
            if(UTXO.isMine(publicKey)) { //if output belongs to me ( if coins belong to me )
            	UTXOs.put(UTXO.id,UTXO); //add it to Wallet's UTXO Hashmap
            	total += UTXO.value ; 
            }
        }
		return total;
	}
	
	//Überprüft die Angaben und danach Generiert eine Transaktion von diesem Wallet
	public Transaction sendFunds(PublicKey _recipient,float value ) {
		if(getBalance() < value) { //gather balance and check funds.
			System.out.println("#Not Enough funds to send transaction. Transaction Discarded.");
			return null;
		}
    //generiert input von deinen UTXO Liste. Sobald der Betrag erreicht wird, break
		ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    
		float total = 0;
		for (Map.Entry<String, TransactionOutput> item: UTXOs.entrySet()){
			TransactionOutput UTXO = item.getValue();
			total += UTXO.value;
			inputs.add(new TransactionInput(UTXO.id));
			if(total > value) break;
			//break hier
		}
		
		Transaction newTransaction = new Transaction(publicKey, _recipient , value, inputs); //publicKey ist von diesem Wallet. Es wird eine Transaktion generiert
		newTransaction.generateSignature(privateKey); //generiert in der Transactionklasse ein Signature
		
		for(TransactionInput input: inputs){
			UTXOs.remove(input.transactionOutputId);// nimmt alle in dieser Transaktion benutzte Einträge aus dem UTXO
		}
		return newTransaction;
	}
}
