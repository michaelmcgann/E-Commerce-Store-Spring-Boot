package com.sbeccommerce.ecommercestore.feature.user.service;

import com.sbeccommerce.ecommercestore.feature.user.DTO.request.AddressRequest;
import com.sbeccommerce.ecommercestore.feature.user.DTO.response.AddressResponse;
import com.sbeccommerce.ecommercestore.feature.user.DTO.response.AddressResponsePage;
import jakarta.validation.Valid;

public interface AddressService {

    AddressResponse createAddress(@Valid AddressRequest request);


    AddressResponsePage findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    AddressResponse getAddressById(@Valid Long addressId);

    AddressResponsePage getUserAddresses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    AddressResponse updateUserAddress(Long addressId, AddressRequest request);

    void deleteUserAddress(Long addressId);
}
