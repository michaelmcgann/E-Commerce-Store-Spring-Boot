package com.sbeccommerce.ecommercestore.feature.order.service;

import com.sbeccommerce.ecommercestore.feature.auth.service.AuthUtil;
import com.sbeccommerce.ecommercestore.feature.cart.model.Cart;
import com.sbeccommerce.ecommercestore.feature.cart.model.CartItem;
import com.sbeccommerce.ecommercestore.feature.cart.repository.CartRepository;
import com.sbeccommerce.ecommercestore.feature.order.DTO.OrderDTO;
import com.sbeccommerce.ecommercestore.feature.order.DTO.OrderRequestDTO;
import com.sbeccommerce.ecommercestore.feature.order.mapping.OrderMapper;
import com.sbeccommerce.ecommercestore.feature.order.model.Order;
import com.sbeccommerce.ecommercestore.feature.order.model.OrderItem;
import com.sbeccommerce.ecommercestore.feature.order.model.Payment;
import com.sbeccommerce.ecommercestore.feature.order.repository.OrderRepository;
import com.sbeccommerce.ecommercestore.feature.product.model.Product;
import com.sbeccommerce.ecommercestore.feature.product.repository.ProductRepository;
import com.sbeccommerce.ecommercestore.feature.user.model.Address;
import com.sbeccommerce.ecommercestore.feature.user.model.User;
import com.sbeccommerce.ecommercestore.feature.user.repository.AddressRepository;
import com.sbeccommerce.ecommercestore.global.common.exception.APIException;
import com.sbeccommerce.ecommercestore.global.common.exception.ResourceNotFoundException;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final AuthUtil authUtil;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(AuthUtil authUtil, CartRepository cartRepository, AddressRepository addressRepository, ProductRepository productRepository, OrderMapper orderMapper, OrderRepository orderRepository) {
        this.authUtil = authUtil;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public OrderDTO placeOrder(String paymentMethod, OrderRequestDTO request) {

        User user = authUtil.loggedInUser();
        Long userId = user.getUserId();

        Cart cart = getCart(userId);

        Address address = getAddress(request, userId);

        Payment payment = getPayment(request);

        Order order = getOrder(user, address, payment, cart);

        addOrderItemsFromCart(cart, order);

        Order savedOrder = orderRepository.save(order);

        clearCart(cart);

        return orderMapper.toOrderDTO(savedOrder);
    }

    private void clearCart(Cart cart) {
        cart.getCartItems().forEach(cartItem -> cartItem.setCart(null));
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    private void addOrderItemsFromCart(Cart cart, Order order) {
        List<Product> touchedProducts = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            int qty = cartItem.getQuantity();

            product.setQuantity(product.getQuantity() - qty);
            touchedProducts.add(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(qty);

            order.getOrderItems().add(orderItem);
        }

        productRepository.saveAll(touchedProducts);
    }

    private static @NonNull Order getOrder(User user, Address address, Payment payment, Cart cart) {
        Order order = new Order();
        order.setEmail(user.getEmail());
        order.setAddress(address);
        order.setPayment(payment);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("PLACED");
        order.setTotalPrice(cart.getTotalPrice());
        payment.setOrder(order);
        return order;
    }

    private static @NonNull Payment getPayment(OrderRequestDTO request) {
        Payment payment = new Payment();
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPgName(request.getPgName());
        payment.setPgPaymentId(request.getPgPaymentId());
        payment.setPgResponseMessage(request.getPgResponseMessage());
        payment.setPgStatus(request.getPgStatus());
        return payment;
    }

    private @NonNull Address getAddress(OrderRequestDTO request, Long userId) {
        return addressRepository.findByAddressIdAndUser_UserId(request.getAddressId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", request.getAddressId()));
    }

    private @NonNull Cart getCart(Long userId) {
        Cart cart = cartRepository.findCartByUser_UserId(userId);
        if (cart == null) throw new ResourceNotFoundException("Cart", "userID", userId);
        if (cart.getCartItems().isEmpty()) throw new APIException("Cart empty.");
        return cart;
    }
}
