package com.sbeccommerce.ecommercestore.feature.user.service;

import com.sbeccommerce.ecommercestore.feature.user.model.AppRole;
import com.sbeccommerce.ecommercestore.feature.user.model.Role;
import com.sbeccommerce.ecommercestore.feature.user.repository.RoleRepository;
import com.sbeccommerce.ecommercestore.global.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role getRoleByRoleNameString(String roleName) {

        AppRole roleNameEnum = switch (roleName.toUpperCase()) {
            case "ROLE_ADMIN" -> AppRole.ROLE_ADMIN;
            case "ROLE_SELLER" -> AppRole.ROLE_SELLER;
            default -> AppRole.ROLE_USER;
        };

        return roleRepository.getRoleByRoleName(roleNameEnum).orElseThrow(() -> new ResourceNotFoundException("Role", "role", roleNameEnum.toString()));
    }
}
