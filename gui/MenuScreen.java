package gui;
import models.Cart;
import models.FoodItem;
import models.FoodMenu;
import services.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuScreen extends JFrame {
    private FoodMenu foodMenu;
    private JComboBox<String> filterComboBox;
    private JPanel foodPanel;
    private CartService cartService;
    private PaymentService paymentService;

    public MenuScreen(FoodMenu foodMenu) {
        this.foodMenu = foodMenu; // Receive the FoodMenu object from constructor
        setTitle("Food Menu");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        cartService = new CartService();
        paymentService = new PaymentService();
        setLayout(new BorderLayout());
        String[] filterOptions = {"all", "vegetarian", "gluten-free", "lactose-free", "nut-free"};
        filterComboBox = new JComboBox<>(filterOptions);
        filterComboBox.setSelectedIndex(0); // Set default to "All"

        // Create the food item panel
        foodPanel = new JPanel();
        foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));

        // Add filter JComboBox to the top
        JPanel filterPanel = new JPanel();
        filterPanel.add(new JLabel("Filter by:"));
        filterPanel.add(filterComboBox);
        add(filterPanel, BorderLayout.NORTH);

        // Add food items panel to the center
        JScrollPane scrollPane = new JScrollPane(foodPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Load the food items initially (without any filter)
        loadFoodItems(null);

        // Add action listener to update the filter when the dropdown changes
        filterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFilter = (String) filterComboBox.getSelectedItem();
                switch (selectedFilter) {
                    case "vegetarian":
                        loadFoodItems("vegetarian");
                        break;
                    case "gluten-free":
                        loadFoodItems("gluten-free");
                        break;
                    case "lactose-free":
                        loadFoodItems("lactose-free");
                        break;
                    case "nut-free":
                        loadFoodItems("nut-free");
                        break;
                    default:
                        loadFoodItems(null);  // Show all items
                        break;
                }
            }
        });

        // Add Cart and Payment buttons at the bottom
        JPanel bottomPanel = new JPanel();
        JButton viewCartButton = new JButton("View Cart");
        JButton checkoutButton = new JButton("Checkout");

        // View Cart button action
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCart();
            }
        });

        // Checkout button action
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });

        bottomPanel.add(viewCartButton);
        bottomPanel.add(checkoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Load food items based on selected filter
    private void loadFoodItems(String filter) {
        foodPanel.removeAll();  // Clear existing items

        List<FoodItem> filteredItems;
        if (filter == null) {
            filteredItems = foodMenu.getFoodItems();  // No filter applied, show all items
        } else {
            System.out.println("Selected filter: " + filter);
            filter = filter.trim().toLowerCase();  // normalize to lowercase

            // Apply the filter based on selected dietary preference
            switch (filter) {
                case "vegetarian":
                    filteredItems = foodMenu.getFilteredFoodItems(false, false, true, false);
                    break;
                case "gluten-free":
                    filteredItems = foodMenu.getFilteredFoodItems(false, false, false, true);
                    break;
                case "lactose-free":
                    filteredItems = foodMenu.getFilteredFoodItems(true, false, false, false);
                    break;
                case "nut-free":
                    filteredItems = foodMenu.getFilteredFoodItems(false, true, false, false);
                    break;
                default:
                    filteredItems = foodMenu.getFoodItems();
                    break;
            }
        }

        // Add the filtered food items to the panel with add-to-cart functionality
        for (FoodItem item : filteredItems) {
            JPanel foodItemPanel = new JPanel();
            JLabel itemLabel = new JLabel(item.getName() + " - ₹" + item.getPrice());
            JButton addButton = new JButton("Add to Cart");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cartService.addToCart(item);
                    JOptionPane.showMessageDialog(MenuScreen.this, item.getName() + " added to cart!");
                }
            });
            foodItemPanel.add(itemLabel);
            foodItemPanel.add(addButton);
            foodPanel.add(foodItemPanel);
        }

        foodPanel.revalidate();  // Refresh the panel to show the new items
        foodPanel.repaint();
    }

    // Show the cart contents
    private void showCart() {
        List<FoodItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!");
        } else {
            StringBuilder cartContents = new StringBuilder();
            for (FoodItem item : cartItems) {
                cartContents.append(item.getName()).append(" - ₹").append(item.getPrice()).append("\n");
            }
            cartContents.append("\nTotal: ₹").append(cartService.getCart().calculateTotal());
            JOptionPane.showMessageDialog(this, cartContents.toString(), "Your Cart", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void checkout() {
        List<FoodItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty! Add items to cart before checkout.");
        } else {
            Cart cart = cartService.getCart();
            int totalPrice = cart.calculateTotal();

            String cardNumber = JOptionPane.showInputDialog(this, "Enter your 16-digit card number:");
            if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
                JOptionPane.showMessageDialog(this, "Invalid card number! Payment cancelled.");
                return;
            }

            String cardExpiry = JOptionPane.showInputDialog(this, "Enter card expiry (MM/YY):");
            if (cardExpiry == null || !cardExpiry.matches("(0[1-9]|1[0-2])/\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Invalid expiry format! Payment cancelled.");
                return;
            }

            String cvv = JOptionPane.showInputDialog(this, "Enter 3-digit CVV:");
            if (cvv == null || !cvv.matches("\\d{3}")) {
                JOptionPane.showMessageDialog(this, "Invalid CVV! Payment cancelled.");
                return;
            }

            String paymentMethod = "Credit Card"; // you can later make this dynamic via dropdown
            boolean paymentSuccess = paymentService.processPayment(cart, paymentMethod);

            if (paymentSuccess) {
                JOptionPane.showMessageDialog(this, "Payment successful! ₹" + totalPrice + " has been paid.\nThank you for your order.");
                cartService.clearCart();
            } else {
                JOptionPane.showMessageDialog(this, "Payment failed! Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        // Example of creating FoodMenu and loading food items
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.loadFoodItemsFromFile("D:/FoodOrderingSystem/food_items.txt");  // Make sure the path is correct

        // Create and show the MenuScreen with the foodMenu
        SwingUtilities.invokeLater(() -> new MenuScreen(foodMenu).setVisible(true));
    }
}