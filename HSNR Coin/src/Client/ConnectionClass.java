package Client;

import java.io.*;
import java.net.*;

import org.json.*;

public class ConnectionClass {

	static Socket socket;
	static BufferedReader in;
	static PrintWriter out;

	static void makeConnection() {

		try {
			socket = new Socket("localhost", 1234);// danach statt local host eine IP Adresse
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);

			out.println("requesting-connection");
			catchup();
			receiveInput();
		} catch (Exception e) {
			System.out.println("connection failed");
			return;
		}
	}

	/*
	 * static void receiveInput() {
	 * 
	 * try { String input; in = new BufferedReader(new
	 * InputStreamReader(socket.getInputStream())); while((input = in.readLine()) ==
	 * null);//wait command System.out.println(input); // put some .setText here }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } // TODO Auto-generated method stub
	 * 
	 * }
	 */

	static void catchup() {
		try {
		out.println("catch-up");
		String blockchainString = "";
		while((blockchainString = in.readLine()) == null); //wait for reply from server
        while((blockchainString = in.readLine()) == null); //wait for reply from server
        JSONObject jsonObject = new JSONObject(blockchainString);
        JSONArray data = jsonObject.getJSONArray("data");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
