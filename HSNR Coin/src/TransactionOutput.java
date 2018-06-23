import java.security.*;

public class TransactionOutput {

	public String id;
	public PublicKey recipient; //wem werden die coins gehören
	public float value;
	public String parentTransactionId; //das ID der TRansaktion, von welcher die folgende Transaktion kommt
	
	
	public TransactionOutput(PublicKey recipient, float value, String parentTransactionId) {
		super();
		this.id = StringUtil.applySha256(StringUtil.getStringFromKey(recipient)+Float.toString(value)+parentTransactionId);
		this.recipient = recipient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
	}
	
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == recipient);
	}
}
