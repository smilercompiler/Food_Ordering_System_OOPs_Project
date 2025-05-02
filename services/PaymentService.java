package services;

import models.Cart;

public class PaymentService {
    public boolean processPayment(Cart cart, String paymentMethod) {
        // Calculate the total amount in the cart
        int totalAmount = cart.calculateTotal();

        if (totalAmount <= 0) {
            System.out.println("Invalid total amount: " + totalAmount);
            return false;
        }

        // Process payment based on selected method
        switch (paymentMethod.toLowerCase()) {
            case "credit card":
                System.out.println("Processing credit card payment of ₹" + totalAmount);
                break;
            case "debit card":
                System.out.println("Processing debit card payment of ₹" + totalAmount);
                break;
            case "cash":
                System.out.println("Processing cash payment of ₹" + totalAmount);
                break;
            case "online":
                System.out.println("Processing online payment of ₹" + totalAmount);
                break;
            default:
                System.out.println("Invalid payment method: " + paymentMethod);
                return false;
        }

        // Assuming payment is successful
        System.out.println("Payment of ₹" + totalAmount + " processed successfully using " + paymentMethod);
        return true;
    }
}
