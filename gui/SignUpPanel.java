package gui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

public class SignUpPanel extends JFrame {

    public SignUpPanel(JFrame parent) {
        setTitle("Sign Up");
        setSize(350, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(9, 1, 10, 5));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton signUpButton = new JButton("Sign Up");

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(signUpButton);

        add(panel);

        // Action for Sign Up Button
        signUpButton.addActionListener(e -> {
            String name = nameField.getText();
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate email format
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save user data to file (e.g., users.txt)
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.dat", true))) {
                writer.write(username + "," + name + "," + email + "," + password);
                writer.newLine();
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                
                // Hide SignUpPanel and show LoginScreen
                parent.setVisible(true);  // Show the parent (welcome page) again
                dispose();  // Close the SignUpPanel
                
                // Open the login screen
                new LoginScreen().setVisible(true);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving user data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    public static void main(String[] args) {
        // You should call this from the Welcome Page
        JFrame welcomeFrame = new JFrame();
        welcomeFrame.setSize(350, 300);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setVisible(true);
        
        // Open SignUpPanel
        SwingUtilities.invokeLater(() -> new SignUpPanel(welcomeFrame).setVisible(true));
    }
}
