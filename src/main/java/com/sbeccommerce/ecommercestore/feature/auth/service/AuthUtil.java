package com.sbeccommerce.ecommercestore.feature.auth.service;

import com.sbeccommerce.ecommercestore.feature.user.model.User;
import com.sbeccommerce.ecommercestore.feature.user.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private final UserRepository userRepository;

    public AuthUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String loggedInEmail() {
        User user = getUser();
        return user.getEmail();
    }


    public User loggedInUser() {
        return getUser();
    }

    private @NonNull User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + authentication.getName()));
    }

}
