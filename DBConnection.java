package Feedback.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DBConnection {
    public static Connection connect() {
        try {
            // 1. Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Connect to MySQL Server (localhost)
            String baseUrl = "jdbc:mysql://localhost:3306/"; 
            String user = "root";
            String password = ""; 
            
            Connection con = DriverManager.getConnection(baseUrl, user, password);
            Statement stmt = con.createStatement();
            
            // 3. Create the Database if it doesn't exist
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS wcu_feedback_db");
            
            // 4. Switch to the database
            stmt.executeUpdate("USE wcu_feedback_db");
            
            // 5. Create 'users' table
            String usersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "full_name VARCHAR(100), "
                    + "id_no VARCHAR(50) UNIQUE, "
                    + "phone VARCHAR(20), "
                    + "college VARCHAR(100), "
                    + "department VARCHAR(100), "
                    + "username VARCHAR(50) UNIQUE, "
                    + "password VARCHAR(50))";
            
            // 6. Create 'feedbacks' table with all 10 question columns
            String feedbackTable = "CREATE TABLE IF NOT EXISTS feedbacks ("
                    + "f_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "submitted_by VARCHAR(100), " 
                    + "category VARCHAR(50), "     // Instructor, Cafeteria, or Dorm
                    + "target_name VARCHAR(100), " 
                    + "q1 INT, q2 INT, q3 INT, q4 INT, q5 INT, "
                    + "q6 INT, q7 INT, q8 INT, q9 INT, q10 INT, "
                    + "average_rating DECIMAL(3,2), "
                    + "comment TEXT, "
                    + "submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            
            // Execute table creation
            stmt.executeUpdate(usersTable);
            stmt.executeUpdate(feedbackTable);
            
            System.out.println("WCU Feedback Database and Tables are Ready!");
            return con;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Connection Error: " + e.getMessage());
            return null;
        }
    }
}