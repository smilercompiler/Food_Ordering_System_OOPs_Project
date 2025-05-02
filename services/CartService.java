package services;

import models.Cart;          
import models.FoodItem;      
import java.util.List;

public class CartService {
    private Cart cart;

    public CartService() {
        cart = new Cart(); 
    }

    public void addToCart(FoodItem item) {
        cart.addItem(item);
    }

    public List<FoodItem> getCartItems() {
        return cart.getItems();
    }

    public Cart getCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }
}
