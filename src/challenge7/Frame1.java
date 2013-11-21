package challenge7;

import java.awt.*;
import javax.swing.*;  //notice javax

public class Frame1 extends JFrame
{
	private static final long serialVersionUID = -5555424069970280265L;
	
	int width = 300;
	int height = 500;
	Color strongColor = new Color (70, 100, 140);
	Font strongFont = new Font("Verdana", 1, 20);
	
	JPanel pane = new JPanel();
	
	Frame1() // the frame constructor method
	{
		super("Chat");
		//setBounds(200,100,300,500);
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pane.setLayout(new GridBagLayout());//new FlowLayout(FlowLayout.CENTER)
		Container con = this.getContentPane(); // inherit main frame
		con.add(pane); // add the panel to frame
		
		// customize panel here
		// pane.add(someWidget);
		
		JLabel lblUsername = new JLabel ("Username");
			lblUsername.setHorizontalAlignment(JLabel.LEFT);
	        lblUsername.setVerticalAlignment(JLabel.CENTER);
			lblUsername.setSize(300, getHeight());
	        lblUsername.setVerticalTextPosition(JLabel.CENTER);
			lblUsername.setForeground(strongColor);
			lblUsername.setFont(strongFont);
		pane.add (lblUsername); //Add component to panel
		
		JLabel lblPassword = new JLabel ("Password");
			lblPassword.setHorizontalAlignment(JLabel.LEFT);
			lblPassword.setVerticalAlignment(JLabel.CENTER);
			lblPassword.setSize(300, getHeight());
			lblPassword.setVerticalTextPosition(JLabel.CENTER);
			lblPassword.setForeground(strongColor);
			lblPassword.setFont(strongFont);
		pane.add (lblPassword);
			
		//JTextField txtUsername = new JTextField  (10);
		//JTextField txtPassword = new JTextField  (10);
		//JButton btnSubmit = new JButton ("Enter");
		
		setVisible(true); // display this frame
	}
	
	public static void main(String args[]) {
		new Frame1();
	}
}