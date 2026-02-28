package com.sbeccommerce.ecommercestore.feature.user.repository;

import com.sbeccommerce.ecommercestore.feature.user.model.AppRole;
import com.sbeccommerce.ecommercestore.feature.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> getRoleByRoleName(AppRole roleName);

}
