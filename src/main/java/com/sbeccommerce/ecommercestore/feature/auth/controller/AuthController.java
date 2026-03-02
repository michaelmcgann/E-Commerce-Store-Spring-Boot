package com.sbeccommerce.ecommercestore.feature.auth.controller;

import com.sbeccommerce.ecommercestore.feature.auth.service.AuthService;
import com.sbeccommerce.ecommercestore.feature.user.repository.UserRepository;
import com.sbeccommerce.ecommercestore.security.DTO.login.LoginRequest;
import com.sbeccommerce.ecommercestore.security.DTO.login.SignupRequest;
import com.sbeccommerce.ecommercestore.security.DTO.login.UserInfoResponse;
import com.sbeccommerce.ecommercestore.security.DTO.response.APIResponse;
import com.sbeccommerce.ecommercestore.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        UserInfoResponse userInfoResponse = authService.authenticateUser(loginRequest);
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userInfoResponse.getUsername());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(userInfoResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(authService.registerUser(signupRequest));
    }

    @GetMapping("/username")
    public String currentUsername() {
        return authService.getCurrentUsername();
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOutUser() {
        ResponseCookie cookie = authService.getSignOutCookie();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new APIResponse("You've been signed out", true));
    }



}
