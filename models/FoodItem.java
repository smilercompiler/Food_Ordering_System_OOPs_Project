package models;

public class FoodItem {
    private String name;
    private String description;
    private double price;
    private boolean isVegetarian;
    private boolean isGlutenFree;
    private boolean isLactoseFree;
    private boolean isNutFree;

    
    public FoodItem(String name, String description, double price, boolean isVegetarian,
                    boolean isGlutenFree, boolean isLactoseFree, boolean isNutFree) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isVegetarian = isVegetarian;
        this.isGlutenFree = isGlutenFree;
        this.isLactoseFree = isLactoseFree;
        this.isNutFree = isNutFree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean isVegetarian) {
        this.isVegetarian = isVegetarian;
    }

    public boolean isGlutenFree() {
        return isGlutenFree;
    }

    public void setGlutenFree(boolean isGlutenFree) {
        this.isGlutenFree = isGlutenFree;
    }

    public boolean isLactoseFree() {
        return isLactoseFree;
    }

    public void setLactoseFree(boolean isLactoseFree) {
        this.isLactoseFree = isLactoseFree;
    }

    public boolean isNutFree() {
        return isNutFree;
    }

    public void setNutFree(boolean isNutFree) {
        this.isNutFree = isNutFree;
    }

    
    public void setDietaryInfo(boolean isVegetarian, boolean isGlutenFree, boolean isLactoseFree, boolean isNutFree) {
        this.isVegetarian = isVegetarian;
        this.isGlutenFree = isGlutenFree;
        this.isLactoseFree = isLactoseFree;
        this.isNutFree = isNutFree;
    }

    // Get dietary filter summary as a string
    public String getDietaryFilterSummary() {
        StringBuilder dietarySummary = new StringBuilder();

        if (isVegetarian) {
            dietarySummary.append("Vegetarian");
        }
        if (isGlutenFree) {
            if (dietarySummary.length() > 0) dietarySummary.append(", ");
            dietarySummary.append("Gluten-Free");
        }
        if (isLactoseFree) {
            if (dietarySummary.length() > 0) dietarySummary.append(", ");
            dietarySummary.append("Lactose-Free");
        }
        if (isNutFree) {
            if (dietarySummary.length() > 0) dietarySummary.append(", ");
            dietarySummary.append("Nut-Free");
        }

        if (dietarySummary.length() == 0) {
            return "None";  // No dietary restrictions
        }

        return dietarySummary.toString();
    }
}



