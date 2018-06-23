package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Receive extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Receive frame = new Receive();
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
	public Receive() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JButton btnShow = new JButton("Show");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnShow, 161, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnShow, -31, SpringLayout.SOUTH, contentPane);
		contentPane.add(btnShow);
		
		JLabel lblUri = new JLabel("URI");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblUri, 62, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblUri, 30, SpringLayout.WEST, contentPane);
		contentPane.add(lblUri);
		
		JLabel lblAdress = new JLabel("Adress");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblAdress, 65, SpringLayout.SOUTH, lblUri);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblAdress, 35, SpringLayout.WEST, contentPane);
		contentPane.add(lblAdress);
		
		JTextArea txtrUrifield = new JTextArea();
		txtrUrifield.setText("URIField");
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtrUrifield, -22, SpringLayout.SOUTH, lblUri);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtrUrifield, 66, SpringLayout.EAST, lblUri);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtrUrifield, 0, SpringLayout.SOUTH, lblUri);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtrUrifield, 168, SpringLayout.EAST, lblUri);
		contentPane.add(txtrUrifield);
		
		JTextArea txtrAdressField = new JTextArea();
		txtrAdressField.setText("Adress Field");
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtrAdressField, -78, SpringLayout.NORTH, btnShow);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtrAdressField, 114, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtrAdressField, -56, SpringLayout.NORTH, btnShow);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtrAdressField, 229, SpringLayout.WEST, contentPane);
		contentPane.add(txtrAdressField);
	}

}
