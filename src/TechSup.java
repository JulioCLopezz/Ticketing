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
import javax.swing.JTextField;

public class TechSup extends JFrame implements ActionListener {
	static String Technician = Login.username.getText();
	

	public TechSup(){
		
		//Button and Text field structure of Tech Suppport Wareframe
		super("Tech Support - " + Technician);
		setSize(400,400);
		setVisible(true);
		this.setLayout(new FlowLayout());
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		this.add(main);
		
		JPanel mainTop = new JPanel();
		JLabel title = new JLabel("Welcome, " + Technician);
		mainTop.add(title);
		main.add(mainTop, BorderLayout.NORTH);
		
		JPanel centre = new JPanel();
		centre.setLayout(new GridLayout(6,1, 5, 5));
		
		JButton creating = new JButton("Create New Ticket");
		creating.addActionListener(this);
		creating.setActionCommand("newT");
		centre.add(creating);
		
		JButton modifyT = new JButton("Modify Ticket");
		modifyT.addActionListener(this);
		modifyT.setActionCommand("modT");
		centre.add(modifyT);
		
		JButton myTickets = new JButton("My Tickets");
		myTickets.addActionListener(this);
		myTickets.setActionCommand("mtic");
		centre.add(myTickets);
		
		JButton tickets = new JButton("List of Tickets");
		tickets.addActionListener(this);
		tickets.setActionCommand("tic");
		centre.add(tickets);
		
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

	//Buttons action command
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("newT")){
			System.out.println("You are gonna create a Ticket");
			this.setVisible(false);
			new addTicket();
		}
		else if(e.getActionCommand().equals("modT")){
			System.out.println("You will modify a Ticket");
			this.setVisible(false);
			new modiTicket();
		}
		
		else if(e.getActionCommand().equals("tic")){
			System.out.println("You will see the Ticket List");
			this.setVisible(false);
			new ticList();
		}
		
		else if(e.getActionCommand().equals("mtic")){
			System.out.println("You will see your Ticket List");
			this.setVisible(false);
			new myTicList();
		}
		
		else if(e.getActionCommand().equals("out")){
			System.out.println("See You Next time");
			int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to close the program?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	
		    	System.exit(0);
		    }
			else if (n!=0){
				this.setVisible(false);
				new TechSup();
			}
		}
	}

}
