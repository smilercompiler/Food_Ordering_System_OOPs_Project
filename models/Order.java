package models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private String orderId;
    private List<FoodItem> items;
    private String status;
    private LocalDateTime timePlaced;

    public Order(String orderId, List<FoodItem> items) {
        this.orderId = orderId;
        this.items = items;
        this.status = "Received";
        this.timePlaced = LocalDateTime.now();
    }

    public String getOrderId() {
        return orderId;
    }

    public List<FoodItem> getItems() {
        return items;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimePlaced() {
        return timePlaced;
    }
}
