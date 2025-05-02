package gui;

import javax.swing.*;
import models.FoodMenu;
import java.awt.*;

public class AdminLoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLoginGUI() {
        setTitle("Admin Login");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout and Components
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        // Add components to panel
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());  // Empty label to align the button
        panel.add(loginButton);

        add(panel);

        // Action for Login Button
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Hardcoded Admin Credentials
            if ("admin".equals(username) && "admin123".equals(password)) {
                // Successful login: Show confirmation and open Admin menu management screen
                JOptionPane.showMessageDialog(this, "Login successful!");

                // Create a new FoodMenu instance
                FoodMenu foodMenu = new FoodMenu();
                
                // Load food items from file if needed 
                foodMenu.loadFoodItemsFromFile("D:\\FoodOrderingSystem\\food_items.txt");

                // Pass the FoodMenu instance to AdminMenuManagementGUI
                new AdminMenuManagementGUI(foodMenu).setVisible(true);

                // Close the AdminLoginGUI screen
                dispose();
            } else {
                // Failed login attempt: Show error message
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Main method to launch the Admin Login GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminLoginGUI().setVisible(true));
    }
}

