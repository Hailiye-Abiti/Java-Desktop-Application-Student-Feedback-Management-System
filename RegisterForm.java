/**
 *
 * @author Hailiye
 */

package Feedback.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

public class RegisterForm extends javax.swing.JFrame {

    private JTextField txtFullName, txtID, txtPhone, txtUser;
    private JPasswordField txtPass, txtConfirmPass;
    private JComboBox<String> comboCollege, comboDept;
    private JButton btnRegister;

    public RegisterForm() {
        initComponentsCustom();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.setLocationRelativeTo(null);
    }

    private void initComponentsCustom() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // --- Header Panel (Kept Original Design) ---
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
        lblWCU.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblWCU.setForeground(Color.WHITE);
        lblWCU.setBounds(40, 150, 200, 30);
        headerPanel.add(lblWCU);

        headerPanel.add(createTopBtn("Home", 550, e -> { new HomeFrame().setVisible(true); this.dispose(); }));
        headerPanel.add(createTopBtn("Login", 650, e -> { new LoginForm().setVisible(true); this.dispose(); }));
        headerPanel.add(createTopBtn("About", 750, e -> { new AboutFrame().setVisible(true); this.dispose(); }));
        headerPanel.add(createTopBtn("Contact", 850, e -> { JOptionPane.showMessageDialog(this, "Contact: info@wcu.edu.et"); }));

        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // --- Center Panel ---
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        
        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(450, 550));
        formPanel.setLayout(new GridLayout(10, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder(null, "Student Registration", 2, 0, new Font("Segoe UI", 1, 18)));

        txtFullName = new JTextField();
        txtID = new JTextField(); 
        txtPhone = new JTextField();
        txtUser = new JTextField();
        txtPass = new JPasswordField();
        txtConfirmPass = new JPasswordField();

        // 1. Full Name: Allow only letters and spaces while typing
        txtFullName.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isLetter(c) || c == KeyEvent.VK_SPACE || c == KeyEvent.VK_BACK_SPACE)) {
                    e.consume(); 
                }
            }
        });

        // 2. ID Number: Force "WCU" prefix and max 10 chars
        txtID.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (txtID.getText().length() >= 10) e.consume();
            }
        });

        // 3. Phone Number: Allow only digits and max 10
        txtPhone.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || txtPhone.getText().length() >= 10) {
                    e.consume();
                }
            }
        });

        String[] colleges = {"Select College", 
         "Engineering and Technology",
            "Natural and Computational Sciences",
            "Medicine and Health Sciences",
            "Agricultural Sciences",
            "Business and Economics",
            "Social Sciences and Humanities",
            "Education & Behavioral Sciences",
        };
        comboCollege = new JComboBox<>(colleges);
        String[] depts = {"Select Department",
               "BSc. in Software Engineering",
         	"BSc. in Architecture",
                "BSc. in Chemical Engineering",
                "BSc. in Civil Engineering",
                "BSc. in Computer Science",
                "BSc. in Construction Technology and Management",
                "BSc. in Electro-Mechanical Engineering",
                "BSc. in Surveying Engineering",
                "BSc. in Information System(IS)",
                "BSc. in Information Technology(IT)",
                "BSc. in Mechanical Engineering",
                "BSc in Hydraulic and Water Resource Engineering",
                "BSc in Biomedical Engineering",
                "BSc. in Electrical and Computer Engineering",

        };
        comboDept = new JComboBox<>(depts);

        formPanel.add(new JLabel("Full Name:")); formPanel.add(txtFullName);
        formPanel.add(new JLabel("ID Number (WCU...):")); formPanel.add(txtID);
        formPanel.add(new JLabel("Phone (07/09...):")); formPanel.add(txtPhone);
        formPanel.add(new JLabel("College:")); formPanel.add(comboCollege);
        formPanel.add(new JLabel("Department:")); formPanel.add(comboDept);
        formPanel.add(new JLabel("Username:")); formPanel.add(txtUser);
        formPanel.add(new JLabel("Password (min 6):")); formPanel.add(txtPass);
        formPanel.add(new JLabel("Confirm Password:")); formPanel.add(txtConfirmPass);

        btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(102, 153, 255));
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.addActionListener(e -> handleRegistration());
        
        formPanel.add(new JLabel("")); 
        formPanel.add(btnRegister);

        centerPanel.add(formPanel);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
    }

    private JButton createTopBtn(String text, int x, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setBounds(x, 80, 90, 30);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.addActionListener(action);
        return btn;
    }

    private void handleRegistration() {
        String fullName = txtFullName.getText().trim();
        String idNo = txtID.getText().trim().toUpperCase(); // Normalize ID
        String phone = txtPhone.getText().trim();
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());
        String confirm = new String(txtConfirmPass.getPassword());

        // --- STRICT VALIDATIONS ---

        // 1. Full Name Validation
        if (fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full Name cannot be empty!");
            return;
        }

        // 2. ID Validation: Must start with WCU and be exactly 10 chars
        if (!idNo.startsWith("WCU") || idNo.length() != 10) {
            JOptionPane.showMessageDialog(this, "ID must start with 'WCU' and be exactly 10 characters!");
            return;
        }

        // 3. Phone Validation: Start with 07 or 09 and be exactly 10 digits
        if (!(phone.startsWith("07") || phone.startsWith("09")) || phone.length() != 10) {
            JOptionPane.showMessageDialog(this, "Phone must start with 07 or 09 and be exactly 10 digits!");
            return;
        }

        // 4. Password Validation: Minimum 6 characters
        if (pass.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters!");
            return;
        }

        if (!pass.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        if (comboCollege.getSelectedIndex() == 0 || comboDept.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select College and Department!");
            return;
        }

        // --- DATABASE UNIQUENESS CHECKS ---
        try (Connection con = DBConnection.connect()) {
            // Check if ID, Username, or Phone already exists
            String checkSql = "SELECT * FROM users WHERE username = ? OR phone = ? OR id_no = ?";
            PreparedStatement checkPst = con.prepareStatement(checkSql);
            checkPst.setString(1, user);
            checkPst.setString(2, phone);
            checkPst.setString(3, idNo);
            ResultSet rs = checkPst.executeQuery();
            
            if (rs.next()) {
                if (rs.getString("username").equalsIgnoreCase(user)) 
                    JOptionPane.showMessageDialog(this, "Username already taken!");
                else if (rs.getString("phone").equals(phone)) 
                    JOptionPane.showMessageDialog(this, "Phone number already registered!");
                else if (rs.getString("id_no").equalsIgnoreCase(idNo))
                    JOptionPane.showMessageDialog(this, "ID Number already exists!");
                return;
            }

            // Insert Data
            String sql = "INSERT INTO users (full_name, id_no, phone, college, department, username, password) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, fullName);
            pst.setString(2, idNo);
            pst.setString(3, phone);
            pst.setString(4, comboCollege.getSelectedItem().toString());
            pst.setString(5, comboDept.getSelectedItem().toString());
            pst.setString(6, user);
            pst.setString(7, pass);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Successfully Registered!");
            new LoginForm().setVisible(true);
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> { new RegisterForm().setVisible(true); });
    }
}