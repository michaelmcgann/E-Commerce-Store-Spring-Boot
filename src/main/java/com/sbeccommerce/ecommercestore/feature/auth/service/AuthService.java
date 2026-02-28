package com.sbeccommerce.ecommercestore.feature.auth.service;

import com.sbeccommerce.ecommercestore.security.DTO.login.LoginRequest;
import com.sbeccommerce.ecommercestore.security.DTO.login.SignupRequest;
import com.sbeccommerce.ecommercestore.security.DTO.login.UserInfoResponse;

public interface AuthService {

    UserInfoResponse authenticateUser(LoginRequest loginRequest);

    Object registerUser(SignupRequest signupRequest);
}
