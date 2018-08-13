import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AddUser extends JFrame implements ActionListener{

	JTextField addUser = new JTextField();
	JTextField addSecu = new JTextField();
	JTextField addPass = new JTextField();
	//JTextField addRole = new JTextField();
	String[] addRole = {"   ", "SystemAdmin", "TechSupport", "Manager"};
	JComboBox addRoles = new JComboBox(addRole);
	String aRoles = "employee";
	JTextField addEmail = new JTextField();
	
	public AddUser(){
		super("Administrator - Add New User");
		setSize(400,600);
		setVisible(true);
		this.setLayout(new FlowLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		//panel.setLayout(new BorderLayout());
		
		JPanel welcome = new JPanel();
		JLabel welc = new JLabel("New Member");
		welcome.add(welc);
		panel.add(welcome, BorderLayout.NORTH);		
		
		JPanel upPanel = new JPanel();
		upPanel.setLayout(new GridLayout(6,1, 6, 20));
		
		//continues changing from here and on
		
		JLabel userN = new JLabel("Username: ");
		upPanel.add(userN);
		addUser.setToolTipText("Type username here");
		upPanel.add(addUser);	
		
		JLabel secur = new JLabel("Security Word: ");
		upPanel.add(secur);
		addSecu.setToolTipText("Security word, please");
		upPanel.add(addSecu);
		
		JLabel pass = new JLabel("Password: ");
		upPanel.add(pass);
		addPass.setToolTipText("Password, please");
		upPanel.add(addPass);
		
		/*JLabel role = new JLabel("Role: ");
		upPanel.add(role);
		addRole.setToolTipText("Role: sysAdmin - manager - techSup");
		upPanel.add(addRole);*/
		JLabel role = new JLabel("Role: ");		
		upPanel.add(role);
		//modRole.setToolTipText("Role: sysAdmin - manager - techSup");
		addRoles.setSelectedIndex(0);
		addRoles.addActionListener(this);
		upPanel.add(addRoles);
				
		JLabel email = new JLabel("Email: ");
		upPanel.add(email);
		addEmail.setToolTipText("Email, please");
		upPanel.add(addEmail);
				
		JButton adding = new JButton("Add New User");
		adding.addActionListener(this);
		adding.setActionCommand("ad");
		upPanel.add(adding);
		
		panel.add(upPanel,BorderLayout.CENTER);
		
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(6,1,8,20));
		
		JButton logout = new JButton("Logout");
		logout.addActionListener(this);
		logout.setActionCommand("out");
		right.add(logout);
		
		JButton back = new JButton("Go Back");
		back.addActionListener(this);
		back.setActionCommand("back");
		right.add(back);
		
		panel.add(right, BorderLayout.EAST);
		
		
		
		this.add(panel);
		
		validate();
		repaint();
		
	}
			
	public void addingUser (){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception e){}
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
			stmt = conn.createStatement();
			
			String aUn = addUser.getText();
    	    String aPas = addPass.getText();
    	    String aRole = aRoles;
    	    String aEmail = addEmail.getText();
    	    String aSecu = addSecu.getText();
    	    
			// until here because, from this point I have to add new password and update the data.
    	    if (stmt.execute("INSERT INTO `Ticketing`.`User` (`username`, `password`, `role`, `email`, `security`) VALUES ('"+aUn+"', '"+aPas+"', '"+aRole+"', '"+aEmail+"', '"+aSecu+"');")) {
    	    	
    	    }
    	
    	   
    	} catch (SQLException ex) {
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		if (e.getActionCommand().equals("ad")){
			//we refer here to ugly because is the frame that we will use to attach the message that will be shown
			int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to add a New Member?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	System.out.println("Has been added a new user");
		    	addingUser();
		    }else if (n!=0){
		    	this.setVisible(false);
		    	new AddUser();
		    }
		}
		
		if(e.getActionCommand().equals("out")){
			System.out.println("See You Next time");
			int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to close the program?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	System.exit(0);
		    }else if (n!=0){
		    	new AddUser();
		    }

		}
		
		if (e.getSource() == addRoles){
			JComboBox aR = (JComboBox)e.getSource();
			String rol = (String)aR.getSelectedItem();
			
			switch (rol){
				case "SystemAdmin": aRoles = "sysAdm";
					System.out.println("It is: " + aRoles);
					break;
				case "TechSupport": aRoles = "techSup";
					System.out.println("It is: " + aRoles);
					break;
				case "Manager" : aRoles = "manager";
				    System.out.println("It is: " + aRoles);
				    break;
		    }
		}
		
		if(e.getActionCommand().equals("back")){
			
			this.setVisible(false);
			new Admin();
		
	    }
	}

}
