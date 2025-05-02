package gui;

import javax.swing.*;
import javax.swing.table.*;
import models.FoodItem;
import models.FoodMenu;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AdminMenuManagementGUI extends JFrame {
    private FoodMenu foodMenu;
    private JTable table;
    private DefaultTableModel model;

    public AdminMenuManagementGUI(FoodMenu foodMenu) {
        this.foodMenu = foodMenu;

        setTitle("Admin Menu Management");
        setSize(700, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"Food Name", "Price", "Dietary Type", "Actions"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only actions column is editable
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);
        populateTable();

        // Render buttons in "Actions" column
        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addNewItemButton = new JButton("Add New Item");
        addNewItemButton.addActionListener(e -> {
            String filePath = "D:/FoodOrderingSystem/food_items.txt"; // Provide the correct file path here
            new AddFoodItemDialog(this, foodMenu, filePath).setVisible(true);
            refreshTable();
        });
        panel.add(addNewItemButton, BorderLayout.SOUTH);

        add(panel);
    }

    private void populateTable() {
        model.setRowCount(0);
        for (FoodItem item : foodMenu.getFoodItems()) {
            model.addRow(new Object[]{
                    item.getName(),
                    "₹" + item.getPrice(),
                    item.getDietaryFilterSummary(),
                    "Edit | Remove"
            });
        }
    }

    private void refreshTable() {
        populateTable();
    }

    // ===== Custom ButtonRenderer =====
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton editButton = new JButton("Edit");
        private final JButton removeButton = new JButton("Remove");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            add(editButton);
            add(removeButton);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            return this;
        }
    }

    // ===== Custom ButtonEditor =====
    class ButtonEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton editButton;
        private JButton removeButton;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            editButton = new JButton("Edit");
            removeButton = new JButton("Remove");

            panel.add(editButton);
            panel.add(removeButton);

            editButton.addActionListener(e -> {
                int row = table.getSelectedRow();
                FoodItem item = foodMenu.getFoodItems().get(row);
                String filePath = "D:/FoodOrderingSystem/food_items.txt"; // Provide the correct file path here
                new AddFoodItemDialog(AdminMenuManagementGUI.this, foodMenu, filePath, item).setVisible(true);
                refreshTable();
            });

            removeButton.addActionListener(e -> {
                int row = table.getSelectedRow();
                FoodItem item = foodMenu.getFoodItems().get(row);
                int confirm = JOptionPane.showConfirmDialog(AdminMenuManagementGUI.this,
                        "Are you sure you want to delete " + item.getName() + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    foodMenu.removeFoodItem(item);
                    refreshTable();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Edit | Remove";
        }
    }
}



    

