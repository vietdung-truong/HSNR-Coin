import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.awt.event.ActionEvent;

public class walletTest extends JFrame {
	
	

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					walletTest frame = new walletTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public walletTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTextArea txtrPublicKey = new JTextArea();
		txtrPublicKey.setText("Public Key");
		contentPane.add(txtrPublicKey, BorderLayout.CENTER);
		
		JButton btnGenerateNewPublic = new JButton("Generate new Public Key");
		btnGenerateNewPublic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
				
					Wallet test = new Wallet();
					txtrPublicKey.setText(StringUtil.getStringFromKey(test.publicKey));
					
				
			}
		});
		contentPane.add(btnGenerateNewPublic, BorderLayout.SOUTH);
	}

}
