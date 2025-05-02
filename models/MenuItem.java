package models;

import java.util.List;
class MenuItem {
    private String name;
    private int price;
    private List<String> allergens;

    public MenuItem(String name, int price, List<String> allergens) {
        this.name = name;
        this.price = price;
        this.allergens = allergens;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public List<String> getAllergens() { return allergens; }

    public String toString() {
        return name + " - Rs." + price;
    }
}


