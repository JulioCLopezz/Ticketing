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

public class modUser extends JFrame implements ActionListener{

	JTextField idN = new JTextField();
	JTextField modUser = new JTextField();
	JTextField modSecu = new JTextField();
	JTextField modPass = new JTextField();
	//JTextField modRole = new JTextField();
	String[] modRole = {"   ", "SystemAdmin", "TechSupport", "Manager"};
	JComboBox modRoles = new JComboBox(modRole);
	String roles = "employee";
	JTextField modEmail = new JTextField();
	
	public modUser(){
		super("Administrator - Modify an existent User");
		setSize(400,600);
		setVisible(true);
		this.setLayout(new FlowLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.add(panel);
		
		JPanel welcome = new JPanel();
		JLabel welc = new JLabel("Modify Member");
		welcome.add(welc);
		panel.add(welcome, BorderLayout.NORTH);		
		
		JPanel upPanel = new JPanel();
		upPanel.setLayout(new GridLayout(7,1, 6, 20));
		
		JLabel userID = new JLabel("ID#: ");
		upPanel.add(userID);
		idN.setToolTipText("Type ID number here");
		upPanel.add(idN);
		
		JLabel userN = new JLabel("New Username: ");
		upPanel.add(userN);
		modUser.setToolTipText("Type username here");
		upPanel.add(modUser);	
		
		JLabel secur = new JLabel("New Security Word: ");
		upPanel.add(secur);
		modSecu.setToolTipText("Security word, please");
		upPanel.add(modSecu);
		
		JLabel pass = new JLabel("New Password: ");
		upPanel.add(pass);
		modPass.setToolTipText("Password, please");
		upPanel.add(modPass);
		
		JLabel role = new JLabel("New Role: ");		
		upPanel.add(role);
		modRoles.setSelectedIndex(0);
		modRoles.addActionListener(this);
		upPanel.add(modRoles);
		
		JLabel email = new JLabel("New Email: ");
		upPanel.add(email);
		modEmail.setToolTipText("Email, please");
		upPanel.add(modEmail);
				
		JButton modi = new JButton("Update User");
		modi.addActionListener(this);
		modi.setActionCommand("mody");
		upPanel.add(modi);
				
		panel.add(upPanel,BorderLayout.CENTER);
		
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(6, 1, 8, 20));
		
		JButton logout = new JButton("Logout");
		logout.addActionListener(this);
		logout.setActionCommand("out");
		right.add(logout);
		
		JButton back = new JButton("Go Back");
		back.addActionListener(this);
		back.setActionCommand("back");
		right.add(back);
		
		
		panel.add(right, BorderLayout.EAST);
			
		validate();
		repaint();
		
	}
			
	public void modingUser (){
		//connecting to DB
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception e){}
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//Updating DB user info
		try{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
			stmt = conn.createStatement();
			
			String idNu = idN.getText();
			String mUn = modUser.getText();
    	    String mPas = modPass.getText();
    	    String mRole = roles;
    	    String mEmail = modEmail.getText();
    	    String mSecu = modSecu.getText();
    	    
    	    if (stmt.execute("UPDATE `Ticketing`.`User` SET `username` = '"+mUn+"', `password` = '"+mPas+"', `role` = '"+mRole+"', `email` = '"+mEmail+"', `security` = '"+mSecu+"' WHERE `ID` = '"+idNu+"';")) {	
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
		//User role
		if (e.getSource() == modRoles){
			JComboBox mR = (JComboBox)e.getSource();
			String rol = (String)mR.getSelectedItem();
			
			switch (rol){
				case "SystemAdmin": roles = "sysAdm";
					System.out.println("It is: " + roles);
					break;
				case "TechSupport": roles = "techSup";
					System.out.println("It is: " + roles);
					break;
				case "Manager" : roles = "manager";
				    System.out.println("It is: " + roles);
				    break;
			}
		}
		
		//Action command to confirm midifications		
		if (e.getActionCommand().equals("mody")){
			//we refer here to ugly because is the frame that we will use to attach the message that will be shown
			int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to modify this Member?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	System.out.println("This member has been modified");
		    	modingUser();
		    }else if (n!=0){
		    	this.setVisible(false);
		    	new modUser();
		    }
		}
		
		//Action commands to Logout and go back
		if(e.getActionCommand().equals("out")){
			System.out.println("See You Next time");
			int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to close the program?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	System.exit(0);
		    }else if (n!=0){
		    	this.setVisible(false);
		    	new modUser();
		    }

		}
		
		if(e.getActionCommand().equals("back")){
			
			this.setVisible(false);
			new Admin();
		
	    }
		
	}

}
