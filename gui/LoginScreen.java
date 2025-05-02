package gui;

import models.FoodMenu;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class LoginScreen extends JFrame {
    private FoodMenu foodMenu;  // Declare foodMenu here

    public LoginScreen() {
        setTitle("Login");
        setSize(300, 250);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);

        
    loginButton.addActionListener(e -> {
        String inputUsername = usernameField.getText().trim();
        String inputPassword = new String(passwordField.getPassword()).trim();
    
        boolean isAuthenticated = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\FoodOrderingSystem\\users.dat"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String storedUsername = parts[0].trim();
                    String storedPassword = parts[3].trim(); // password is in the 4th position
    
                    if (storedUsername.equals(inputUsername) && storedPassword.equals(inputPassword)) {
                        isAuthenticated = true;
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error reading users.dat file.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return;
        }
    
        if (isAuthenticated) {
            foodMenu = new FoodMenu();
            foodMenu.loadFoodItemsFromFile("D:\\FoodOrderingSystem\\food_items.txt");
    
            new MenuScreen(foodMenu).setVisible(true);
            dispose(); // Close LoginScreen
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    });
    
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}

