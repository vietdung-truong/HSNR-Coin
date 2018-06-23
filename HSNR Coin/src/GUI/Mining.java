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

public class Mining extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mining frame = new Mining();
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
	public Mining() {
		setDefaultCloseOperation(Mining.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblBisherGemint = new JLabel("Bisher gemint:");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBisherGemint, 92, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBisherGemint, 42, SpringLayout.WEST, contentPane);
		contentPane.add(lblBisherGemint);
		
		JTextArea txtrMinedBlocks = new JTextArea();
		txtrMinedBlocks.setText("Mined Blocks");
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtrMinedBlocks, 92, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtrMinedBlocks, 30, SpringLayout.EAST, lblBisherGemint);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, txtrMinedBlocks, 114, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtrMinedBlocks, 143, SpringLayout.EAST, lblBisherGemint);
		contentPane.add(txtrMinedBlocks);
		
		JButton btnStartMining = new JButton("Start Mining");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnStartMining, -66, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnStartMining, 106, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnStartMining, -43, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnStartMining, 283, SpringLayout.WEST, contentPane);
		contentPane.add(btnStartMining);
	}

}
