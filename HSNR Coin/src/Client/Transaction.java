package Client;

import java.security.*;
import java.util.*;

public class Transaction {

	public String transactionId; // hier wird hash als ID benutzt. Siehe unten
	public PublicKey sender;
	public PublicKey reciepient;
	public float value;
	public byte[] signature;

	
	private static int sequence = 0;

	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>(); // die Transaktionen die wir bekommen
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>(); // Die Transaktionen die wir
																						// schicken

	public Transaction(PublicKey sender, PublicKey reciepient, float value, ArrayList<TransactionInput> inputs) {
		super();
		this.sender = sender;
		this.reciepient = reciepient;
		this.value = value;
		this.inputs = inputs;
		this.transactionId = calculateHash();
	}

	private String calculateHash() {//kalkuliert mit Hilfe von StringUtils das Hash von Transaction
		// nimmt die Methode von StringUtil
		sequence++; // wir addieren dazu die Reihenfolge, damit 2 Transaktionen keine identische
					// Hasshes haben
		String calculatedHash = StringUtil
				.applySha256(StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient) +
		// nimmt die Methode von StringUtil
						Float.toString(value) + sequence);
		return calculatedHash;
	}

	// Diese Methode nimmt das privatekey und die Nachricht von der Transaktion und
	// Verslüsselt sie mit ECDSA
	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value);
		signature = StringUtil.applyECDSASig(privateKey, data);
	}

	// diese Methode nimmt die Nachricht und überprüft die validität mit Hilfe von
	// der Public key
	public boolean verifiySignature() {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value);
		return StringUtil.verifyECDSASig(sender, data, signature);
	}

	public boolean processTransaction() {// hier wird alles überprüft, bevopr die Transaktion erstellt wird

		if (verifiySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}

		// sammelt alle unbenutzte TRansaktionen
		for (TransactionInput i : inputs) {
			i.UTXO = Blockchain.UTXOs.get(i.transactionOutputId);
		}

		// Überprüft, ob die Transaktion in Ordnung ist
		if (getInputsValue() < Blockchain.minimumTransaction) {
			System.out.println("#Transaction Inputs to small: " + getInputsValue());
			return false;
		}

		// kalkuliert den Betrag, der zurückgeschickt wird; Change
		float leftOver = getInputsValue() - value;
		transactionId = calculateHash();
		outputs.add(new TransactionOutput(this.reciepient, value, transactionId)); // schickt Betrag an Empfänger
		outputs.add(new TransactionOutput(this.sender, leftOver, transactionId)); // schickt den Rest zurück

		// fügt outputs zum UTXO
		for (TransactionOutput o : outputs) {
			Blockchain.UTXOs.put(o.id, o);
		}

		// löscht input vaus der UTXO
		for (TransactionInput i : inputs) {
			if (i.UTXO == null)
				continue; // wenn nicht gefunden, skip
			Blockchain.UTXOs.remove(i.UTXO.id);
		}

		return true;
	}

	// UTXO liegt in Main
	// returns sum of inputs(UTXOs) values
	public float getInputsValue() {
		float total = 0;
		for (TransactionInput i : inputs) {
			if (i.UTXO == null)
				continue; // if Transaction can't be found skip it
			total += i.UTXO.value;
		}
		return total;
	}

	// returns sum of outputs:
	public float getOutputsValue() {
		float total = 0;
		for (TransactionOutput o : outputs) {
			total += o.value;
		}
		return total;
	}
}
