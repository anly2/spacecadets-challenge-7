package challenge7;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class WindowChat {

	private JFrame frmChat;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowChat window = new WindowChat();
					window.frmChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WindowChat() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChat = new JFrame();
		frmChat.setTitle("Chat");
		frmChat.setBounds(100, 100, 449, 339);
		frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChat.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 257, 286, 33);
		frmChat.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSend = new JButton("SEND");
		btnSend.setBounds(306, 252, 89, 43);
		frmChat.getContentPane().add(btnSend);
		
		JLabel lblMessage = new JLabel("");
		lblMessage.setBounds(10, 41, 385, 205);
		frmChat.getContentPane().add(lblMessage);
		
		JLabel lblUser = new JLabel("");
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setBounds(10, 11, 111, 27);
		frmChat.getContentPane().add(lblUser);
		
		JLabel lblContact = new JLabel("");
		lblContact.setBounds(185, 11, 111, 27);
		frmChat.getContentPane().add(lblContact);
		
		JLabel lblArrow = new JLabel("->");
		lblArrow.setHorizontalAlignment(SwingConstants.CENTER);
		lblArrow.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblArrow.setBounds(131, 11, 46, 27);
		frmChat.getContentPane().add(lblArrow);
	}

}
