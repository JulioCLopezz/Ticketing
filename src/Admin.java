import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Admin extends JFrame implements ActionListener {
	//constructor for Account type Admin
	public Admin(){
		//Distribution of Button, label and text field required
		super("System Administrator");
		setSize(400,400);
		setVisible(true);
		this.setLayout(new FlowLayout());
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		this.add(main);
		
		JPanel mainTop = new JPanel();
		JLabel title = new JLabel("Administrator");
		mainTop.add(title);
		main.add(mainTop, BorderLayout.NORTH);
		
		JPanel centre = new JPanel();
		centre.setLayout(new GridLayout(4,1, 5, 5));
				
		JButton adding = new JButton("Add New Members");
		adding.addActionListener(this);
		adding.setActionCommand("newAdd");
		centre.add(adding);
		
		JButton modify = new JButton("Modify Members");
		modify.addActionListener(this);
		modify.setActionCommand("mod");
		centre.add(modify);
		
		JButton members = new JButton("List of Members");
		members.addActionListener(this);
		members.setActionCommand("mem");
		centre.add(members);
		
		main.add(centre, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new GridLayout(1,1));
		
		JButton logout = new JButton("Logout");
		logout.addActionListener(this);
		logout.setActionCommand("out");
		bottom.add(logout);
		main.add(bottom, BorderLayout.SOUTH);
		
		
		validate();
		repaint();
	}
	
	//actions after click each button
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("newAdd")){
			System.out.println("You are gonna add somebody");
			this.setVisible(false);
			new addUser();
		}
		else if(e.getActionCommand().equals("mod")){
			System.out.println("You will modify an user");
			this.setVisible(false);
			new modUser();
		}
		
		else if(e.getActionCommand().equals("mem")){
			System.out.println("You will see the Membrs List");
			this.setVisible(false);
			new MemberList();
		}
		else if(e.getActionCommand().equals("out")){
			System.out.println("See You Next time");
			int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to close the program?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	
		    	System.exit(0);
		    }
			
		}
	}

}
