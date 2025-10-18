//Accessing the database by using the JDBC

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public class fullprog implements ActionListener 
{
	JLabel l1,l2,l3;
	JTextField t1,t2,t3;
	JButton b1,b2,b3,b4;
	JFrame p;
	Connection con;
     Statement stmt; 
     ResultSet rs;
public void frame()
{
p=new JFrame("Student Data");
JPanel pi = new JPanel(new GridLayout(4,2));
//labeling and adding to panel
l1=new JLabel("Student ID :");
t1=new JTextField(8);
l2=new JLabel("Student Name :");
t2=new JTextField(15);
l3=new JLabel("Student Course :");
t3=new JTextField(15);
pi.add(l1);
pi.add(t1);
pi.add(l2);
pi.add(t2);
pi.add(l3);
pi.add(t3);
//new grid for buttons
JPanel pb = new JPanel(new GridLayout(1,4));
b1 = new JButton("Insert");
b1.addActionListener(this);
b2 = new JButton("Update");
b2.addActionListener(this);
b3 = new JButton("Delete");
b3.addActionListener(this);
b4 = new JButton("View");
b4.addActionListener(this);
pb.add(b1);
pb.add(b2);
pb.add(b3);
pb.add(b4);
Container cn = p.getContentPane();
cn.setLayout(new BoxLayout(cn,BoxLayout.Y_AXIS));
        p.add(pi);
        p.add(pb);
      //If this will not be written, the only frame will be closed
     // but the application will be active.
        p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.pack();
	p.setVisible(true);
}
        public void actionPerformed(ActionEvent evt)
      {
       String action = evt.getActionCommand();
        if(action.equals("Insert"))
	        {
	            addoperation();
	        }else if(action.equals("Update"))
	        {
	            updateoperation();
	        }else if(action.equals("Delete"))
	        {
	            deleteoperation();

	        }else if(action.equals("View"))
	       {
	         viewo();
	        }
}
public void viewo(){
try
	   {
	if(rs == null){
	  //Load Jdbc Odbc Driver
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localHost:1521:xe","system","tiger");
	 
	      String sql = "SELECT * FROM stu";
Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
rs = stmt.executeQuery(sql);
	            }
	            if(rs.next())
	            {		 
	t1.setText(rs.getString("id")); 
        t2.setText(rs.getString("name"));
        t3.setText(rs.getString("course")); 

	            }
	            }catch(Exception a)
	            {
	                JOptionPane.showMessageDialog(null, a.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
}	            }   	
    public void addoperation()
    {
        try
     	 {            //Load Jdbc Odbc Driver

          Class.forName("oracle.jdbc.driver.OracleDriver");
  	 Connection  con=DriverManager.getConnection("jdbc:oracle:thin:@localHost:1521:xe","system","tiger");         
           String sql = "INSERT INTO stu (id,name,course) values('"+t1.getText()+"','"+t2.getText()+"','"+t3.getText()+"')";
          
          Statement stmt = con.createStatement();
          stmt.execute(sql);

      JOptionPane.showMessageDialog(null, "Data is successfully inserted into database.","Record Added", JOptionPane.INFORMATION_MESSAGE);
	clearControls();
       }catch(Exception e)
        {
           JOptionPane.showMessageDialog(null, e.getMessage(),"error in submitting data",JOptionPane.ERROR_MESSAGE);
        }
    }
public void updateoperation()
    {
try
	   {
	  //Load Jdbc Odbc Driver
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localHost:1521:xe","system","tiger");
	 
	      String sql = "Update stu SET name = '"+t2.getText()+"',course = '"+t3.getText()+"' Where id = '"+t1.getText()+"'";
        	Statement stmt = con.createStatement(); 
	    stmt.execute(sql);
	JOptionPane.showMessageDialog(null,"Data successfully Updated in database");
	clearControls();
	  }
	catch(Exception a){
	System.out.println(a);
	}
      }  
public void deleteoperation()
 {
	  int ans = JOptionPane.showConfirmDialog(null,"Are you sure to delete the Record ?", "Delete Record", JOptionPane.YES_NO_OPTION);
   	if(ans == JOptionPane.YES_OPTION){
        try
	   {
	            //Load Jdbc Odbc Driver
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	    Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localHost:1521:xe","system","tiger");
	 
	String sql="delete from stu where id='"+t1.getText()+"'";
	Statement stmt=con.createStatement();
	stmt.execute(sql);
	JOptionPane.showMessageDialog(null,"Data successfully Deleted from the database");}
	catch(Exception l){
	System.out.println(l);
	}}
        else
        {
	      JOptionPane.showMessageDialog(null, "Operation Canceled","Cancel",JOptionPane.INFORMATION_MESSAGE);	       
	 }
	clearControls();
	}

    public void clearControls()
    {
     t1.setText("");
     t2.setText("");
     t3.setText("");
}  	
         public static void main(String as[])
 	{
  	fullprog m=new fullprog();
	m.frame();
   	}
 }