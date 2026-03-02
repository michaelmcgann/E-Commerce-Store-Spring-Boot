package com.sbeccommerce.ecommercestore.feature.cart.service;

import com.sbeccommerce.ecommercestore.feature.auth.service.AuthUtil;
import com.sbeccommerce.ecommercestore.feature.cart.DTO.AddCartItemRequest;
import com.sbeccommerce.ecommercestore.feature.cart.DTO.CartDTO;
import com.sbeccommerce.ecommercestore.feature.cart.mapping.CartMapper;
import com.sbeccommerce.ecommercestore.feature.cart.model.Cart;
import com.sbeccommerce.ecommercestore.feature.cart.repository.CartRepository;
import com.sbeccommerce.ecommercestore.feature.product.model.Product;
import com.sbeccommerce.ecommercestore.feature.product.repository.ProductRepository;
import com.sbeccommerce.ecommercestore.global.common.exception.APIException;
import com.sbeccommerce.ecommercestore.global.common.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final AuthUtil authUtil;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository, AuthUtil authUtil, ProductRepository productRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.authUtil = authUtil;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
    }

    @Transactional
    @Override
    public CartDTO addItem(AddCartItemRequest itemRequest) {

        Cart userCart = getOrCreateCart();

        Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", itemRequest.getProductId()));

        userCart.addProduct(product, itemRequest.getQuantity());

        Cart savedCart = cartRepository.save(userCart);

        return cartMapper.toDTO(savedCart);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        if (carts.isEmpty()) throw new APIException("There are no carts yet.");
        return cartMapper.toDTOs(carts);
    }

    @Override
    @Transactional(readOnly = true)
    public CartDTO getCurrentUserCart() {
        Cart cart = findCartOrNull();
        if (cart == null) return new CartDTO(); // Returning empty DTO
        return cartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public void removeItem(Long productId) {
        String loggedInEmail = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByUser_Email(loggedInEmail);
        if (cart == null) throw new ResourceNotFoundException("Cart", "email", loggedInEmail);

        boolean removed = cart.removeProduct(productId);
        if (!removed) throw new ResourceNotFoundException("Product", "productId", productId);

        cartRepository.save(cart);

    }

    private Cart findCartOrNull() {
        return cartRepository.findCartByUser_Email(authUtil.loggedInEmail());
    }

    private Cart getOrCreateCart() {
        Cart cart = findCartOrNull();
        if (cart != null) return cart;

        try {
            Cart newCart = new Cart();
            newCart.setUser(authUtil.loggedInUser());
            return cartRepository.save(newCart);
        } catch (DataIntegrityViolationException exception) {
            return cartRepository.findCartByUser_Email(authUtil.loggedInEmail());
        }
    }

}
