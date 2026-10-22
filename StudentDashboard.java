/**
 *
 * @author Hailiye
 */

package Feedback.app;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends javax.swing.JFrame {

    public StudentDashboard() {
        setTitle("WCU - Student Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponentsCustom();
        this.setLocationRelativeTo(null);
    }

    private void initComponentsCustom() {
        getContentPane().setLayout(new BorderLayout());

        // TOP PANEL (HEADER) 
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(153, 153, 153)); 
        headerPanel.setPreferredSize(new Dimension(1300, 180));
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

        JButton btnLogout = new JButton("Log out");
        btnLogout.setBounds(1100, 70, 100, 35);
        headerPanel.add(btnLogout);
        
        JButton btnAbout = new JButton("About");
        btnAbout.setBounds(700, 70, 100, 35);
        headerPanel.add(btnAbout);
        
        JButton btnContact = new JButton("Contact");
        btnContact.setBounds(900, 70, 100, 35);
        headerPanel.add(btnContact);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // MAIN CONTENT PANEL 
        JPanel mainContent = new JPanel();
        mainContent.setLayout(null);
        mainContent.setBackground(new Color(240, 240, 240));

        JLabel lblTitle = new JLabel("Chose Category!");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setBounds(550, 20, 300, 40);
        mainContent.add(lblTitle);

       
        mainContent.add(createCategoryBtn("Instructor Feedback", 250, 100));
        mainContent.add(createCategoryBtn("Dormitory Feedback", 550, 100));
        mainContent.add(createCategoryBtn("Cafteria Feedback", 850, 100));
        mainContent.add(createCategoryBtn("View Feedback", 550, 300)); 

        getContentPane().add(mainContent, BorderLayout.CENTER);

        // Actions Header Buttons
        btnLogout.addActionListener(e -> {
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

    // Action 
    private JPanel createCategoryBtn(String text, int x, int y) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(x, y, 250, 150); 
        panel.setBackground(new Color(204, 204, 255)); 
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        
        //  Action 
        btn.addActionListener(e -> {
            if (text.equals("Cafteria Feedback")) {
                new CafeteriaFeedback().setVisible(true);
                this.dispose();
            } else if (text.equals("Instructor Feedback")) {
                new InstructorFeedback().setVisible(true); 
                this.dispose();
            } else if (text.equals("Dormitory Feedback")) {
                new DormitoryFeedback().setVisible(true); 
                this.dispose();
            }else if (text.equals("View Feedback")) {
                 new ViewFeedback().setVisible(true);
                this.dispose();
            }
        });

        panel.add(btn, BorderLayout.CENTER);
        return panel;
    }
}