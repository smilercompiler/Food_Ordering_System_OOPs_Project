package gui;
import models.User;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import models.*;  // Make sure this is from java.util package

public class LoginPanel extends JPanel {
    private FoodMenu foodMenu;

    public LoginPanel(JFrame frame, FoodMenu foodMenu) {
        this.foodMenu = foodMenu;
        setLayout(new GridLayout(3, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel());
        add(loginButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (isValidUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(LoginPanel.this); // Get the parent frame (LoginScreen)
                parentFrame.dispose(); // Close the login screen
                new MenuScreen(foodMenu).setVisible(true); // Open the menu screen
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
            }
        });
    }

    private boolean isValidUser(String username, String password) {
        File file = new File("data/users.dat"); // Ensure correct path for users.dat
        if (!file.exists()) {
            return false;  // No users file found
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();

            if (obj instanceof List<?>) {
                List<?> users = (List<?>) obj; // Cast the object to a List of User objects
                for (Object o : users) {
                    if (o instanceof User) {
                        User user = (User) o;
                        if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                            return true;  // If a match is found, return true
                        }
                    }
                }
            } else {
                System.err.println("Error: users.dat does not contain a List of users.");
                return false;  // If file content is not a valid List of Users
            }
        } catch (EOFException e) {
            // EOFException happens when the file is empty. Handle it gracefully.
            System.out.println("No users found in the file.");
            return false;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;  // If any other error occurs
        }

        return false;  // No valid user found in the file
    }
}

