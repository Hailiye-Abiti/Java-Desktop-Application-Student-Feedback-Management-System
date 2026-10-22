/**
 *
 * @author Hailiye
 */

package Feedback.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeFrame extends JFrame {

    public HomeFrame() {
        setTitle("Wachemo University - Feedback System");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(null); 
        add(mainPanel);

        JPanel navBar = new JPanel();
        navBar.setBackground(new Color(153, 153, 153)); 
        navBar.setBounds(0, 0, 2000, 180); 
        navBar.setLayout(null);
        mainPanel.add(navBar);

        JLabel lblLogo = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/image/Logo.png"));
            Image img = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblLogo.setText("WCU LOGO"); 
        }
        lblLogo.setBounds(20, 10, 150, 150);
        navBar.add(lblLogo);

        JLabel lblUnivName = new JLabel("Wachemo University");
        lblUnivName.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblUnivName.setForeground(Color.WHITE);
        lblUnivName.setBounds(180, 60, 300, 40);
        navBar.add(lblUnivName);

        JButton btnLoginTop = createNavButton("Login", 850);
        JButton btnContact = createNavButton("Contact", 1000);
        JButton btnAbout = createNavButton("About", 1150);

        navBar.add(btnLoginTop);
        navBar.add(btnContact);
        navBar.add(btnAbout);
        
        JLabel lblMoto = new JLabel("<html>Your Voice Shapes<br>WCU's Future</html>");
        lblMoto.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblMoto.setBounds(50, 450, 400, 100);
        mainPanel.add(lblMoto);

        JButton btnGetStarted = new JButton("Get Started Now");
        btnGetStarted.setBackground(new Color(33, 150, 243)); 
        btnGetStarted.setForeground(Color.WHITE);
        btnGetStarted.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnGetStarted.setBounds(50, 580, 250, 50);
        btnGetStarted.setFocusPainted(false);
        mainPanel.add(btnGetStarted);

        // --- BUTTON ACTIONS ---
        btnGetStarted.addActionListener(e -> {
            new RegisterForm().setVisible(true);
            this.dispose();
        });

        btnLoginTop.addActionListener(e -> {
            new LoginForm().setVisible(true);
            this.dispose();
        });

       
        btnAbout.addActionListener(e -> {
            new AboutFrame().setVisible(true);
            this.dispose();
        });

        btnContact.addActionListener(e -> {
    new ContactFrame().setVisible(true); 
    this.dispose(); 
});
    }

    private JButton createNavButton(String text, int x) {
        JButton btn = new JButton(text);
        btn.setBounds(x, 60, 120, 40);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);
        return btn;
    }

    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                ImageIcon bgIcon = new ImageIcon(getClass().getResource("/image/park.jpg"));
                g.drawImage(bgIcon.getImage(), 0, 180, getWidth(), getHeight() - 180, this);
            } catch (Exception e) {
                g.setColor(new Color(220, 220, 220)); 
                g.fillRect(0, 180, getWidth(), getHeight());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HomeFrame().setVisible(true);
        });
    }
}