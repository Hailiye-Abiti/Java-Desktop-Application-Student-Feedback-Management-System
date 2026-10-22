/**
 *
 * @author Hailiye
 */
package Feedback.app;

import javax.swing.*;
import java.awt.*;

public class ContactFrame extends javax.swing.JFrame {

    public ContactFrame() {
        initComponentsCustom();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setLocationRelativeTo(null);
       
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
    }

    private void initComponentsCustom() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // --- HEADER PANEL ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(153, 153, 153)); 
        headerPanel.setPreferredSize(new Dimension(1300, 180));
        headerPanel.setLayout(null);

        // Logo
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

        // Header Buttons
        JButton btnHome = createTopBtn("Home", 720);
        JButton btnAbout = createTopBtn("About", 840);
        JButton btnLogin = createTopBtn("Log in", 960);
        
        headerPanel.add(btnHome);
        headerPanel.add(btnAbout);
        headerPanel.add(btnLogin);
        
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // --- MAIN CONTENT PANEL ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(new Color(220, 220, 220)); 

        JLabel lblTitle = new JLabel("Contact Information");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBounds(150, 50, 300, 40);
        contentPanel.add(lblTitle);

        JLabel lblSub = new JLabel("<html>For any inquiries regarding the Student Feedback System or general university matters,<br>please contact the respective offices listed below:</html>");
        lblSub.setBounds(150, 100, 800, 50);
        contentPanel.add(lblSub);

        // WCU Inquiry
        JLabel lblGen = new JLabel("Wachemo University General Inquiry:");
        lblGen.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblGen.setBounds(150, 170, 400, 30);
        contentPanel.add(lblGen);

        JButton btnMail1 = new JButton("Email : mailto:info@wcu.edu.et");
        btnMail1.setBounds(230, 210, 200, 30);
        contentPanel.add(btnMail1);

        // Registrar
        JLabel lblReg = new JLabel("Registrar Office:");
        lblReg.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblReg.setBounds(150, 260, 400, 30);
        contentPanel.add(lblReg);

        JButton btnMail2 = new JButton("Email : mailto:registrarpos@wcu.edu.et");
        btnMail2.setBounds(230, 300, 250, 30);
        contentPanel.add(btnMail2);

        // Developer
        JLabel lblDev = new JLabel("Developed by : Hailiye Abitl Admasu");
        lblDev.setBounds(150, 380, 400, 30);
        contentPanel.add(lblDev);
        
        JButton btnMail3 = new JButton("Email : hailiyeabiti@gmail.com");
        btnMail3.setBounds(150, 420, 400, 30);
        contentPanel.add(btnMail3);


        getContentPane().add(contentPanel, BorderLayout.CENTER);

        // ACTIONS
        btnHome.addActionListener(e -> { new HomeFrame().setVisible(true); this.dispose(); });
        btnAbout.addActionListener(e -> { new AboutFrame().setVisible(true); this.dispose(); });
        btnLogin.addActionListener(e -> { new LoginForm().setVisible(true); this.dispose(); });
    }

    private JButton createTopBtn(String text, int x) {
        JButton btn = new JButton(text);
        btn.setBounds(x, 60, 100, 35);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return btn;
    }
}