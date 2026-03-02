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

        Cart userCart = getCart();

        Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", itemRequest.getProductId()));

        userCart.addProduct(product, itemRequest.getQuantity());

        Cart savedCart = cartRepository.save(userCart);

        return cartMapper.toDTO(savedCart);
    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        if (carts.isEmpty()) throw new APIException("There are no carts yet.");
        return cartMapper.toDTOs(carts);
    }

    @Override
    public CartDTO getCurrentUserCart() {
        Cart cart = getCart();
        return cartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public void removeItem(Long productId) {
        Cart cart = cartRepository.findCartByUser_Email(authUtil.loggedInEmail());
        if (cart == null) return;

        boolean removed = cart.removeProduct(productId);
        if (!removed) return;

        cartRepository.save(cart);

    }

    private Cart getCart() {
        Cart userCart = cartRepository.findCartByUser_Email(authUtil.loggedInEmail());
        if (userCart != null) return userCart;

        Cart cart = new Cart();
        cart.setUser(authUtil.loggedInUser());
        return cartRepository.save(cart);
    }

//    private @NonNull CartItem createCartItem(AddCartItemRequest itemRequest, Cart userCart) {
//        Product product = productRepository.findById(itemRequest.getProductId())
//                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", itemRequest.getProductId()));
//
//        CartItem cartItem = new CartItem();
//        cartItem.setCart(userCart);
//        cartItem.setQuantity(itemRequest.getQuantity());
//        cartItem.setProduct(product);
//        return cartItem;
//    }

}
