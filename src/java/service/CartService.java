package service;

import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Product;

public class CartService {
    private List<CartItem> items = new ArrayList<>();
    private double totalPrice = 0;

    public void addProduct(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                totalPrice += product.getPrice() * quantity;
                return;
            }
        }
        CartItem newItem = new CartItem(product, quantity);
        items.add(newItem);
        totalPrice += product.getPrice() * quantity;
    }

    public void removeProduct(int productId) {
        CartItem itemToRemove = null;
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                totalPrice -= item.getProduct().getPrice() * item.getQuantity();
                itemToRemove = item;
                break;
            }
        }
        if (itemToRemove != null) {
            items.remove(itemToRemove);
        }
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
