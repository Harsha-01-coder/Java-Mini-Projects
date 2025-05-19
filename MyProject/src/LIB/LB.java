package LIB;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class LB extends JFrame {
	JButton add,display,borrow,returnb;
	JTextField t1,t2,t3;
	JTextArea t4;
	JLabel Title,Author,Id;
	Connection con;
	
	public LB() throws ClassNotFoundException, SQLException{
		
		setTitle("Library Management System");
		setLayout(new FlowLayout());
		
		Title= new JLabel("Title:");
		Author= new JLabel("Author:");
		Id= new JLabel("ID:");
		
		t1=new JTextField(15);
		t2=new JTextField(15);
		t3=new JTextField(5);
		t4=new JTextArea(10,40);
		
		
		add= new JButton("ADD Book");
		display= new JButton("View All Books");
		borrow= new JButton("Borrow Book");
		returnb= new JButton("Return Book");
	
		add(Title);
		add(t1);
		add(Author);
		add(t2);
		add(Id);
		add(t3);
		add(add);
		add(borrow);
		add(returnb);
		add(display);
		add(new JScrollPane(t4));
		
		
		
		setVisible(true);
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		connectToDB();
			/*display.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
        					display();
    						}
						}); instead of above we use this e->*/
		
		display.addActionListener(e -> display());
        add.addActionListener(e -> add());
        borrow.addActionListener(e -> updateBookQuantity(-1));
        returnb.addActionListener(e -> updateBookQuantity(1));
		
	
		
	} 
	void connectToDB() throws ClassNotFoundException, SQLException {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con= DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/LibraryDB",
				"root","plmqaz999900A"
				);
		}
		 catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	void display() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            t4.setText("");
            while (rs.next()) {
                t4.append("ID: " + rs.getInt("id") +
                        ", Title: " + rs.getString("title") +
                        ", Author: " + rs.getString("author") +
                        ", Qty: " + rs.getInt("quantity") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	void add() {
        try {
            String sql = "INSERT INTO books (title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, t1.getText());
            ps.setString(2, t2.getText());
            ps.setInt(3, 1);  // Default quantity
            ps.executeUpdate();
            t4.setText("Book added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void updateBookQuantity(int change) {
        try {
            int id = Integer.parseInt(t3.getText());
            String checkSql = "SELECT quantity FROM books WHERE id = ?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, id);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                int qty = rs.getInt("quantity") + change;
                if (qty < 0) {
                    t4.setText("Book not available to borrow.");
                    return;
                }

                String updateSql = "UPDATE books SET quantity = ? WHERE id = ?";
                PreparedStatement updatePs = con.prepareStatement(updateSql);
                updatePs.setInt(1, qty);
                updatePs.setInt(2, id);
                updatePs.executeUpdate();

                t4.setText(change < 0 ? "Book borrowed." : "Book returned.");
            } else {
                t4.setText("Book ID not found.");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		new LB();
	}

}
