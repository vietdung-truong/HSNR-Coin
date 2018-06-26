package hsnrCoinServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class blockchainServer {

	public static ArrayList<PrintWriter> outputList;
	public static ArrayList<BufferedReader> inputList;
	public static String tailHash;
	public static Blockchain blockchainobject;
	public static int fixedCount = 0;

	public static void main(String[] args) {

		outputList = new ArrayList<>();
		inputList = new ArrayList<>();
		tailHash = "";
		blockchainobject = new Blockchain();
		acceptAndConnect();
	}

	private static void acceptAndConnect() {
		// TODO Auto-generated method stub
		try (ServerSocket serverSocket = new ServerSocket(1234)) {
			Runtime.getRuntime().addShutdownHook(new shutDownThread(outputList));
			while (true) {
				ClientThread mst = new ClientThread(serverSocket.accept());
				mst.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
