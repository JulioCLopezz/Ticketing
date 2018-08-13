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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class mgerTicG extends JFrame implements ActionListener{
	
	String[][] datac = new String[1][3];
	String[] colNamesc = {"Total Tickets", "Total Open", "Total Closed"};
	int totT;
	int openT;
	int closeT;
	
	public mgerTicG(){
		
		super("Manager - " + Login.username.getText());
		setSize(1000,600);
		setVisible(true);
		this.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainF = new JPanel();
		mainF.setLayout(new BorderLayout());
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.setPreferredSize(new Dimension(700,650));
		
		JPanel mainTop = new JPanel();
		mainTop.setLayout(new FlowLayout());
		JLabel title = new JLabel("Management - Tickets");
		mainTop.add(title);
		main.add(mainTop, BorderLayout.NORTH);		
		
		JPanel centre = new JPanel();
		centre.setLayout(new GridLayout(3, 1, 5, 5));
			
		//start connection to database
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception e){}
		
		Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	//finish connection to database
    	
    	//start pulling info from DB
	    try {
    		conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
    	    stmt = conn.createStatement();
    	    
    	    String oSta = "open";
    	    
    	    rs = stmt.executeQuery("SELECT COUNT(ID_Ticket) FROM `Ticketing`.`Ticket` WHERE `status`='"+oSta+"'; "); 
    	    int counter = 0;
    	    
    	    while(rs.next()){
    	    	openT = rs.getInt("COUNT(ID_Ticket)");

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
    	    	closeT = rs.getInt("COUNT(ID_Ticket)");

    	        counter++;
    	    }
    	    
		} catch (SQLException ex) {
	    // handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    	
    	//finish pulling info from DB
    	
    	//JPanel for Graph
	    JPanel Top = new JPanel();
		Top.setLayout(new FlowLayout());
		
		//starting Graph method
		DefaultCategoryDataset ticT = new DefaultCategoryDataset();
		final String statusO = "Open";
		final String statusC = "Close";
		final int opeT = openT;
		final int cloT = closeT;
		
		ticT.addValue(opeT, statusO, "Tickets");
		ticT.addValue(cloT, statusC, "Tickets");
		
		JFreeChart cost = ChartFactory.createBarChart3D("Tickets", "Status", "Number of Tickets", ticT, PlotOrientation.VERTICAL, true, true, false);

		ChartPanel costS = new ChartPanel(cost);
		Top.add(costS);
	    centre.add(Top);

		main.add(centre, BorderLayout.CENTER);
		mainF.add(main, BorderLayout.CENTER);
		
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(30, 1, 6, 20));
		
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
		left.setLayout(new GridLayout(30,1,5,20));
		
		JButton refresh = new JButton("Refresh");
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

		if(e.getActionCommand().equals("ref")){
			System.out.println("You will see modifications");
			this.setVisible(false);
			new mgerTicG();
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
			    new Manager();
			}else{
			    new Manager();
			}
		}
	}
}
