package models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.MenuScreen;

public class FoodMenu {
    private List<FoodItem> foodItems;

    // Constructor
    public FoodMenu() {
        this.foodItems = new ArrayList<>();
    }

public void loadFoodItemsFromFile(String filePath) {
    foodItems.clear(); 

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(","); 
            if (parts.length == 7) { 
                String name = parts[0].trim(); 
                String description = parts[1].trim();
                double price = Double.parseDouble(parts[2].trim());
                boolean isVegetarian = Boolean.parseBoolean(parts[3].trim());
                boolean isGlutenFree = Boolean.parseBoolean(parts[4].trim());
                boolean isLactoseFree = Boolean.parseBoolean(parts[5].trim());
                boolean isNutFree = Boolean.parseBoolean(parts[6].trim());

                FoodItem item = new FoodItem(name, description, price,
                        isVegetarian, isGlutenFree, isLactoseFree, isNutFree);
                foodItems.add(item); // Add to list
            } else {
                System.out.println(" Invalid line format: " + line); 
            }
        }
        System.out.println("Total items loaded: " + foodItems.size()); 
    } catch (IOException e) {
        System.out.println("Error loading food items: " + e.getMessage());
    }
}



    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

  
    public void addFoodItem(FoodItem item) {
        foodItems.add(item);
        saveFoodItemsToFile("food_items.txt"); 
    }

    
    public void removeFoodItem(FoodItem item) {
        foodItems.remove(item);
        saveFoodItemsToFile("food_items.txt"); 
    }

    
    public void editFoodItem(String name, String description, double price,
                             boolean isVegetarian, boolean isGlutenFree, boolean isLactoseFree, boolean isNutFree) {
        for (FoodItem item : foodItems) {
            if (item.getName().equals(name)) {
                item.setDescription(description);
                item.setPrice(price);
                item.setVegetarian(isVegetarian);
                item.setGlutenFree(isGlutenFree);
                item.setLactoseFree(isLactoseFree);
                item.setNutFree(isNutFree);
                break;
            }
        }
        saveFoodItemsToFile("food_items.txt"); 
    }

    
    public List<FoodItem> getFilteredFoodItems(boolean lactoseFree, boolean nutFree, boolean vegetarian, boolean glutenFree) {
        List<FoodItem> filteredItems = new ArrayList<>();
        
        
        for (FoodItem item : foodItems) {
            boolean matchesFilter = true;  
            
            
            System.out.println("Checking item: " + item.getName());
            System.out.println("Lactose Free: " + item.isLactoseFree() + " | Nut Free: " + item.isNutFree() + 
                               " | Vegetarian: " + item.isVegetarian() + " | Gluten Free: " + item.isGlutenFree());
    
           
            System.out.println("Filter conditions - Lactose Free: " + lactoseFree + " | Nut Free: " + nutFree + 
                               " | Vegetarian: " + vegetarian + " | Gluten Free: " + glutenFree);
    
            
            if (lactoseFree && !item.isLactoseFree()) {
                matchesFilter = false; 
                System.out.println("Excluding " + item.getName() + " due to Lactose-Free filter.");
            }
            if (nutFree && !item.isNutFree()) {
                matchesFilter = false;
                System.out.println("Excluding " + item.getName() + " due to Nut-Free filter.");
            }
            if (vegetarian && !item.isVegetarian()) {
                matchesFilter = false;
                System.out.println("Excluding " + item.getName() + " due to Vegetarian filter.");
            }
            if (glutenFree && !item.isGlutenFree()) {
                matchesFilter = false;
                System.out.println("Excluding " + item.getName() + " due to Gluten-Free filter.");
            }
    
          
            if (matchesFilter) {
                filteredItems.add(item);
                System.out.println("Including " + item.getName() + " in the filtered list.");
            }
        }
    
        
        System.out.println("Filtered items count: " + filteredItems.size());
        
        return filteredItems;
    }
    
    
    
    

   
    public void saveFoodItemsToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (FoodItem item : foodItems) {
                writer.write(item.getName() + "," +
                        item.getDescription() + "," +
                        item.getPrice() + "," +
                        item.isVegetarian() + "," +
                        item.isGlutenFree() + "," +
                        item.isLactoseFree() + "," +
                        item.isNutFree());
                writer.newLine();
            }
            System.out.println("Food items saved to file.");
        } catch (IOException e) {
            System.out.println(" Error saving food items: " + e.getMessage());
        }
    }
}



