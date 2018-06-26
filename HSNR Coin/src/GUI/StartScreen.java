package GUI;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpringLayout;

public class StartScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartScreen window = new StartScreen();
					window.frame.setVisible(true);
					String userHome = System.getProperty("user.home");
					File f = new File(userHome+"//usercredential.json");
					if(f.exists() && !f.isDirectory()) { 
					    // do something
						// read the public key and the private key from the data
					}else {
						String path = userHome+"//usercredential.json";
						// Use relative path for Unix systems
						File f1 = new File(path);

						Wallet ourWallet = new Wallet();//wir müssen noch die Package importieren.
						//Stringbuffer mit neuen Public key und Private Key.
						f1.getParentFile().mkdirs(); 
						f1.createNewFile();
						
						InputStream inSig = PGPUtil.getDecoderStream(new FileInputStream(signaturFile));
					    //ArmoredInputStream inSig = new ArmoredInputStream(new FileInputStream(signaturFile));
					    JcaPGPObjectFactory objFactory = new JcaPGPObjectFactory(inSig);
					    PGPSignatureList signatureList = (PGPSignatureList) objFactory.nextObject();
					    PGPSignature signature = signatureList.get(0);

					    InputStream keyIn = PGPUtil.getDecoderStream(new FileInputStream(publicKeyFile));
					    //ArmoredInputStream keyIn = new ArmoredInputStream(new FileInputStream(publicKeyFile));
					    JcaPGPPublicKeyRingCollection pgpRing = new JcaPGPPublicKeyRingCollection(keyIn);
					    PGPPublicKey publicKey = pgpRing.getPublicKey(signature.getKeyID());

					    byte[] bytePublicKeyFingerprint = publicKey.getFingerprint();
					    char[] publicKeyFingerprintHexArray = org.apache.commons.codec.binary.Hex.encodeHex(bytePublicKeyFingerprint);
					    String publicKeyFingerprintHex = new String(publicKeyFingerprintHexArray);

					    signature.init(new JcaPGPContentVerifierBuilderProvider().setProvider("BC"), publicKey);

					    FileInputStream in = new FileInputStream(file);
					    byte[] byteData = new byte[(int) file.length()];
					    in.read(byteData);
					    in.close();
					    signature.update(byteData);

					    if (signature.verify() && publicKeyFingerprintHex.equals(fingerprint)) {
					        return true;
					    } else {
					        return false;
					    }
					}
					//liest lokales blockchain aus dem pfad
					//connect to server
					//uptodate?
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public StartScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnPortfolio = new JButton("Portfolio");
		btnPortfolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Portfolio portfolio = new Portfolio();
				portfolio.setVisible(true);
				
			}
		});
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, btnPortfolio, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnPortfolio, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnPortfolio, 434, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().setLayout(springLayout);
		frame.getContentPane().add(btnPortfolio);
		
		JButton btnWallet = new JButton("Wallet");
		btnWallet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SendundReceive sendundreceive = new SendundReceive();
				sendundreceive.setVisible(true);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnWallet, 68, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnWallet, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnWallet, -88, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnWallet, 0, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnPortfolio, -6, SpringLayout.NORTH, btnWallet);
		frame.getContentPane().add(btnWallet);
		
		JButton btnMiningMode = new JButton("Mining Mode");
		btnMiningMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mining mining = new Mining();
				mining.setVisible(true);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnMiningMode, 6, SpringLayout.SOUTH, btnWallet);
		springLayout.putConstraint(SpringLayout.SOUTH, btnMiningMode, 78, SpringLayout.SOUTH, btnWallet);
		springLayout.putConstraint(SpringLayout.WEST, btnMiningMode, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnMiningMode, 434, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnMiningMode);
	}

}
