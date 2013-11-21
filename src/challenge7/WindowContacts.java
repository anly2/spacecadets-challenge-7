package challenge7;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;

public class WindowContacts {
private Chat chat = new Chat();
	private JFrame frmContacts;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowContacts window = new WindowContacts();
					window.frmContacts.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WindowContacts() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmContacts = new JFrame();
		frmContacts.setTitle("Contacts");
		frmContacts.setBounds(100, 100, 269, 451);
		frmContacts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmContacts.getContentPane().setLayout(springLayout);
		
		JLabel lblContact1 = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblContact1, 58, SpringLayout.NORTH, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblContact1, 10, SpringLayout.WEST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblContact1, -10, SpringLayout.EAST, frmContacts.getContentPane());
		lblContact1.setHorizontalAlignment(SwingConstants.TRAILING);
		frmContacts.getContentPane().add(lblContact1);
		
		JLabel lblContact2 = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, lblContact2, 0, SpringLayout.WEST, lblContact1);
		springLayout.putConstraint(SpringLayout.EAST, lblContact2, 0, SpringLayout.EAST, lblContact1);
		lblContact2.setHorizontalAlignment(SwingConstants.TRAILING);
		frmContacts.getContentPane().add(lblContact2);
		
		JLabel lblContact3 = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblContact2, -29, SpringLayout.NORTH, lblContact3);
		springLayout.putConstraint(SpringLayout.SOUTH, lblContact2, -6, SpringLayout.NORTH, lblContact3);
		springLayout.putConstraint(SpringLayout.SOUTH, lblContact1, -35, SpringLayout.NORTH, lblContact3);
		springLayout.putConstraint(SpringLayout.WEST, lblContact3, 10, SpringLayout.WEST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblContact3, -10, SpringLayout.EAST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblContact3, 116, SpringLayout.NORTH, frmContacts.getContentPane());
		lblContact3.setHorizontalAlignment(SwingConstants.TRAILING);
		frmContacts.getContentPane().add(lblContact3);
		
		JLabel lblContact4 = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, lblContact4, 10, SpringLayout.WEST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblContact4, -10, SpringLayout.EAST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblContact3, -6, SpringLayout.NORTH, lblContact4);
		springLayout.putConstraint(SpringLayout.NORTH, lblContact4, 145, SpringLayout.NORTH, frmContacts.getContentPane());
		lblContact4.setHorizontalAlignment(SwingConstants.TRAILING);
		frmContacts.getContentPane().add(lblContact4);
		
		JLabel lblContact5 = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, lblContact5, 10, SpringLayout.WEST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblContact5, -10, SpringLayout.EAST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblContact4, -6, SpringLayout.NORTH, lblContact5);
		springLayout.putConstraint(SpringLayout.NORTH, lblContact5, 174, SpringLayout.NORTH, frmContacts.getContentPane());
		lblContact5.setHorizontalAlignment(SwingConstants.TRAILING);
		frmContacts.getContentPane().add(lblContact5);
		
		JLabel lblContact6 = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, lblContact6, 10, SpringLayout.WEST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblContact6, -10, SpringLayout.EAST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblContact5, -6, SpringLayout.NORTH, lblContact6);
		springLayout.putConstraint(SpringLayout.NORTH, lblContact6, 203, SpringLayout.NORTH, frmContacts.getContentPane());
		lblContact6.setHorizontalAlignment(SwingConstants.TRAILING);
		frmContacts.getContentPane().add(lblContact6);
		
		JLabel lblContact7 = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, lblContact7, 10, SpringLayout.WEST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblContact7, -10, SpringLayout.EAST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblContact6, -6, SpringLayout.NORTH, lblContact7);
		springLayout.putConstraint(SpringLayout.NORTH, lblContact7, 232, SpringLayout.NORTH, frmContacts.getContentPane());
		lblContact7.setHorizontalAlignment(SwingConstants.TRAILING);
		frmContacts.getContentPane().add(lblContact7);
		
		JLabel lblContact8 = new JLabel("");
		springLayout.putConstraint(SpringLayout.WEST, lblContact8, 10, SpringLayout.WEST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblContact8, -10, SpringLayout.EAST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblContact7, -6, SpringLayout.NORTH, lblContact8);
		springLayout.putConstraint(SpringLayout.NORTH, lblContact8, 261, SpringLayout.NORTH, frmContacts.getContentPane());
		lblContact8.setHorizontalAlignment(SwingConstants.TRAILING);
		frmContacts.getContentPane().add(lblContact8);
		
		JLabel lblContact9 = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblContact9, 290, SpringLayout.NORTH, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblContact9, 10, SpringLayout.WEST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblContact9, -100, SpringLayout.SOUTH, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblContact9, -10, SpringLayout.EAST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblContact8, -6, SpringLayout.NORTH, lblContact9);
		lblContact9.setHorizontalAlignment(SwingConstants.TRAILING);
		frmContacts.getContentPane().add(lblContact9);
		
		JLabel lblContact10 = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblContact10, 6, SpringLayout.SOUTH, lblContact9);
		springLayout.putConstraint(SpringLayout.WEST, lblContact10, 0, SpringLayout.WEST, lblContact1);
		springLayout.putConstraint(SpringLayout.SOUTH, lblContact10, -71, SpringLayout.SOUTH, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblContact10, -10, SpringLayout.EAST, frmContacts.getContentPane());
		lblContact10.setHorizontalAlignment(SwingConstants.TRAILING);
		frmContacts.getContentPane().add(lblContact10);
		
		JButton btnUp = new JButton("U");
		springLayout.putConstraint(SpringLayout.NORTH, btnUp, 22, SpringLayout.NORTH, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnUp, 101, SpringLayout.WEST, frmContacts.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnUp, -6, SpringLayout.NORTH, lblContact1);
		springLayout.putConstraint(SpringLayout.EAST, btnUp, -112, SpringLayout.EAST, frmContacts.getContentPane());
		frmContacts.getContentPane().add(btnUp);
		
		JButton btnDown = new JButton("D");
		springLayout.putConstraint(SpringLayout.NORTH, btnDown, 6, SpringLayout.SOUTH, lblContact10);
		springLayout.putConstraint(SpringLayout.WEST, btnDown, 0, SpringLayout.WEST, btnUp);
		springLayout.putConstraint(SpringLayout.SOUTH, btnDown, 320, SpringLayout.NORTH, lblContact1);
		springLayout.putConstraint(SpringLayout.EAST, btnDown, -112, SpringLayout.EAST, frmContacts.getContentPane());
		frmContacts.getContentPane().add(btnDown);
	}
}
