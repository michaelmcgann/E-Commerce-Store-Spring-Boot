package com.sbeccommerce.ecommercestore.feature.user.service;

import com.sbeccommerce.ecommercestore.feature.user.model.AppRole;
import com.sbeccommerce.ecommercestore.feature.user.model.Role;
import com.sbeccommerce.ecommercestore.feature.user.repository.RoleRepository;
import com.sbeccommerce.ecommercestore.global.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleServiceImpl roleService;

    private Role adminRole;

    @BeforeEach
    void setUp() {
        adminRole = new Role(AppRole.ROLE_ADMIN);
    }

    @Test
    void getRoleByRoleNameString_ShouldReturnRole_WhenRoleExists() {

        String roleName = "ROLE_ADMIN";
        when(roleRepository.getRoleByRoleName(AppRole.ROLE_ADMIN)).thenReturn(Optional.of(adminRole));

        Role result = roleService.getRoleByRoleNameString(roleName);

        assertNotNull(result);
        assertEquals(AppRole.ROLE_ADMIN, result.getRoleName());
        verify(roleRepository, times(1)).getRoleByRoleName(AppRole.ROLE_ADMIN);

    }

    @Test
    void getRoleByRoleNameString_ShouldThrowException_WhenRoleDoesNotExist() {

        String roleName = "ROLE_SELLER";
        when(roleRepository.getRoleByRoleName(AppRole.ROLE_SELLER)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            roleService.getRoleByRoleNameString(roleName);
        });

        verify(roleRepository, times(1)).getRoleByRoleName(AppRole.ROLE_SELLER);

    }



}
