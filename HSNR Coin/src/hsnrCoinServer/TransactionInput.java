package hsnrCoinServer;

public class TransactionInput {

	public String transactionOutputId; //referenziert if transationIDs
	public TransactionOutput UTXO; //dass sind unbenutzte Inputs, die dir zur Verf�gung stehen
	
	public TransactionInput(String transactionOutputId) {
	this.transactionOutputId = transactionOutputId;
	}
}