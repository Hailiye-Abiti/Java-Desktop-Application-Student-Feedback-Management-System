/**
 *
 * @author Hailiye
 */
package Feedback.app;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CafeteriaFeedback extends JFrame {

   
    private JTextField txtCafeteriaName;
    private JTextArea txtComment;
    private List<ButtonGroup> buttonGroups = new ArrayList<>();

    public CafeteriaFeedback() {
        setTitle("WCU - Cafeteria Feedback Form");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponentsCustom();
        this.setLocationRelativeTo(null);
    }

    private void initComponentsCustom() {
        getContentPane().setLayout(new BorderLayout());

        // --- TOP PANEL (HEADER) ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(153, 153, 153));
        headerPanel.setPreferredSize(new Dimension(1300, 150));
        headerPanel.setLayout(null);

        // Logo
        JLabel lblLogo = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/image/Logo.png"));
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) { lblLogo.setText("LOGO"); }
        lblLogo.setBounds(20, 5, 120, 120);
        headerPanel.add(lblLogo);

        JLabel lblTitle = new JLabel("Cafeteria Feedback");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(550, 40, 300, 40);
        headerPanel.add(lblTitle);

        // Back to Dashboard Button
        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.setBounds(1050, 50, 180, 35);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 12));
        headerPanel.add(btnBack);
        
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // --- MAIN CONTENT (SCROLLABLE FORM) ---
        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBackground(new Color(245, 245, 245));
        formContainer.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Cafeteria Name Input
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setOpaque(false);
        namePanel.add(new JLabel("Cafeteria Name (Social/Natural): "));
        txtCafeteriaName = new JTextField(20);
        namePanel.add(txtCafeteriaName);
        formContainer.add(namePanel);
        formContainer.add(Box.createVerticalStrut(20));

        // 10 Feedback Questions
        String[] questions = {
            "1. Food Quality",
            "2. Dining Hygiene",
            "3. Portion Sizes",
            "4. Service Speed",
            "5. Staff Politeness",
            "6. Water Availability",
            "7. Table and Chair Comfort",
            "8. Food Variety",
            "9. Hand Washing Area Neatness",
            "10. Overall Satisfaction"
        };

        for (String q : questions) {
            formContainer.add(createRatingPanel(q));
            formContainer.add(Box.createVerticalStrut(15));
        }

        // Additional Comments
        JLabel lblComment = new JLabel("Additional Comments:");
        lblComment.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblComment.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(lblComment);
        
        txtComment = new JTextArea(4, 50);
        txtComment.setLineWrap(true);
        JScrollPane commentScroll = new JScrollPane(txtComment);
        commentScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        formContainer.add(commentScroll);
        
        formContainer.add(Box.createVerticalStrut(20));
        JButton btnSubmit = new JButton("Submit Feedback");
        btnSubmit.setBackground(new Color(33, 150, 243));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        formContainer.add(btnSubmit);

        JScrollPane scrollPane = new JScrollPane(formContainer);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // --- ACTIONS ---
        btnBack.addActionListener(e -> {
            new StudentDashboard().setVisible(true);
            this.dispose();
        });

        btnSubmit.addActionListener(e -> {
            saveFeedbackToDatabase();
        });
    }

    private JPanel createRatingPanel(String questionText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblQ = new JLabel(questionText);
        lblQ.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblQ.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblQ);

        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        optionsPanel.setOpaque(false);
        optionsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ButtonGroup group = new ButtonGroup();
        
        String[] ratings = {"1 (Very Low)", "2 (Low)", "3 (Medium)", "4 (High)", "5 (Excellent)"};
        for (int i = 0; i < ratings.length; i++) {
            JRadioButton rb = new JRadioButton(ratings[i]);
            rb.setActionCommand(String.valueOf(i + 1)); 
            rb.setOpaque(false);
            group.add(rb);
            optionsPanel.add(rb);
        }
        
        buttonGroups.add(group); 
        panel.add(optionsPanel);
        return panel;
    }

    // AUTOMATIC CALCULATION AND SAVE 
    private void saveFeedbackToDatabase() {
        int totalScore = 0;
        int[] scores = new int[10];

        
        for (int i = 0; i < buttonGroups.size(); i++) {
            ButtonModel model = buttonGroups.get(i).getSelection();
            if (model == null) {
                JOptionPane.showMessageDialog(this, "Please answer Question #" + (i + 1));
                return;
            }
            int val = Integer.parseInt(model.getActionCommand());
            scores[i] = val;
            totalScore += val;
        }

        if (txtCafeteriaName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the Cafeteria Name.");
            return;
        }

        double average = totalScore / 10.0;

        try (Connection con = DBConnection.connect()) {
            String sql = "INSERT INTO feedbacks (submitted_by, category, target_name, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, average_rating, comment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, UserSession.loggedInStudent);
            pst.setString(2, "Cafeteria");
            pst.setString(3, txtCafeteriaName.getText());
            
            for (int i = 0; i < 10; i++) {
                pst.setInt(i + 4, scores[i]);
            }
            
            pst.setDouble(14, average);
            pst.setString(15, txtComment.getText());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cafeteria Feedback Submitted! Average Rating: " + average);
            
            new StudentDashboard().setVisible(true);
            this.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CafeteriaFeedback().setVisible(true));
    }
}