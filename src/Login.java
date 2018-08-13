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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener{
	//Declaring Global Variables
	static JTextField username = new JTextField();
	JTextField password = new JTextField();
	static String IDTechSup;
	
	//Constructor
	public Login(){
		super("Ticket System");
		setSize(400,600);
		setVisible(true);
		this.setLayout(new FlowLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.add(panel);

		JPanel welcome = new JPanel();
		JLabel welc = new JLabel("WELCOME to Ticket System");
		welcome.add(welc);
		panel.add(welcome, BorderLayout.NORTH);		
		
		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(new GridLayout(4,1, 6, 20));
		panel.add(loginPanel,BorderLayout.CENTER);
		
		JLabel user = new JLabel("Username: ");
		loginPanel.add(user);
		username.setToolTipText("Type username here");
		loginPanel.add(username);		
		
		JLabel pass = new JLabel("Password: ");
		loginPanel.add(pass);
		password.setToolTipText("Your password, please");
		loginPanel.add(password);
		
		JButton login = new JButton("Login!");
		login.addActionListener(this);
		login.setActionCommand("log");
		loginPanel.add(login);
		
		validate();
		repaint();
		
	}
			
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Login();

	}
	
	public void loginSystem (){
		//connecting to Ticketing DB
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception e){}
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//Checking data in Ticketing DB to allow access to the system
		try{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
			stmt = conn.createStatement();
			
			String un = username.getText();
    	    		String pw = password.getText();
			
    	    if (stmt.execute("SELECT * FROM User WHERE username = '"+un+"' and password = '"+pw+"'")) {
    	        rs = stmt.getResultSet();
    	    }
    	    
    	    if(rs.next()){	
    	    	JOptionPane.showMessageDialog(this, "Logged in!", "Confirmation Message", JOptionPane.PLAIN_MESSAGE);

    	    	System.out.print("----------------------");
    	    	String role = rs.getString("role");
    	        System.out.println("ID: " + role);
    	        IDTechSup = rs.getString("ID");
    	    	if(role.equals("sysAdm")){
    	    		new Admin();
    	    		System.out.println("hello");
    	    	}	
    	    	else if(role.equals("techSup")){
    	    		new TechSup();
    	    	}
    	        else if(role.equals("manager")){
    	    		new Manager();
    	    	}
    	    	
       	    }else {
       	    	JOptionPane.showMessageDialog(this, "Try it again. Username or Password incorrect");
       	    	this.setVisible(false);
       	    	new Login();
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
		if(e.getActionCommand().equals("log")){
			//System.out.println("Has been logged");
			loginSystem();
			this.setVisible(false);
		}
		
		/*if(e.getActionCommand().equals("res")){
			//System.out.println("Has been logged");
			new resetPassword();
		}*/
		
	}

}
