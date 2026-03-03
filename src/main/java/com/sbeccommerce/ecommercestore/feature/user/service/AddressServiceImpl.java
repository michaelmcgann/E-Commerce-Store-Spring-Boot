package com.sbeccommerce.ecommercestore.feature.user.service;

import com.sbeccommerce.ecommercestore.feature.auth.service.AuthUtil;
import com.sbeccommerce.ecommercestore.feature.user.DTO.request.AddressRequest;
import com.sbeccommerce.ecommercestore.feature.user.DTO.response.AddressResponse;
import com.sbeccommerce.ecommercestore.feature.user.DTO.response.AddressResponsePage;
import com.sbeccommerce.ecommercestore.feature.user.mapping.AddressMapper;
import com.sbeccommerce.ecommercestore.feature.user.model.Address;
import com.sbeccommerce.ecommercestore.feature.user.model.User;
import com.sbeccommerce.ecommercestore.feature.user.repository.AddressRepository;
import com.sbeccommerce.ecommercestore.feature.user.repository.UserRepository;
import com.sbeccommerce.ecommercestore.global.common.exception.APIException;
import com.sbeccommerce.ecommercestore.global.common.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class AddressServiceImpl implements AddressService {


    private final AuthUtil authUtil;
    private final AddressMapper addressMapper;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AuthUtil authUtil, AddressMapper addressMapper, UserRepository userRepository, AddressRepository addressRepository) {
        this.authUtil = authUtil;
        this.addressMapper = addressMapper;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public AddressResponse createAddress(AddressRequest request) {

        User loggedInUser = authUtil.loggedInUser();
        Address address = addressMapper.toModel(request);

        loggedInUser.addAddress(address);

        userRepository.save(loggedInUser);
        return addressMapper.toResponse(address);

    }

    @Override
    public AddressResponsePage findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Pageable pageDetails = createPageable(pageNumber, pageSize, sortBy, sortOrder);
        Page<Address> addressPage = addressRepository.findAll(pageDetails);

        if (addressPage.isEmpty()) throw new APIException("There are no addresses added yet.");

        return addressMapper.toResponsePage(addressPage);
    }

    @Override
    public AddressResponse getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new APIException("Address not found with id: " + addressId));
        return addressMapper.toResponse(address);
    }

    @Override
    public AddressResponsePage getUserAddresses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageDetails = createPageable(pageNumber, pageSize, sortBy, sortOrder);
        Long userId = authUtil.loggedInUser().getUserId();

        Page<Address> addressPage = addressRepository.findAllByUserId(pageDetails, userId);
        return addressMapper.toResponsePage(addressPage);
    }

    @Override
    @Transactional
    public AddressResponse updateUserAddress(Long addressId, AddressRequest request) {
        Long userId = authUtil.loggedInUser().getUserId();
        Address address = addressRepository.findByAddressIdAndUser_UserId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        addressMapper.updateAddressFromRequest(request, address);
        addressRepository.save(address);

        return addressMapper.toResponse(address);

    }

    @Override
    @Transactional
    public void deleteUserAddress(Long addressId) {
        Long userId = authUtil.loggedInUser().getUserId();
        Address address = addressRepository.findByAddressIdAndUser_UserId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        addressRepository.delete(address);
    }

    private Pageable createPageable(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Set<String> allowed = Set.of("addressId", "street", "city", "region", "postcode", "country");
        if (!allowed.contains(sortBy)) {
            throw new APIException("sortBy name: '" + sortBy + "' not valid.");
        }

        Sort sortByAndOrder = "asc".equalsIgnoreCase(sortOrder)
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

        return PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    }

}
