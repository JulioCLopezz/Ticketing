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

public class ticList extends JFrame implements ActionListener{
	String[][] data = new String[100][7];
	String[] colNames = {"Ticket #", "Description", "Status", "Start", "Finish", "Response", "Technician"};
	JTextField iDticket = new JTextField();
	JTextField iCticket = new JTextField();
	long epochF = Instant.now().getEpochSecond();
	long stD;
	long clD;
	String startD;
	String closeD;
	
	//Constructor with structure of this Wareframe
	public ticList(){
		
		super("Tech Support - " + TechSup.Technician);
		setSize(900,500);
		setVisible(true);
		this.setLayout(new FlowLayout());
		
		JPanel mainF = new JPanel();
		mainF.setLayout(new BorderLayout());
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		mainF.add(main, BorderLayout.CENTER);
		
		JPanel mainTop = new JPanel();
		JLabel title = new JLabel("Ticket List");
		mainTop.add(title);
		main.add(mainTop, BorderLayout.NORTH);
		
		JPanel centre = new JPanel();
		centre.setLayout(new GridLayout(2,1));
		
		//conecting to DB
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception e){}
		
		Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	
    	//to bring th info from DB
    	try {
    		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");

    	    stmt = conn.createStatement();
    	    rs = stmt.executeQuery("SELECT * FROM Ticket t, User u WHERE t.ID_TechSup = u.ID;");
    	    
    	    int counter = 0;
    	    
    	    //creating the table to list the whole created tickets
    	    while(rs.next()){

    	    	String idt = rs.getString("ID_Ticket");
    	       
    	    	data[counter][0] = idt;
    	      
    	        String de = rs.getString("description");
    	    	data[counter][1] = de;    	      
    	        
    	        String sts = rs.getString("status");
    	    	data[counter][2] = sts;
    	    	
    	    	stD = rs.getLong("start_Date");
    	    	startingDate();
    	    	String st = startD;
    	    	data[counter][3] = st;    	      
    	        
    	    	clD = rs.getLong("close_Date");
    	    	closingDate();
    	        String fi = closeD;
    	    	data[counter][4] = fi;
    	    	
    	    	String re = rs.getString("response");
    	    	data[counter][5] = re;
    	    	
    	    	String ts = rs.getString("username");
    	    	data[counter][6] = ts;
    	        
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
		
		//Table structure
		JTable listT = new JTable(data, colNames);
	    JScrollPane scrP = new JScrollPane(listT);
	    scrP.setPreferredSize(new Dimension(500,350));
	    scrP.setBorder(BorderFactory.createTitledBorder("Tickets Information"));
	    centreBo.add(scrP);
	    centre.add(centreBo);
	    
	    //Table modifications
	    listT.getColumnModel().getColumn(0).setPreferredWidth(6);
	    listT.getColumnModel().getColumn(2).setPreferredWidth(10);
	    listT.getColumnModel().getColumn(3).setPreferredWidth(10);
	    listT.getColumnModel().getColumn(4).setPreferredWidth(10);
	    listT.getColumnModel().getColumn(5).setPreferredWidth(10);
	    listT.getColumnModel().getColumn(6).setPreferredWidth(10);
	    
	    
		main.add(centre, BorderLayout.CENTER);
		
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(16, 1, 6, 20));
		
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
		left.setLayout(new GridLayout(16, 1, 6, 20));
		
		JButton refresh = new JButton("Refresh List");
		refresh.addActionListener(this);
		refresh.setActionCommand("ref");
		left.add(refresh);
		
		JPanel del = new JPanel();
		del.setLayout(new GridLayout(1,2,5,5));
		JButton delete = new JButton("Delete Ticket #");
		delete.addActionListener(this);
		delete.setActionCommand("del");
		del.add(delete);
		
		iDticket.setToolTipText("Type ticket # to delete");
		del.add(iDticket);
		left.add(del);
		
		JPanel clo = new JPanel();
		clo.setLayout(new GridLayout(1,2,5,5));
		JButton close = new JButton("Close Ticket #");
		close.addActionListener(this);
		close.setActionCommand("cl");
		clo.add(close);
		
		iCticket.setToolTipText("Type ticket # to delete");
		clo.add(iCticket);
		left.add(clo);
		
		mainF.add(left, BorderLayout.WEST);
		
		this.add(mainF);
		
		validate();
		repaint();
	}
	
	public String startingDate(){
		long epochD= stD;
		Date date = new Date(epochD);
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		startD = format.format(date);
		
		return startD;
		
	}
	
	public String closingDate(){
		long epochD= clD;
		Date date = new Date(epochD*1000L);
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		if (epochD == 0){
			closeD = "Not closed";
		}else {
			closeD = format.format(date);
		}
		return closeD;
		
	}
	
	public void deleteTicket(){
		String iDTicket = iDticket.getText();
		
		//connecting to DB
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception e){}
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//Code to delete ticket from DB
		try{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
			stmt = conn.createStatement();
		
			String ID = iDTicket;
		if (stmt.execute("DELETE FROM `Ticketing`.`Ticket` WHERE `ID_Ticket`='"+ID+"';")) {
	    	
			}
	
	   
		} catch (SQLException ex) {
	    // handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	
	}
	
	//method to close ticket
	public void closeTicket(){
		String icTicket = iCticket.getText();
		
		//connecting to DB
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception e){}
	
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		//Updating closed tickets in DB
		try{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
			stmt = conn.createStatement();
		
			String ID = icTicket;
			long fDa = epochF;
	    	String mSta = "close";
		    String oSta = "open";	
		    
		    if (stmt.execute("UPDATE `Ticketing`.`Ticket` SET `status`='"+mSta+"', `close_Date`='"+fDa+"' WHERE `ID_Ticket`='"+ID+"' AND `status`='"+oSta+"';")) {
				
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

		if(e.getActionCommand().equals("ref")){
			System.out.println("You will see modifications");
			setVisible(false);
			new ticList();
		}
		
		//Action command for button delete that allow delete the ticket
		if(e.getActionCommand().equals("del")){
			System.out.println("You will delete this ticket");
			int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the ticket?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	deleteTicket();
		    	this.setVisible(false);
		    	new ticList();
		    }else{

		    }
		} 
		
		//Action command for Button close that allow close the ticket process
		 if(e.getActionCommand().equals("cl")){
			System.out.println("You will close this ticket");
			int n = JOptionPane.showConfirmDialog(this, "Are you sure that this issue is solved?",
				"confirmation", 
						JOptionPane.YES_NO_OPTION);
			if (n == 0){
			    closeTicket();
			    this.setVisible(false);
		    	new ticList();
			}else{

			}
		
		 }
		
		
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
		    	new TechSup();
		    }else{

		    }
	    }
	}


}
