import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MemberList extends JFrame implements ActionListener{
	
	String[][] data = new String[100][6];
	String[] colNames = {"ID", "Username", "Password", "Email", "Security", "Role"};
	
	public MemberList(){
		
		super("System Administrator - Members");
		setSize(900,500);
		setVisible(true);
		this.setLayout(new FlowLayout());
		
		JPanel mainF = new JPanel();
		mainF.setLayout(new BorderLayout());
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		mainF.add(main, BorderLayout.CENTER);
		
		JPanel mainTop = new JPanel();
		JLabel title = new JLabel("Administrator");
		mainTop.add(title);
		main.add(mainTop, BorderLayout.NORTH);
		
		JPanel centre = new JPanel();
		centre.setLayout(new GridLayout(2,1));
		
		//connecting to DB
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception e){}
		
		Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	
    	//Getting info from DB to show it on a Table
    	try {
    		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");

    	    stmt = conn.createStatement();
    	    rs = stmt.executeQuery("SELECT * FROM User;");
    	    
    	    int counter = 0;
    	    
    	    while(rs.next()){
    	    	String id = rs.getString("ID");
    	       
    	    	data[counter][0] = id;
    	      
    	        String un = rs.getString("Username");
    	    	data[counter][1] = un;    	      
    	        
    	        String pas = rs.getString("Password");
    	    	data[counter][2] = pas;
    	    	
    	    	String em = rs.getString("Email");
    	    	data[counter][3] = em;    	      
    	        
    	        String se = rs.getString("Security");
    	    	data[counter][4] = se;
    	    	
    	    	String ro = rs.getString("Role");
    	    	data[counter][5] = ro;
    	        
    	        counter++;
    	    }
    	    
		} catch (SQLException ex) {
	    // handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		
		//adding the table to JFrame
		JPanel centreBo = new JPanel();
		centreBo.setLayout(new FlowLayout());
		
		JTable listT = new JTable(data, colNames);
	    JScrollPane scrP = new JScrollPane(listT);
	    scrP.setPreferredSize(new Dimension(500,350));
	    scrP.setBorder(BorderFactory.createTitledBorder("User List"));
	    centreBo.add(scrP);
	    centre.add(centreBo);
	    
	    listT.getColumnModel().getColumn(0).setPreferredWidth(6);
	    listT.getColumnModel().getColumn(1).setPreferredWidth(20);
	    listT.getColumnModel().getColumn(2).setPreferredWidth(10);
	    listT.getColumnModel().getColumn(5).setPreferredWidth(20);
	    listT.getColumnModel().getColumn(4).setPreferredWidth(15);
	    
	    
		main.add(centre, BorderLayout.CENTER);
		
		//Buttons and Labels of this frame
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(16, 1, 8, 20));
		
		JButton logout = new JButton("Logout");
		logout.addActionListener(this);
		logout.setActionCommand("out");
		right.add(logout);
		
		JButton back = new JButton("Go Back");
		back.addActionListener(this);
		back.setActionCommand("back");
		right.add(back);
		
		mainF.add(right, BorderLayout.EAST);
		
		JPanel left = new JPanel();
		left.setLayout(new FlowLayout());
		
		JButton refresh = new JButton("Refresh List");
		refresh.addActionListener(this);
		refresh.setActionCommand("ref");
		left.add(refresh);
		mainF.add(left, BorderLayout.WEST);
		
		this.add(mainF);
		
		validate();
		repaint();
	}
	
			
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Update action command
		if(e.getActionCommand().equals("ref")){
			System.out.println("You will see modifications");
			setVisible(false);
			new MemberList();
		}
		
		//Logout and go back action commands
		if(e.getActionCommand().equals("out")){
			int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to close the program?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	
		    	System.exit(0);
		    }
		
		}
		
		if(e.getActionCommand().equals("back")){
			int n = JOptionPane.showConfirmDialog(this, "Do you want to close this window?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	this.setVisible(false);
		    	new Admin();
		    }else{
		    	new Admin();
		    }
			
	    }
	}

}
