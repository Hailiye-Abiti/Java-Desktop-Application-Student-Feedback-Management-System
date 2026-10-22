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

public class InstructorFeedback extends JFrame {

    // Class level variables for database access
    private JTextField txtInstName, txtCourse;
    private JTextArea txtComment;
    private List<ButtonGroup> buttonGroups = new ArrayList<>();

    public InstructorFeedback() {
        setTitle("WCU - Instructor Feedback Form");
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

        JLabel lblTitle = new JLabel("Instructor Feedback");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(550, 40, 400, 40);
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

        // Instructor Details Input
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false);
        infoPanel.add(new JLabel("Instructor Name: "));
        txtInstName = new JTextField(15);
        infoPanel.add(txtInstName);
        infoPanel.add(new JLabel("   Course Title: "));
        txtCourse = new JTextField(15);
        infoPanel.add(txtCourse);
        formContainer.add(infoPanel);
        
        formContainer.add(Box.createVerticalStrut(20));

        String[] questions = {
            "1. Punctuality ",
            "2. Clarity of Explanation ",
            "3. Mastery of Subject Matter ",
            "4. Encouraging Student Participation ",
            "5. Fairness in Grading ",
            "6. Use of Teaching Aids ",
            "7. Availability for Consultation ",
            "8. Covering the Course Syllabus ",
            "9. Providing Feedback on Assignments",
            "10. Overall Teaching Effectiveness "
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
        btnSubmit.setBackground(new Color(76, 175, 80)); // Green color for instructor
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        formContainer.add(btnSubmit);

        JScrollPane scrollPane = new JScrollPane(formContainer);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Actions
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
        
        String[] ratings = {"1 (Poor)", "2 (Fair)", "3 (Good)", "4 (Very Good)", "5 (Excellent)"};
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

    private void saveFeedbackToDatabase() {
        // --- 1. VALIDATION: Check if Name and Course are empty ---
        if (txtInstName.getText().trim().isEmpty() || txtCourse.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide BOTH Instructor Name and Course Title!", "Input Required", JOptionPane.WARNING_MESSAGE);
            return; // Stop here if empty
        }

        int totalScore = 0;
        int[] scores = new int[10];

        // --- 2. VALIDATION: Check if all questions are answered ---
        for (int i = 0; i < buttonGroups.size(); i++) {
            ButtonModel model = buttonGroups.get(i).getSelection();
            if (model == null) {
                JOptionPane.showMessageDialog(this, "Please answer all questions! Question #" + (i + 1) + " is missing.", "Incomplete Form", JOptionPane.WARNING_MESSAGE);
                return; // Stop here if any question is missing
            }
            int val = Integer.parseInt(model.getActionCommand());
            scores[i] = val;
            totalScore += val;
        }

        double average = totalScore / 10.0;
        String instructorInfo = "Instructor: " + txtInstName.getText() + " | Course: " + txtCourse.getText();

        // 3. Insert into Database
        try (Connection con = DBConnection.connect()) {
            String sql = "INSERT INTO feedbacks (submitted_by, category, target_name, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, average_rating, comment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, UserSession.loggedInStudent); 
            pst.setString(2, "Instructor");
            pst.setString(3, instructorInfo);
            
            for (int i = 0; i < 10; i++) {
                pst.setInt(i + 4, scores[i]);
            }
            
            pst.setDouble(14, average);
            pst.setString(15, txtComment.getText());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Instructor Feedback Submitted Successfully! Average Score: " + average);
            
            new StudentDashboard().setVisible(true);
            this.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InstructorFeedback().setVisible(true));
    }
}