/**
 *
 * @author Hailiye
 */
package Feedback.app;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginForm extends javax.swing.JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin, btnRegister, btnHome, btnAbout, btnContacts;

    public LoginForm() {
        initComponentsCustom();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
    }

    private void initComponentsCustom() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null); 
        getContentPane().setBackground(new Color(220, 220, 220)); 

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(153, 153, 153)); 
        headerPanel.setBounds(0, 0, 2000, 180);
        headerPanel.setLayout(null);

        JLabel lblLogo = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/image/Logo.png"));
            Image img = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) { lblLogo.setText("LOGO"); }
        lblLogo.setBounds(20, 10, 140, 140);
        headerPanel.add(lblLogo);

        JLabel lblWCU = new JLabel("Wachemo University");
        lblWCU.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblWCU.setForeground(Color.WHITE);
        lblWCU.setBounds(30, 150, 150, 20);
        headerPanel.add(lblWCU);

        btnHome = createTopBtn("Home", 700);
        btnAbout = createTopBtn("About", 820);
        btnContacts = createTopBtn("Contacts", 940);
        
        headerPanel.add(btnHome);
        headerPanel.add(btnAbout);
        headerPanel.add(btnContacts);
        
        getContentPane().add(headerPanel);

        JLabel lblWelcome = new JLabel("Welcome Back!", JLabel.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblWelcome.setBounds(500, 250, 300, 40);
        getContentPane().add(lblWelcome);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(480, 330, 100, 25);
        getContentPane().add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(580, 330, 150, 30);
        getContentPane().add(txtUser);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(480, 380, 100, 25);
        getContentPane().add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(580, 380, 150, 30);
        getContentPane().add(txtPass);

        btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(0, 153, 255)); 
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBounds(500, 430, 230, 35);
        getContentPane().add(btnLogin);

        JLabel lblNoAcc = new JLabel("Don't have an account?");
        lblNoAcc.setBounds(460, 480, 150, 25);
        getContentPane().add(lblNoAcc);

        btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(0, 153, 255));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBounds(610, 480, 100, 25);
        getContentPane().add(btnRegister);

        // --- ACTIONS ---
        btnLogin.addActionListener(e -> handleLogin());
        
        btnRegister.addActionListener(e -> {
            new RegisterForm().setVisible(true);
            this.dispose();
        });

        btnHome.addActionListener(e -> {
            new HomeFrame().setVisible(true);
            this.dispose();
        });
        
         btnContacts.addActionListener(e -> {
    new ContactFrame().setVisible(true); 
    this.dispose(); 
});
        //About
        btnAbout.addActionListener(e -> {
            new AboutFrame().setVisible(true);
            this.dispose();
        });
        
        
    }

    private JButton createTopBtn(String text, int x) {
        JButton btn = new JButton(text);
        btn.setBounds(x, 60, 100, 35);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return btn;
    }

    private void handleLogin() {
    String user = txtUser.getText();
    String pass = new String(txtPass.getPassword());

    if (user.isEmpty() || pass.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter both username and password!");
        return;
    }

    try {
        Connection con = DBConnection.connect();
        
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, user);
        pst.setString(2, pass);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            UserSession.loggedInStudent = rs.getString("full_name"); 
            new StudentDashboard().setVisible(true);
            this.dispose();
            String studentName = rs.getString("full_name"); 

            
            JOptionPane.showMessageDialog(this, "Login Successful! Welcome to WCU, " + studentName);

          
            StudentDashboard dashboard = new StudentDashboard();
            dashboard.setVisible(true);
            this.dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
    }
}

    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}