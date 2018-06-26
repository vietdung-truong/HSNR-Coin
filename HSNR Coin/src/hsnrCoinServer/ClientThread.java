package hsnrCoinServer;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread extends Thread {

	public Socket clientSocket = null;
	public BufferedReader in = null;
	public PrintWriter out = null;
	public int modified = 0;
	public referenceInt c;

	public static int count = 0;
	public static int handshake = 0;

	public ClientThread(Socket clientSocket) {
		super("serverThread");
		this.clientSocket = clientSocket;
		count++;
		System.out.println("Clients" + count);
		c = new referenceInt(0);
	}

	@Override
	public void run() {

		makeConnection(clientSocket);
	}

	private void makeConnection(Socket clientSocket) {
		// TODO Auto-generated method stub
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream());
			String input = "";
			blockchainServer.outputList.add(out);
			blockchainServer.inputList.add(in);

			while (clientSocket.isConnected() == true)
				;
			{

				while ((input = in.readLine()) != null) {
					/*
					 * System.out.println("Client "+blockchainServer.inputList.indexOf(in)+": "
					 * +input);
					 * 
					 * if (fixedCount != 0 || input == "fixed") {
					 * 
					 * fixedCount = fixedCount + 1; if (fixedCount == count) {
					 * 
					 * handshake = 1;
					 * 
					 * } break;
					 */

					if (input.equals("connection-request"))// Woher kommt inout?
					{

						out.println("Acknowledged");
						System.out.println("Server: Client Connected");
						break;
					} else if (input.equals("catch-up")) {

						String jsonResponse = "{\"data\":[";// wo wird "data" gezeigt?
						for (int i = 1; i < blockchainServer.blockchainobject.blockchain.size(); i++) {

							Block b = blockchainServer.blockchainobject.blockchain.get(i);
							ArrayList<Transaction> tempTransactionArray = b.transactions;
							// methose zum Auflisten von allen Transaktionen in einem Block
							if (i != blockchainServer.blockchainobject.blockchain.size() - 1)

							{

							
								for (Transaction i1 : tempTransactionArray) {

									jsonResponse += String.format("{\"from\":\"%s\",\"to\":\"%s\",\"amount\":\"%s\"},",
											i1.sender, i1.reciepient, i1.value);

								}
							} else {

								int counter = 0;
								while (counter < tempTransactionArray.size()-1) {
									Transaction i1 = tempTransactionArray.get(counter);
									jsonResponse += String.format("{\"from\":\"%s\",\"to\":\"%s\",\"amount\":\"%s\"},",
											i1.sender, i1.reciepient, i1.value);
									counter++;
								}
								Transaction i1 = tempTransactionArray.get(counter);
								jsonResponse += String.format("{\"from\":\"%s\",\"to\":\"%s\",\"amount\":\"%s\"}",
										i1.sender, i1.reciepient, i1.value);
							}

						}
						jsonResponse += "]}";
						out.println("Acknowledged");
						out.println(jsonResponse);
						break;

					} else if (input.equals("exit")) {

						out.println("Server: exit");// in Console
						int i = blockchainServer.outputList.indexOf(out);
						blockchainServer.outputList.remove(i);
						clientSocket.close();
						break;

					} else if (input.startsWith("{from:")) {
						
						

						String parse = new String(input);
						JSONObject jsonObject = new JSONObject(parse);
						blockchainServer.tailHash = jsonObject.get("tailHash").toString();
						String from = jsonObject.getString("from");
						String to = jsonObject.getString("to");
						float amount = jsonObject.getInt("amount");// Console?
						//blockchainServer.blockchainobject.append(from, to, amount);// needs improvement. jsonObject. Teilweise gelöst durch die nächste Zeile #125
						Transaction tempTransaction = new Transaction (from, to, amount);
						Block newBlock = new Block();//wir müssen noch das hash von letzten Block referenzieren. NOCH IN TESTING
						newBlock.addTransaction(tempTransaction);
						addBlock(newBlock);
						// hier muss ich aufpassen. Allign the Transactions!
						for (PrintWriter o : blockchainServer.outputList) {

							if (o != out) {

								// broadcast to all the other devices on the network
								o.println(input);

							}

						}
						break;

					} else if (input.startsWith("{tailHash:")) {

						String parse = new String(input);
						JSONObject jsonObject = new JSONObject(parse);
						if (blockchainServer.blockchainobject.blockchain.size() > 1) {

							if (blockchainServer.tailHash.equals(jsonObject.get("tailHash").toString())) {

								out.println("Your blockchain is OK");

							} else {

								for (PrintWriter o : blockchainServer.outputList) {

									o.println(String.format("Server: Client %s has modified his blockchain",
											blockchainServer.outputList.indexOf(out)));

								}

							}

						} else {

							modified = 0;
							out.println("Server: All nodes agree");

						}
						break;
					} else if (input.equals("fix")) {

						for (PrintWriter o : blockchainServer.outputList) {

							o.println("Fixing blockchain, please wait");

						}

						// int currentCount = count;
						// fixedCount = 0;

						String jsonResponse = "{\"fix\":[";
						for (int i = 0; i < blockchainServer.blockchainobject.blockchain.size(); i++) {

							Block b = blockchainServer.blockchainobject.blockchain.get(i);
							if (i != blockchainServer.blockchainobject.blockchain.size() - 1)
								// wir brauchen eine Methode zum ausschreiben aller Transaktionen in einem Block
								/*
								 * {
								 * 
								 * jsonResponse += String.format(
								 * "{\"from\":\"%s\",\"to\":\"%s\",\"amount\":\"%s\",\"previousHash\":\"%s\",\"currentHash\":\"%s\"},",
								 * b.from, b.to, b.amount, b.previousHash, b.currentHash);
								 * 
								 * } else {
								 * 
								 * jsonResponse += String.format(
								 * "{\"from\":\"%s\",\"to\":\"%s\",\"amount\":\"%s\",\"previousHash\":\"%s\",\"currentHash\":\"%s\"}",
								 * b.from, b.to, b.amount, b.previousHash, b.currentHash);
								 * 
								 * }
								 */

								/* } */
								jsonResponse += "]}";

							out.println(jsonResponse);
							blockchainServer.fixedCount++;

							for (PrintWriter o : blockchainServer.outputList) {

								if (o != out) {

									o.println(jsonResponse);

								}

							}

							/*
							 * while (fixedCount < count) {
							 * 
							 * Thread.sleep(1);
							 * 
							 * }
							 * 
							 * System.out.println("All clients fixed"); for (PrintWriter o : outputList) {
							 * 
							 * o.println("fixed");
							 * 
							 * } //fixedCount = 0; handshake = 0; break;
							 */
						}

					}

				}
				count--;
				System.out.println(
						String.format("Server: Client %s disconnected", blockchainServer.outputList.indexOf(out)));
				clientSocket.close();

			}
		} catch (Exception e) {

			int i = blockchainServer.outputList.indexOf(out);
			if (i != -1) {

				blockchainServer.outputList.remove(i);
				blockchainServer.inputList.remove(i);

			}
			count--;
			System.out.println(count);
		}

	}
	
	static void addBlock(Block newBlock) {
		int difficulty = 5; //erstmal test
		// TODO Auto-generated method stub
		newBlock.mineBlock(difficulty );
		blockchainServer.blockchainobject.blockchain.add(newBlock); // finde das Blockchain von dem Server Guy
	}

}
