package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Portfolio extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Portfolio frame = new Portfolio();
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
	public Portfolio() {
		setDefaultCloseOperation(Portfolio.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblAktuellerKontostand = new JLabel("Aktueller Kontostand");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblAktuellerKontostand, 112, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblAktuellerKontostand, 16, SpringLayout.WEST, contentPane);
		contentPane.add(lblAktuellerKontostand);
		
		JTextArea txtrKontostand = new JTextArea();
		sl_contentPane.putConstraint(SpringLayout.EAST, lblAktuellerKontostand, -112, SpringLayout.WEST, txtrKontostand);
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtrKontostand, -5, SpringLayout.NORTH, lblAktuellerKontostand);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtrKontostand, 228, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtrKontostand, -55, SpringLayout.EAST, contentPane);
		txtrKontostand.setText("Kontostand");
		contentPane.add(txtrKontostand);
		
		JTextArea txtrVerlauf = new JTextArea();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtrVerlauf, 174, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtrVerlauf, 168, SpringLayout.WEST, contentPane);
		txtrVerlauf.setText("Verlauf");
		contentPane.add(txtrVerlauf);
		
		JButton btnGenerateNewPublic = new JButton("Generate new Public Key");
		btnGenerateNewPublic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		sl_contentPane.putConstraint(SpringLayout.WEST, btnGenerateNewPublic, 133, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnGenerateNewPublic, -10, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnGenerateNewPublic);
		
		JTextArea txtrPublicKeyTest = new JTextArea();
		txtrPublicKeyTest.setText("Public Key Test");
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtrPublicKeyTest, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtrPublicKeyTest, 30, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtrPublicKeyTest, 78, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtrPublicKeyTest, 365, SpringLayout.WEST, contentPane);
		contentPane.add(txtrPublicKeyTest);
	}

}
