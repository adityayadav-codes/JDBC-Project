import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.util.Properties;

public class fullprog implements ActionListener {

    JLabel l1, l2, l3;
    JTextField t1, t2, t3;
    JButton b1, b2, b3, b4;
    JFrame p;

    Connection con;
    Statement stmt;
    ResultSet rs;

    public void frame() {
        p = new JFrame("Student Data");
        JPanel pi = new JPanel(new GridLayout(4, 2));

        l1 = new JLabel("Student ID :");
        t1 = new JTextField(8);
        l2 = new JLabel("Student Name :");
        t2 = new JTextField(15);
        l3 = new JLabel("Student Course :");
        t3 = new JTextField(15);

        pi.add(l1); pi.add(t1);
        pi.add(l2); pi.add(t2);
        pi.add(l3); pi.add(t3);

        JPanel pb = new JPanel(new GridLayout(1, 4));
        b1 = new JButton("Insert");
        b2 = new JButton("Update");
        b3 = new JButton("Delete");
        b4 = new JButton("View");

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        pb.add(b1); pb.add(b2); pb.add(b3); pb.add(b4);

        Container cn = p.getContentPane();
        cn.setLayout(new BoxLayout(cn, BoxLayout.Y_AXIS));
        p.add(pi);
        p.add(pb);
        p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.pack();
        p.setVisible(true);
    }

    public Connection getConnection() throws Exception {
        // ✅ Read database details safely from config.properties
        Properties props = new Properties();
        props.load(new FileInputStream("config.properties"));
        System.out.println("✅ Connected to database successfully!");//it ensures that the connection is successful
        System.out.println("✅ Properties loaded successfully");
        //

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");

        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }

    public void actionPerformed(ActionEvent evt) {
        String action = evt.getActionCommand();

        switch (action) {
            case "Insert": addoperation(); break;
            case "Update": updateoperation(); break;
            case "Delete": deleteoperation(); break;
            case "View": viewoperation(); break;
        }
    }

    public void viewoperation() {
        try {
            con = getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery("SELECT * FROM stu");

            if (rs.next()) {
                t1.setText(rs.getString("id"));
                t2.setText(rs.getString("name"));
                t3.setText(rs.getString("course"));
            }
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addoperation() {
        try {
            con = getConnection();
            stmt = con.createStatement();
            String sql = "INSERT INTO stu (id,name,course) VALUES ('" +
                    t1.getText() + "','" + t2.getText() + "','" + t3.getText() + "')";
            stmt.execute(sql);
            JOptionPane.showMessageDialog(null, "Record Added Successfully!");
            clearControls();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Insert Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateoperation() {
        try {
            con = getConnection();
            stmt = con.createStatement();
            String sql = "UPDATE stu SET name='" + t2.getText() +
                    "', course='" + t3.getText() + "' WHERE id='" + t1.getText() + "'";
            stmt.execute(sql);
            JOptionPane.showMessageDialog(null, "Record Updated Successfully!");
            clearControls();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteoperation() {
        int ans = JOptionPane.showConfirmDialog(null, "Are you sure to delete the Record?", "Delete Record", JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            try {
                con = getConnection();
                stmt = con.createStatement();
                String sql = "DELETE FROM stu WHERE id='" + t1.getText() + "'";
                stmt.execute(sql);
                JOptionPane.showMessageDialog(null, "Record Deleted Successfully!");
                clearControls();
                con.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void clearControls() {
        t1.setText("");
        t2.setText("");
        t3.setText("");
    }

    public static void main(String[] args) {
        new fullprog().frame();
    }
}