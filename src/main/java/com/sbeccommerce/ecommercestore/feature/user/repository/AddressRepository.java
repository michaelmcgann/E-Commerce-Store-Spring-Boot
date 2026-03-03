package com.sbeccommerce.ecommercestore.feature.user.repository;

import com.sbeccommerce.ecommercestore.feature.user.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.user.userId = :userId")
    Page<Address> findAllByUserId(Pageable pageable, Long userId);

    Optional<Address> findByAddressIdAndUser_UserId(Long addressId, Long userId);
}
