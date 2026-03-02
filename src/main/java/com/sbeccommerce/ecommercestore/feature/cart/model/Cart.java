package com.sbeccommerce.ecommercestore.feature.cart.model;

import com.sbeccommerce.ecommercestore.feature.product.model.Product;
import com.sbeccommerce.ecommercestore.feature.user.model.User;
import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    private Double totalPrice = 0.0;

    public Cart() {
    }

    public Cart(Long cartId, User user, Set<CartItem> cartItems, Double totalPrice) {
        this.cartId = cartId;
        this.user = user;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }

    public void addProduct(Product product, int qty) {
        if (qty == 0) return;

        Long productId = product.getProductId();
        CartItem item = getCartItemByProductId(productId);

        if (item == null) {
            if (qty <= 0) return;
            item = new CartItem();
            item.setProduct(product);
            item.setQuantity(qty);
            addItem(item);
            return;
        }
        int newQty = item.getQuantity() + qty;
        if (newQty <= 0) removeItem(item);
        else item.setQuantity(newQty);

    }

    public boolean removeProduct(Long productId) {
        CartItem item = getCartItemByProductId(productId);
        if (item == null) return false;
        removeItem(item);
        return true;
    }

    private @Nullable CartItem getCartItemByProductId(Long productId) {
        return cartItems.stream()
                .filter(ci -> ci.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    private void recalculateTotalPrice() {
        this.totalPrice = cartItems.stream()
                .mapToDouble(ci -> ci.getProduct().getPrice() * ci.getQuantity())
                .sum();
    }

    private void addItem(CartItem item) {
        cartItems.add(item);
        item.setCart(this);
        recalculateTotalPrice();
    }

    private void removeItem(CartItem item) {
        cartItems.remove(item);
        item.setCart(null);
        recalculateTotalPrice();
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
