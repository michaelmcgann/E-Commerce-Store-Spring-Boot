package com.sbeccommerce.ecommercestore.feature.auth.service;

import com.sbeccommerce.ecommercestore.feature.user.mapping.UserMapper;
import com.sbeccommerce.ecommercestore.feature.user.model.AppRole;
import com.sbeccommerce.ecommercestore.feature.user.model.Role;
import com.sbeccommerce.ecommercestore.feature.user.model.User;
import com.sbeccommerce.ecommercestore.feature.user.repository.RoleRepository;
import com.sbeccommerce.ecommercestore.feature.user.repository.UserRepository;
import com.sbeccommerce.ecommercestore.feature.user.service.RoleService;
import com.sbeccommerce.ecommercestore.global.common.exception.ResourceNotFoundException;
import com.sbeccommerce.ecommercestore.security.DTO.login.LoginRequest;
import com.sbeccommerce.ecommercestore.security.DTO.login.SignupRequest;
import com.sbeccommerce.ecommercestore.security.DTO.login.UserInfoResponse;
import com.sbeccommerce.ecommercestore.security.DTO.response.APIResponse;
import com.sbeccommerce.ecommercestore.security.jwt.JwtUtils;
import com.sbeccommerce.ecommercestore.security.service.UserDetailsImpl;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    public static final AppRole DEFAULT_ROLE = AppRole.ROLE_USER;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserInfoResponse authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        assert userDetails != null;
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new UserInfoResponse(userDetails.getId(), jwtToken, userDetails.getUsername(), roles);

    }

    @Override
    public Object registerUser(SignupRequest signupRequest) {

        Optional<ResponseEntity<APIResponse>> validationError = validateUniqueFields(signupRequest);
        if (validationError.isPresent()) return validationError.get();

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), encodedPassword);

        Set<Role> roles = getRoles(signupRequest);

        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);

    }

    private @NonNull Set<Role> getRoles(SignupRequest signupRequest) {
        Set<String> rolesStr = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (!rolesStr.isEmpty()) {
            rolesStr.stream()
                    .map(roleService::getRoleByRoleNameString)
                    .forEach(roles::add);
        } else {
            roles.add(roleRepository.getRoleByRoleName(DEFAULT_ROLE).orElseThrow(() -> new ResourceNotFoundException("Role", "roleName", DEFAULT_ROLE.toString())));
        }
        return roles;
    }

    private Optional<ResponseEntity<APIResponse>> validateUniqueFields(SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return Optional.of(ResponseEntity.badRequest().body(new APIResponse("Error: Username already exists.", false)));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return Optional.of(ResponseEntity.badRequest().body(new APIResponse("Error: Email already belongs to an account.", false)));
        }

        return Optional.empty();

    }

}
