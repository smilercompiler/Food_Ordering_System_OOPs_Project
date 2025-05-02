package models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<FoodItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(FoodItem item) {
        items.add(item);
    }

    public List<FoodItem> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public int calculateTotal() {
        int total = 0;
        for (FoodItem item : items) {
            total += item.getPrice(); 
        }
        return total;
    }
}
