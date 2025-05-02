package gui;

import javax.swing.*;
import models.FoodItem;
import models.FoodMenu;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddFoodItemDialog extends JDialog {

    private FoodMenu foodMenu;
    private JTextField nameField, descriptionField, priceField;
    private JCheckBox vegetarianCheckBox, glutenFreeCheckBox, lactoseFreeCheckBox, nutFreeCheckBox;
    private JButton addButton;
    private FoodItem itemToEdit;

    // Constructor for adding a new food item
    public AddFoodItemDialog(JFrame parent, FoodMenu foodMenu, String filePath) {
        this(parent, foodMenu, filePath, null);  // Pass null for the new item case
    }

    // Constructor for editing an existing food item
    public AddFoodItemDialog(JFrame parent, FoodMenu foodMenu, String filePath, FoodItem itemToEdit) {
        super(parent, itemToEdit == null ? "Add New Food Item" : "Edit Food Item", true);
        this.foodMenu = foodMenu;
        this.itemToEdit = itemToEdit;

        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(20);
        descriptionField = new JTextField(20);
        priceField = new JTextField(20);
        vegetarianCheckBox = new JCheckBox("Vegetarian");
        glutenFreeCheckBox = new JCheckBox("Gluten-Free");
        lactoseFreeCheckBox = new JCheckBox("Lactose-Free");
        nutFreeCheckBox = new JCheckBox("Nut-Free");

        // If editing an existing food item, prefill the form with its details
        if (itemToEdit != null) {
            nameField.setText(itemToEdit.getName());
            descriptionField.setText(itemToEdit.getDescription());
            priceField.setText(String.valueOf(itemToEdit.getPrice()));
            vegetarianCheckBox.setSelected(itemToEdit.isVegetarian());
            glutenFreeCheckBox.setSelected(itemToEdit.isGlutenFree());
            lactoseFreeCheckBox.setSelected(itemToEdit.isLactoseFree());
            nutFreeCheckBox.setSelected(itemToEdit.isNutFree());
        }

        // Add components to the form panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Food Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        formPanel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Price (₹):"), gbc);
        gbc.gridx = 1;
        formPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel("Dietary Info:"), gbc);

        gbc.gridy++;
        JPanel checkBoxPanel = new JPanel(new GridLayout(2, 2));
        checkBoxPanel.add(vegetarianCheckBox);
        checkBoxPanel.add(glutenFreeCheckBox);
        checkBoxPanel.add(lactoseFreeCheckBox);
        checkBoxPanel.add(nutFreeCheckBox);
        formPanel.add(checkBoxPanel, gbc);

        addButton = new JButton(itemToEdit == null ? "Add Item" : "Update Item");
        gbc.gridy++;
        formPanel.add(addButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            try {
                if (itemToEdit == null) { // Adding a new item
                    FoodItem newItem = new FoodItem(
                            nameField.getText(),
                            descriptionField.getText(),
                            Double.parseDouble(priceField.getText()),
                            vegetarianCheckBox.isSelected(),
                            glutenFreeCheckBox.isSelected(),
                            lactoseFreeCheckBox.isSelected(),
                            nutFreeCheckBox.isSelected()
                    );
                    foodMenu.addFoodItem(newItem);
                } else { // Editing an existing item
                    itemToEdit.setName(nameField.getText());
                    itemToEdit.setDescription(descriptionField.getText());
                    itemToEdit.setPrice(Double.parseDouble(priceField.getText()));
                    itemToEdit.setVegetarian(vegetarianCheckBox.isSelected());
                    itemToEdit.setGlutenFree(glutenFreeCheckBox.isSelected());
                    itemToEdit.setLactoseFree(lactoseFreeCheckBox.isSelected());
                    itemToEdit.setNutFree(nutFreeCheckBox.isSelected());
                }

                // Save the updated menu to the file
                foodMenu.saveFoodItemsToFile(filePath); 

                JOptionPane.showMessageDialog(this, itemToEdit == null ? "Item added successfully!" : "Item updated successfully!");
                dispose(); // Close dialog
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

