package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Send extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Send frame = new Send();
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
	public Send() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblRecipient = new JLabel("Recipient");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblRecipient, 72, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblRecipient, 36, SpringLayout.WEST, contentPane);
		contentPane.add(lblRecipient);
		
		JLabel lblAmount = new JLabel("Amount");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblAmount, 34, SpringLayout.SOUTH, lblRecipient);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblAmount, 0, SpringLayout.WEST, lblRecipient);
		contentPane.add(lblAmount);
		
		JLabel lblDescriiption = new JLabel("Description");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblDescriiption, 36, SpringLayout.SOUTH, lblAmount);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblDescriiption, 0, SpringLayout.WEST, lblRecipient);
		contentPane.add(lblDescriiption);
		
		JTextArea txtrRecipient = new JTextArea();
		txtrRecipient.setText("Recipient");
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtrRecipient, -22, SpringLayout.SOUTH, lblRecipient);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtrRecipient, 40, SpringLayout.EAST, lblRecipient);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtrRecipient, 0, SpringLayout.SOUTH, lblRecipient);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtrRecipient, 132, SpringLayout.EAST, lblRecipient);
		contentPane.add(txtrRecipient);
		
		JTextArea txtrAmount = new JTextArea();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtrAmount, -12, SpringLayout.NORTH, lblAmount);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtrAmount, 0, SpringLayout.WEST, txtrRecipient);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtrAmount, -163, SpringLayout.EAST, contentPane);
		txtrAmount.setText("amount");
		contentPane.add(txtrAmount);
		
		JTextArea txtrDescription = new JTextArea();
		txtrDescription.setText("Description");
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtrDescription, -5, SpringLayout.NORTH, lblDescriiption);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtrDescription, 0, SpringLayout.WEST, txtrRecipient);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtrDescription, -38, SpringLayout.EAST, txtrAmount);
		contentPane.add(txtrDescription);
		
		JButton btnSend = new JButton("Send");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnSend, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnSend, 0, SpringLayout.EAST, txtrRecipient);
		contentPane.add(btnSend);
	}

}
