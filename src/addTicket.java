import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class addTicket extends JFrame implements ActionListener{
	
	long epochT;
	long epoch = Instant.now().getEpochSecond();
	Date todayDa = new Date(epoch*1000L);
	DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	/*TimeZone timeZone = TimeZone.getTimeZone("GMT");
	format.setTimeZone(timeZone);*/
	String formatted = format.format(todayDa);
	
	JTextField addDate = new JTextField(formatted);
	/*JTextField addSecu = new JTextField();
	JTextField addPass = new JTextField();
	
	JTextField addRole = new JTextField();*/
	String[] addTech = {"Select one...", "1-James", "2-John", "3-Johan"};
	JComboBox addTechS = new JComboBox(addTech);
	String aTech = "";
	
	String[] response = {"Select one...", "Urgent", "Normal", "Long Term"};
	JComboBox resp = new JComboBox(response);
	String aResp = "";
	
	JTextArea addDesc = new JTextArea();
	
	public addTicket(){
		//Structure for this wareframe
		super("Tech Support - " + TechSup.Technician);
		setSize(800,600);
		setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel welcome = new JPanel();
		JLabel welc = new JLabel("New Ticket");
		welcome.add(welc);
		panel.add(welcome, BorderLayout.NORTH);		
		
		JPanel west = new JPanel();
		west.setLayout(new GridLayout(12, 1, 6, 20));
		
		JLabel date = new JLabel("Date: ");
		west.add(date);
		addDate.setToolTipText("Type Date dd/mm/yyyy");
		west.add(addDate);	
		
		JLabel tech = new JLabel("Technician: ");
		west.add(tech);
		addTechS.setSelectedIndex(0);
		addTechS.addActionListener(this);
		west.add(addTechS);
		
		JLabel response = new JLabel("Response: ");
		west.add(response);
		resp.setSelectedIndex(0);
		resp.addActionListener(this);
		west.add(resp);
		
		panel.add(west, BorderLayout.WEST);
		
		JPanel centre = new JPanel();
		centre.setLayout(new BorderLayout());
		
		JPanel topc = new JPanel();
		topc.setLayout(new FlowLayout());
		JLabel description = new JLabel("Description:");		
		topc.add(description);
		centre.add(topc, BorderLayout.NORTH);
		
		JPanel centreC = new JPanel();
		centreC.setLayout(new GridLayout(2,1));
		addDesc.setToolTipText("Type issue here");
		centreC.add(addDesc);
		centre.add(centreC, BorderLayout.CENTER);
		
		panel.add(centre,BorderLayout.CENTER);
		
		JPanel right = new JPanel();
		right.setLayout(new GridLayout(12,1,8,20));
		
		JButton logout = new JButton("Logout");
		logout.addActionListener(this);
		logout.setActionCommand("out");
		right.add(logout);
		
		JButton back = new JButton("Go Back");
		back.addActionListener(this);
		back.setActionCommand("back");
		right.add(back);
		
		panel.add(right, BorderLayout.EAST);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		
		JButton create = new JButton("Create");
		create.addActionListener(this);
		create.setActionCommand("cre");
		bottom.add(create);
		
		panel.add(bottom, BorderLayout.SOUTH);
		
		this.add(panel);
		
		validate();
		repaint();
		
	}
	
	//method to convert the input date string to epoch time
	public long convertDate(){
		DateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateStr = addDate.getText();
		
		//to content any problem in the conversion
		try {
			Date date = formatter.parse(dateStr);
			System.out.println(date);
			System.out.println(formatter.format(date));
			epochT = date.getTime();
			System.out.println(epochT);
		} catch (ParseException e){
			e.printStackTrace();
		}
		return epochT;
	}
	
	//method to add ticket to DB
	public void addingTicket (){
		convertDate();
		//conecting to DB
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception e){}
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//inserting the ticket info into DB
		try{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
			stmt = conn.createStatement();
			
			String aDe = addDesc.getText();
			long aDa = epochT;
    	    String aRes = aResp;
    	    String aTec = aTech;
    	    
    	    if (stmt.execute("INSERT INTO `Ticketing`.`Ticket` (`Description`, `start_Date`, `response`, `ID_TechSup`) VALUES ('"+aDe+"', '"+aDa+"', '"+aRes+"', '"+aTec+"');")) {
    	    	
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
		
		
		if (e.getActionCommand().equals("cre")){
			int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to create a New Ticket?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	System.out.println("Has been added a new ticket");
		    	addingTicket();
		    }else if (n!=0){
		    	this.setVisible(false);
		    	new addTicket();
		    }
		}
		
		if(e.getActionCommand().equals("out")){
			System.out.println("See You Next time");
			int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to leave your session?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	System.exit(0);
		    }

		}
		
		//action command for JComboBox TechSupport
		if (e.getSource() == addTechS){
			JComboBox aT = (JComboBox)e.getSource();
			String tech = (String)aT.getSelectedItem();
			
			switch (tech){
				case "1-James": aTech = "1";
					System.out.println("It is: " + aTech);
					break;
				case "2-John": aTech = "2";
					System.out.println("It is: " + aTech);
					break;
				case "3-Johan" : aTech = "3";
				    System.out.println("It is: " + aTech);
				    break;
		    }
		}
		
		//action command for JComboBox response
		if (e.getSource() == resp){
			JComboBox aR = (JComboBox)e.getSource();
			String res = (String)aR.getSelectedItem();
			
			switch (res){
				case "Urgent": aResp = "Urgent";
					System.out.println("It is: " + aResp);
					break;
				case "Normal": aResp = "Normal";
					System.out.println("It is: " + aResp);
					break;
				case "Long Term" : aResp = "Long Term";
				    System.out.println("It is: " + aResp);
				    break;
		    }
		}
		
		if(e.getActionCommand().equals("back")){
			
			this.setVisible(false);
			new TechSup();
		
	    }
	}

}
