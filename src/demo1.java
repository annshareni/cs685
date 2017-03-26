

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class demo1 {

	private JFrame frame;
	symm s;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					demo1 window = new demo1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public demo1() {
		initialize();
		s = new symm();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		final JTextArea textArea = new JTextArea(); // message to send out		
		textArea.setBounds(6, 39, 172, 66);
		panel.add(textArea);
		
	
			
		JTextArea textArea_1 = new JTextArea(); // message to take in 
		textArea_1.setBounds(6, 156, 172, 66);
		panel.add(textArea_1);
		
		JLabel lblNewLabel = new JLabel("Type in Message:");
		lblNewLabel.setBounds(6, 19, 126, 16);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Recieve Message: ");
		lblNewLabel_1.setBounds(6, 134, 126, 16);
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
			}
		});
		btnNewButton.setBounds(228, 54, 117, 29);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New Message");
		btnNewButton_1.setBounds(228, 167, 117, 29);
		panel.add(btnNewButton_1);
	}
}
