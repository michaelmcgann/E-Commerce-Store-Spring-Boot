package com.sbeccommerce.ecommercestore.feature.user.controller;

import com.sbeccommerce.ecommercestore.feature.user.DTO.request.AddressRequest;
import com.sbeccommerce.ecommercestore.feature.user.DTO.response.AddressResponse;
import com.sbeccommerce.ecommercestore.feature.user.DTO.response.AddressResponsePage;
import com.sbeccommerce.ecommercestore.feature.user.service.AddressService;
import com.sbeccommerce.ecommercestore.global.config.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressResponse> createAddress(@RequestBody @Valid AddressRequest request) {
        AddressResponse createdAddress = addressService.createAddress(request);
        return ResponseEntity.ok(createdAddress);
    }

    @GetMapping("/admin/addresses")
    public ResponseEntity<AddressResponsePage> getAllAddresses(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ADDRESSES_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ADDRESSES_ORDER) String sortOrder) {

            AddressResponsePage addressPage = addressService.findAll(pageNumber, pageSize, sortBy, sortOrder);
            return ResponseEntity.ok(addressPage);
    }

    @GetMapping("/admin/addresses/{addressId}")
    public ResponseEntity<AddressResponse> getAddress(@PathVariable @Valid Long addressId) {
        AddressResponse response = addressService.getAddressById(addressId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/addresses")
    public ResponseEntity<AddressResponsePage> getUserAddresses(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ADDRESSES_BY) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ADDRESSES_ORDER) String sortOrder) {


        AddressResponsePage responsePage = addressService.getUserAddresses(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(responsePage);
    }

    @PostMapping("/addresses/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long addressId, @RequestBody @Valid AddressRequest request) {
        AddressResponse response = addressService.updateUserAddress(addressId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/addresses{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteUserAddress(addressId);
        return ResponseEntity.noContent().build();
    }

}
