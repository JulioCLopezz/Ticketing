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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

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
import javax.swing.JTextField;

public class Manager extends JFrame implements ActionListener{
	
	String[][] data = new String[3][4];
	String[] colNames = {"Tech Support", "Tickets", "Open", "Closed"};
	String[][] datac = new String[1][3];
	String[] colNamesc = {"Total Tickets", "Total Open", "Total Closed"};
	String[][] dataCt = new String[1][2];
	String[] colNamesCt = {"Open Cost", "Closed Cost"};
	
	JTextField iDticket = new JTextField();
	JTextField iCticket = new JTextField();
	long epochF = Instant.now().getEpochSecond();
	long stD;
	long clD;
	String startD;
	String closeD;
	
	public Manager(){
		
		super("Manager - " + Login.username.getText());
		setSize(900,500);
		setVisible(true);
		this.setLayout(new FlowLayout());
		
		JPanel mainF = new JPanel();
		mainF.setLayout(new BorderLayout());
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.setPreferredSize(new Dimension(600,600));
		mainF.add(main, BorderLayout.CENTER);
		
		JPanel mainTop = new JPanel();
		JLabel title = new JLabel("Management Information");
		mainTop.add(title);
		main.add(mainTop, BorderLayout.NORTH);
		JPanel centre = new JPanel();
		centre.setLayout(new GridLayout(10, 1, 10, 5));
		
		//connecting to DB
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception e){}
		
		Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;

