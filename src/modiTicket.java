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

public class modiTicket extends JFrame implements ActionListener{
	
	long epochT;
	long epoch = Instant.now().getEpochSecond();
	Date todayDa = new Date(epoch*1000L);
	DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	String formatted = format.format(todayDa);
	
	JTextField ticID = new JTextField();
	JTextField addDate = new JTextField(formatted);
	
	String[] modTec = {"Select one...", "1-James", "2-John", "3-Johan"};
	JComboBox modTechS = new JComboBox(modTec);
	String modTech = "";
	
	String[] response = {"Select one...", "Urgent", "Normal", "Long Term"};
	JComboBox resp = new JComboBox(response);
	String modResp = "";
	
	JTextArea modDesc = new JTextArea();
	
	public modiTicket(){
		super("Tech Support - " + TechSup.Technician);
		setSize(800,600);
		setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel welcome = new JPanel();
		JLabel welc = new JLabel("Modify Ticket");
		welcome.add(welc);
		panel.add(welcome, BorderLayout.NORTH);		
		
		//continues changing from here and on
		JPanel west = new JPanel();
		west.setLayout(new GridLayout(12, 1, 6, 20));
		
		JLabel ticketID = new JLabel("Ticket #: ");
		west.add(ticketID);
		ticID.setToolTipText("Type Date Ticket #");
		west.add(ticID);
		
		JLabel tech = new JLabel("Technician: ");
		west.add(tech);
		modTechS.setSelectedIndex(0);
		modTechS.addActionListener(this);
		west.add(modTechS);
		
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
		modDesc.setToolTipText("Type issue here");
		centreC.add(modDesc);
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
		
		JButton modify = new JButton("Modify");
		modify.addActionListener(this);
		modify.setActionCommand("mod");
		bottom.add(modify);
		
		panel.add(bottom, BorderLayout.SOUTH);
		
		this.add(panel);
		
		validate();
		repaint();
		
	}
	
	//converting date to epoch time
	public long convertDate(){
		DateFormat DateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateStr = addDate.getText();
		
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
	
	
	public void modifyingTicket (){
		convertDate();
		//connecting to DB
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		}catch(Exception e){}
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//updating info to DB
		try{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Ticketing?user=root&password=");
			stmt = conn.createStatement();
			
			String ID = ticID.getText();
			String mDe = modDesc.getText();
			//long aDa = epochT;
			//long fDa = epochF
    	    String mRes = modResp;
    	    String mTec = modTech;
    	    //String mSta = modStatus;
			    	        	    
			// until here because, from this point I have to add new password and update the data.,  `start_Date`='"+aDa+"', `status`='"+mSta+"'
    	    if (stmt.execute("UPDATE `Ticketing`.`Ticket` SET `Description`='"+mDe+"', `response`='"+mRes+"', `ID_TechSup`='"+mTec+"' WHERE `ID_Ticket`='"+ID+"';")) {
    	    	
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
		
		//action command to modify info
		if (e.getActionCommand().equals("mod")){
			int n = JOptionPane.showConfirmDialog(this, "Are you sure you want to modify this Ticket?",
					"confirmation", 
					JOptionPane.YES_NO_OPTION);
		    if (n == 0){
		    	System.out.println("Has been modifyed a ticket");
		    	modifyingTicket();
		    }else if (n!=0){
		    	this.setVisible(false);
		    	new modiTicket();
		    }
		}
		
		//Action command for JComboBox Tech Support
		if (e.getSource() == modTechS){
			JComboBox aT = (JComboBox)e.getSource();
			String tech = (String)aT.getSelectedItem();
			
			switch (tech){
				case "1-James": modTech = "1";
					System.out.println("It is: " + modTech);
					break;
				case "2-John": modTech = "2";
					System.out.println("It is: " + modTech);
					break;
				case "3-Johan" : modTech = "3";
				    System.out.println("It is: " + modTech);
				    break;
		    }
		}
		
		//Action command for JComboBox response
		if (e.getSource() == resp){
			JComboBox aR = (JComboBox)e.getSource();
			String res = (String)aR.getSelectedItem();
			
			switch (res){
				case "Urgent": modResp = "Urgent";
					System.out.println("It is: " + modResp);
					break;
				case "Normal": modResp = "Normal";
					System.out.println("It is: " + modResp);
					break;
				case "Long Term" : modResp = "Long Term";
				    System.out.println("It is: " + modResp);
				    break;
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
		
		if(e.getActionCommand().equals("back")){
			
			this.setVisible(false);
			new TechSup();
		
	    }
	}

}
