package com.sbeccommerce.ecommercestore.feature.user.repository;

import com.sbeccommerce.ecommercestore.feature.user.model.User;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(@NonNull String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
