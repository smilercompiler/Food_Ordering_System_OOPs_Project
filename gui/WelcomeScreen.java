package gui;

import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {

    public WelcomeScreen() {
        setTitle("Welcome to Food Ordering System");
        setSize(300, 250);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10)); 
        JLabel welcomeLabel = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Customizing the font

        // Create buttons for Admin and User Sign-In/Sign-Up
        JButton adminSignInButton = new JButton("Admin Sign-In");
        JButton signUpButton = new JButton("Sign Up");
        JButton signInButton = new JButton("Sign In");

        // Customize button sizes
        adminSignInButton.setPreferredSize(new Dimension(200, 40));
        signUpButton.setPreferredSize(new Dimension(200, 40));
        signInButton.setPreferredSize(new Dimension(200, 40));

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(welcomeLabel);
        panel.add(adminSignInButton);
        panel.add(signUpButton);
        panel.add(signInButton);

        add(panel);

        // Action for Admin Sign-In Button
        adminSignInButton.addActionListener(e -> {
            new AdminLoginGUI().setVisible(true); // Open Admin Login screen
            dispose(); // Close WelcomeScreen
        });

        // Action for Sign Up Button
        signUpButton.addActionListener(e -> {
            new SignUpPanel(this).setVisible(true); // Open Sign Up screen with this JFrame reference
            dispose(); // Close WelcomeScreen
        });

        // Action for Sign In Button
        signInButton.addActionListener(e -> {
            new LoginScreen().setVisible(true); // Open Sign In screen
            dispose(); // Close WelcomeScreen
        });
    }

    // Main method to launch the welcome screen
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomeScreen().setVisible(true));
    }
}
