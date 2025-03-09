package model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Integer, Item> items;
    
    public Cart() {
        items = new HashMap<>();
    }
    
    public Map<Integer, Item> getItems() {
        return items;
    }
    
    public double getTotalMoney() {
        double total = 0;
        for (Item item : items.values()) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total;
    }
    
    public void addItem(Product product, int quantity) {
        if (items.containsKey(product.getId())) {
            Item item = items.get(product.getId());
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            items.put(product.getId(), new Item(product, quantity));
        }
    }
    
    public void removeItem(int productId) {
        items.remove(productId);
    }
    
    public static class Item {
        private Product product;
        private int quantity;
        private double price;
        
        public Item(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
            this.price = product.getPrice();
        }
        
        public Product getProduct() {
            return product;
        }
        
        public int getQuantity() {
            return quantity;
        }
        
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        
        public double getPrice() {
            return price;
        }
    }
}