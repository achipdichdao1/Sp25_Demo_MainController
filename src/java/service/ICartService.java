package service;

import model.CartItem;
import java.util.List;

public interface ICartService {
    void addToCart(int productId, int quantity);
    List<CartItem> getCartItems();
    void removeFromCart(int productId);
    void updateCartItem(int productId, int quantity);
    double getTotalPrice();
}