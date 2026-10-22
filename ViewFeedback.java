/**
 *
 * @author Hailiye
 */
package Feedback.app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewFeedback extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public ViewFeedback() {
        setTitle("WCU - View Submitted Feedbacks");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponentsCustom();
        this.setLocationRelativeTo(null);
        
        loadFeedbackFromDatabase(); 
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

        JLabel lblTitle = new JLabel("My Feedbacks History");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(500, 40, 400, 40);
        headerPanel.add(lblTitle);

        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.setBounds(1050, 50, 180, 35);
        headerPanel.add(btnBack);
        
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // --- MAIN CONTENT (TABLE) ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setBackground(new Color(240, 240, 240));

        // Table Columns 
        String[] columns = {"Category", "Target (Name/Block)", "Average Rating", "Comment", "Date Submitted"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //(Read Only)
            }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(70, 130, 180));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Actions
        btnBack.addActionListener(e -> {
            new StudentDashboard().setVisible(true);
            this.dispose();
        });
    }

   
    private void loadFeedbackFromDatabase() {
        tableModel.setRowCount(0);

        try (Connection con = DBConnection.connect()) {
            String sql = "SELECT category, target_name, average_rating, comment, submitted_at FROM feedbacks WHERE submitted_by = ? ORDER BY submitted_at DESC";
            
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, UserSession.loggedInStudent); // Login  username
            
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String category = rs.getString("category");
                String target = rs.getString("target_name");
                double avg = rs.getDouble("average_rating");
                String comment = rs.getString("comment");
                Timestamp date = rs.getTimestamp("submitted_at");

                // JTable 
                tableModel.addRow(new Object[]{
                    category, 
                    target, 
                    String.format("%.2f / 5", avg), 
                    comment, 
                    date
                });
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "You haven't submitted any feedback yet.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading history: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewFeedback().setVisible(true));
    }
}