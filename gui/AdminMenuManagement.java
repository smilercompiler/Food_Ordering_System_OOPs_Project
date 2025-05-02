package gui;

import models.FoodItem;
import models.FoodMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenuManagement extends JFrame {
    private FoodMenu foodMenu;
    private JTable foodTable;
    private JButton addButton, removeButton, editButton;

    public AdminMenuManagement() {
        foodMenu = new FoodMenu();
        foodMenu.loadFoodItemsFromFile("D:/FoodOrderingSystem/food_items.txt"); // Load items from file

        // Set up the JFrame
        setTitle("Admin Menu Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the table
        String[] columnNames = {"Name", "Price", "Dietary Filter"};
        Object[][] data = new Object[foodMenu.getFoodItems().size()][3];
        for (int i = 0; i < foodMenu.getFoodItems().size(); i++) {
            FoodItem item = foodMenu.getFoodItems().get(i);
            data[i] = new Object[]{item.getName(), item.getPrice(), item.getDietaryFilterSummary()};
        }
        foodTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(foodTable);

        // Set up buttons
        addButton = new JButton("Add Food");
        removeButton = new JButton("Remove Food");
        editButton = new JButton("Edit Food");

        JPanel panel = new JPanel();
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(editButton);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        // Action listeners for buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a dialog to add new food item
                String name = JOptionPane.showInputDialog("Enter food name:");
                double price = Double.parseDouble(JOptionPane.showInputDialog("Enter food price:"));
                String dietaryFilter = JOptionPane.showInputDialog("Enter dietary filter (comma separated, e.g., vegetarian, gluten-free):");

                // Parse dietary filter and update booleans
                boolean isVegetarian = dietaryFilter.contains("vegetarian");
                boolean isGlutenFree = dietaryFilter.contains("gluten-free");
                boolean isLactoseFree = dietaryFilter.contains("lactose-free");
                boolean isNutFree = dietaryFilter.contains("nut-free");

                foodMenu.addFoodItem(new FoodItem(name, "", price, isVegetarian, isGlutenFree, isLactoseFree, isNutFree));
                foodMenu.saveFoodItemsToFile("D:/FoodOrderingSystem/food_items.txt"); // Save after adding
                refreshTable();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = foodTable.getSelectedRow();
                if (selectedRow >= 0) {
                    FoodItem selectedItem = foodMenu.getFoodItems().get(selectedRow);
                    foodMenu.removeFoodItem(selectedItem);
                    foodMenu.saveFoodItemsToFile("D:/FoodOrderingSystem/food_items.txt"); // Save after removal
                    refreshTable();
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = foodTable.getSelectedRow();
                if (selectedRow >= 0) {
                    FoodItem selectedItem = foodMenu.getFoodItems().get(selectedRow);

                    // Edit name and price
                    String name = JOptionPane.showInputDialog("Edit food name:", selectedItem.getName());
                    double price = Double.parseDouble(JOptionPane.showInputDialog("Edit food price:", selectedItem.getPrice()));
                    String dietaryFilter = JOptionPane.showInputDialog("Edit dietary filter:", selectedItem.getDietaryFilterSummary());

                    // Parse dietary filter and update booleans
                    boolean isVegetarian = dietaryFilter.contains("vegetarian");
                    boolean isGlutenFree = dietaryFilter.contains("gluten-free");
                    boolean isLactoseFree = dietaryFilter.contains("lactose-free");
                    boolean isNutFree = dietaryFilter.contains("nut-free");

                    // Set the updated values
                    selectedItem.setName(name);
                    selectedItem.setPrice(price);
                    selectedItem.setDietaryInfo(isVegetarian, isGlutenFree, isLactoseFree, isNutFree);

                    foodMenu.saveFoodItemsToFile("D:/FoodOrderingSystem/food_items.txt"); // Save after editing
                    refreshTable();
                }
            }
        });
    }

    private void refreshTable() {
        // Update the table with the latest food items
        String[] columnNames = {"Name", "Price", "Dietary Filter"};
        Object[][] data = new Object[foodMenu.getFoodItems().size()][3];
        for (int i = 0; i < foodMenu.getFoodItems().size(); i++) {
            FoodItem item = foodMenu.getFoodItems().get(i);
            data[i] = new Object[]{item.getName(), item.getPrice(), item.getDietaryFilterSummary()};
        }
        foodTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminMenuManagement().setVisible(true);
            }
        });
    }
}


