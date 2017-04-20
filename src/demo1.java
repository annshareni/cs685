

/*
 * @author Rui Wang
 * application layer
 * */

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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.io.IOException;



import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;





public class demo1 {

	JFrame frame;
	JComboBox<String> comboBox ;
	JTextArea textArea;
    JTextArea textArea_1 ;
    JLabel lblNewLabel_3;
    JLabel label;
    static JTextField txtNew = new JTextField();
	
	static symm s = new symm();
	int messageCount = 0;
	private JTextField textField;
	private JTextField textField_1;
	
	static int select=-1;
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
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
		
		
		
		Provider server = new Provider(s,txtNew );
        while(true){
            server.run();
        }
        

	}

	/**
	 * Create the application.
	 */
	public demo1() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 641, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("Algorithm");
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textArea = new JTextArea(); // message to send out		
		textArea.setBounds(6, 39, 223, 66);
		panel.add(textArea);
		
	
			
		textArea_1 = new JTextArea(); // message to take in 
		textArea_1.setBounds(6, 162, 223, 66);
		panel.add(textArea_1);
		
		JLabel lblNewLabel = new JLabel("Type in Message:");
		lblNewLabel.setBounds(6, 19, 126, 16);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Recieve Message: ");
		lblNewLabel_1.setBounds(6, 134, 126, 16);
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Encode");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String t = textArea.getText();
				s.setInputMessage(t);
				select = comboBox.getSelectedIndex();
				s.setAlgorithm(String.valueOf(select));
				
				if (select ==0) {
					//lblNewLabel_3.setText("Type Key Here:");
					s.Encode();
					s.HashWithSha1_512(s.getCypherText());
					textField.setText("Ready to Send");
					
				}else if (select == 1 ){
					s.EncryptWithAES_256();
					s.HashWithSha1_512(s.getCypherText());
					textField.setText("Ready to Send");
				}else if (select == 2 ) {
					
				}else if (select == 3 ) {
					
				}
				
				
				
			
				
			}
		}); // end send button event handler 
		
		btnNewButton.setBounds(267, 72, 70, 33);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Decode");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				messageCount--;
				txtNew.setText("");
				if (s.isIntegrity()) {
					//System.out.println(s.getAlgorithm());
					if(s.getAlgorithm().equals("0")) {
						//label.setText("Type Key Here");
						s.Decode();
						textField_1.setText("Ready to show");
					}else if(s.getAlgorithm().equals("1")) {
						s.DecryptWithAES_256();
						textField_1.setText("Ready to show");
						
					}
				}else {
					textArea_1.setText("Hash doesn't match!");
				}
				
				//textArea_1.setText(s.getPlainText());
			}
		});// end receive message handler
		
		btnNewButton_1.setBounds(267, 190, 70, 38);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Clear");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea.setText("");
				s.setInputMessage("");
				s.setCypherText("");
				s.setPlainText("");
				txtNew.setText("");
				label.setText("");
				lblNewLabel_3.setText("");
				textField.setText("");
				textField_1.setText("");
				
				
			}
		});
		btnNewButton_2.setBounds(126, 14, 76, 29);
		panel.add(btnNewButton_2);
		
		JButton button = new JButton("Clear");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textArea_1.setText("");
				s.setInputMessage("");
				s.setCypherText("");
				s.setPlainText("");
				txtNew.setText("");
				label.setText("");
				lblNewLabel_3.setText("");
				textField.setText("");
				textField_1.setText("");
				
			}
		});
		button.setBounds(126, 129, 76, 29);
		panel.add(button);
		
		
		
		txtNew.setForeground(Color.RED);
		txtNew.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		txtNew.setBackground(Color.LIGHT_GRAY);
		txtNew.setBounds(270, 157, 158, 28);
		panel.add(txtNew);
		txtNew.setColumns(10);
		
		
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Vigenère", "AES 256", "RSA 2048", "Signature"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(257, 29, 177, 38);
		panel.add(comboBox);
		
		JLabel lblNewLabel_2 = new JLabel("Algorithms");
		lblNewLabel_2.setBounds(267, 19, 87, 16);
		panel.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(429, 33, 154, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_3 = new JLabel(""); // key encode
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(429, 6, 126, 26);
		panel.add(lblNewLabel_3);
		
		label = new JLabel(""); // key decode
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(440, 134, 126, 26);
		panel.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(439, 156, 154, 28);
		panel.add(textField_1);
		
		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				messageCount++; 
				Requester client = new Requester(s);
		        client.run();
				/*if (select == 0) {
					
					if(!textField.getText().isEmpty()) {
						String key = textField.getText();
						s.setKEY(key);
						
						s.Encode();
						s.HashWithSha1_512(s.getCypherText());
						messageCount++; 
						Requester client = new Requester(s);
				        client.run();
						}else {
							
							lblNewLabel_3.setForeground(Color.RED);
						}
					
				} else {
					messageCount++; 
					Requester client = new Requester(s);
			        client.run();
				}*/
				
				
				
			}
		});
		btnSend.setBounds(439, 72, 70, 33);
		panel.add(btnSend);
		
		JButton btnShow = new JButton("Show");
		btnShow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				/*if (s.getAlgorithm().equals("0")){
					if(!textField_1.getText().isEmpty()) {
						String key = textField_1.getText();
						s.setKEY(key);
						
						s.Decode();
						textArea_1.setText(s.getPlainText());
						
						}else {
							
							label.setForeground(Color.RED);
						}
				}else if (s.getAlgorithm().equals("1")) {
					textArea_1.setText(s.getPlainText());
				}*/
				
				
				textArea_1.setText(s.getPlainText());
				
				
				
			}
		});
		btnShow.setBounds(439, 195, 70, 33);
		panel.add(btnShow);
	}
}