    	//Getting info required from DB to create Tables
    	try {
    		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
    	    stmt = conn.createStatement();
    	    rs = stmt.executeQuery("SELECT `username`, COUNT(ID_Ticket) FROM `Ticketing`.`Ticket` `t`, `Ticketing`.`User` `u` WHERE `t`.`ID_TechSup`= `u`.`ID` GROUP BY `u`.`username`; ");
    	     
    	    int counter = 0;
    	    
    	    while(rs.next()){
    	    	String usnm = rs.getString("username");
    	       	data[counter][0] = usnm;
    	      
    	        String nT = rs.getString("COUNT(ID_Ticket)");
    	    	data[counter][1] = nT;    	      

    	        counter++;
    	    }
    	    
		} catch (SQLException ex) {
	    // handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
    	
    	try {
    		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
    	    stmt = conn.createStatement();
    	    String oSta = "open";
    	    
    	    rs = stmt.executeQuery("SELECT `username`, COUNT(ID_Ticket) FROM `Ticketing`.`Ticket` `t`, `Ticketing`.`User` `u` WHERE `t`.`ID_TechSup`= `u`.`ID` AND `t`.`status`='"+oSta+"' GROUP BY `u`.`username` ; ");
    	     
    	    int counter = 0;
    	    
    	    while(rs.next()){
    	    	//to get the value from database use inside the () in getString the name of the column in the table
    	    	String opn = rs.getString("COUNT(ID_Ticket)");
    	       	data[counter][2] = opn;
    	        counter++;
    	    }
    	    
		} catch (SQLException ex) {
	    // handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
    	try {
    		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
    	    stmt = conn.createStatement();
    	    String cSta = "close";
    	    
    	    rs = stmt.executeQuery("SELECT `username`, COUNT(ID_Ticket) FROM `Ticketing`.`Ticket` `t`, `Ticketing`.`User` `u` WHERE `t`.`ID_TechSup`= `u`.`ID` AND `t`.`status`='"+cSta+"' GROUP BY `u`.`username` ; ");
    	     
    	    int counter = 0;
    	    
    	    while(rs.next()){
    	    	//to get the value from database use inside the () in getString the name of the column in the table
    	    	
    	        String cls = rs.getString("COUNT(ID_Ticket)");
    	    	data[counter][3] = cls;  	      

    	        counter++;
    	    }
    	    
		} catch (SQLException ex) {
	    // handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		JPanel centreBo = new JPanel();
		centreBo.setLayout(new FlowLayout());
		
		JTable listT = new JTable(data, colNames);
	    JScrollPane scrP = new JScrollPane(listT);
	    scrP.setPreferredSize(new Dimension(450,100));
	    scrP.setBorder(BorderFactory.createTitledBorder("Tickets Distribution"));
	    centreBo.add(scrP);
	    centre.add(centreBo);
	    
	    
	    try {
    		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
    	    stmt = conn.createStatement();
    	    String cSta = "close";
    	    String oSta = "open";
    	    
    	    rs = stmt.executeQuery("SELECT COUNT(ID_Ticket) FROM `Ticketing`.`Ticket`; ");
    	    int counter = 0;
    	    
    	    while(rs.next()){
    	    	String tot = rs.getString("COUNT(ID_Ticket)");
    	       	datac[counter][0] = tot;
    	        counter++;
    	    }
    	    
		} catch (SQLException ex) {
	    // handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
    	
	    try {
    		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
    	    stmt = conn.createStatement();
    	    
    	    String oSta = "open";
    	    
    	    rs = stmt.executeQuery("SELECT COUNT(ID_Ticket) FROM `Ticketing`.`Ticket` WHERE `status`='"+oSta+"'; "); 
    	    int counter = 0;
    	    
    	    while(rs.next()){
    	    	String ope = rs.getString("COUNT(ID_Ticket)");
    	    	int opC = rs.getInt("COUNT(ID_Ticket)") * 50;
    	    	String opeC = Integer.toString(opC);
    	       	datac[counter][1] = ope;
    	       	dataCt[counter][0]= "\u20AC " + opeC;
    	        counter++;
    	    }
    	    
		} catch (SQLException ex) {
	    // handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	    try {
    		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
    	    stmt = conn.createStatement();
    	    String cSta = "close";
    	        	    
    	    rs = stmt.executeQuery("SELECT COUNT(ID_Ticket) FROM `Ticketing`.`Ticket` WHERE `status`='"+cSta+"'; "); 
    	    int counter = 0;
    	    
    	    while(rs.next()){
    	    	String cls = rs.getString("COUNT(ID_Ticket)");
    	    	int clC = rs.getInt("COUNT(ID_Ticket)") * 50;
    	    	String clsC = Integer.toString(clC);
    	       	datac[counter][2] = cls;
    	       	dataCt[counter][1] = "\u20AC " + clsC;
    	        counter++;
    	    }
    	    
		} catch (SQLException ex) {
	    // handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	    
	    
		JPanel centreTo = new JPanel();
		centreTo.setLayout(new FlowLayout());
		
		JTable costT = new JTable(datac, colNamesc);
	    JScrollPane scrPc = new JScrollPane(costT);
	    scrPc.setPreferredSize(new Dimension(450,70));
	    scrPc.setBorder(BorderFactory.createTitledBorder("Total Tickets"));

	    centreTo.add(scrPc);
	    centre.add(centreTo);
	    
	    JPanel centreDo = new JPanel();
		centreDo.setLayout(new FlowLayout());
		
		JTable costTc = new JTable(dataCt, colNamesCt);
	    JScrollPane scrPCt = new JScrollPane(costTc);
	    scrPCt.setPreferredSize(new Dimension(450,70));
	    scrPCt.setBorder(BorderFactory.createTitledBorder("Cost Tickets"));
	    centreDo.add(scrPCt);
	    centre.add(centreDo);

		main.add(centre, BorderLayout.CENTER);
		
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(30, 1, 6, 20));
		
		JButton logout = new JButton("Logout");
		logout.addActionListener(this);
		logout.setActionCommand("out");
		right.add(logout);
		
		mainF.add(right, BorderLayout.EAST);
		
		JPanel left = new JPanel();
		left.setLayout(new GridLayout(30,1,5,20));
		
		JButton refresh = new JButton("Refresh");
		refresh.addActionListener(this);
		refresh.setActionCommand("ref");
		left.add(refresh);
		
		JButton graph = new JButton("Costs");
		graph.addActionListener(this);
		graph.setActionCommand("gra");
		left.add(graph);
		
		JButton tickets = new JButton("Tickets");
		tickets.addActionListener(this);
		tickets.setActionCommand("tic");
		left.add(tickets);
		
		JButton dist = new JButton("Distribution");
		dist.addActionListener(this);
		dist.setActionCommand("dis");
		left.add(dist);
				
		mainF.add(left, BorderLayout.WEST);
		
		this.add(mainF);
		
		validate();
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub

		if(e.getActionCommand().equals("ref")){
			System.out.println("You will see modifications");
			this.setVisible(false);
			new Manager();
		}
		
		//action command to connect with tickets graphic
		if(e.getActionCommand().equals("tic")){
			this.setVisible(false);
			new mgerTicG();

		}   
		
		//action command to connect with distribution tickets
		if(e.getActionCommand().equals("dis")){
			this.setVisible(false);
			new mgerDistG();
		}
		
		//action command to connect with cost graphic
		if(e.getActionCommand().equals("gra")){
			this.setVisible(false);
			new mgerGraph();
		}
		 
		if(e.getActionCommand().equals("out")){
			int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to close the program?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	
		    	System.exit(0);
		    }
		
		}
		

	}
}
