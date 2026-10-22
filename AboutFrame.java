/**
 *
 * @author Hailiye
 */
package Feedback.app;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends javax.swing.JFrame {

    public AboutFrame() {
        initComponentsCustom();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setLocationRelativeTo(null);
    }

    private void initComponentsCustom() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // --- TOP PANEL (HEADER) ---
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
        JButton btnHome = createTopBtn("Home", 600);
        JButton btnContacts = createTopBtn("Contacts", 750);
        JButton btnLogin = createTopBtn("Log in", 900);
        
        headerPanel.add(btnHome);
        headerPanel.add(btnContacts);
        headerPanel.add(btnLogin);
        
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // --- CONTENT PANEL (TEXTS) ---
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // About Title
        contentPanel.add(createStyledLabel("About Wachemo University", true));
        contentPanel.add(createTextArea("To be the leading comprehensive sector university in Ethiopia in academic excellence, research and innovation, and community service by 2030. The university has been working hard to achieve its mission of teaching learning, research and community service, and it has continued producing qualified and productive manpower.\nTo this end, the university focuses on academic quality, equity, and fairness.\n\nWachemo University has three campuses: Main campus, Durame campus and Nigist Eleni Mohamed Memorial Comprehensive Specialized Hospital. It has also seven agricultural and technology transfer centers, and seven teaching and centers."));

        // Vision Section
        contentPanel.add(createStyledLabel("Vision", true));
        contentPanel.add(createTextArea("To be the leading comprehensive sector university in Ethiopia in academic excellence, research and innovation, and community service by 2030."));

        // Mission Section
        contentPanel.add(createStyledLabel("Mission", true));
        contentPanel.add(createTextArea("To deliver proficient Graduates equipped with knowledge, skill and attitude by providing relevant and quality education; produce new knowledge and technology by conducting problem solving research; transform community livelihoods by strengthening community-industry partnership, technology transfer and advisory, training healthy services; enhance sustainable institutional capacity and integrated leadership."));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Actions
        btnContacts.addActionListener(e -> {new ContactFrame().setVisible(true); this.dispose(); });
        btnHome.addActionListener(e -> { new HomeFrame().setVisible(true); this.dispose(); });
        btnLogin.addActionListener(e -> { new LoginForm().setVisible(true); this.dispose(); });
    }

    private JButton createTopBtn(String text, int x) {
        JButton btn = new JButton(text);
        btn.setBounds(x, 60, 110, 35);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return btn;
    }

    private JLabel createStyledLabel(String text, boolean isHeader) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        if (isHeader) {
            label.setOpaque(true);
            label.setBackground(new Color(230, 230, 230));
        }
        return label;
    }

    private JTextArea createTextArea(String text) {
        JTextArea area = new JTextArea(text);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setBorder(BorderFactory.createEmptyBorder(5, 10, 15, 10));
        return area;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> { new AboutFrame().setVisible(true); });
    }
}