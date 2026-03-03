package com.sbeccommerce.ecommercestore.feature.user.mapping;

import com.sbeccommerce.ecommercestore.feature.user.DTO.request.AddressRequest;
import com.sbeccommerce.ecommercestore.feature.user.DTO.response.AddressResponse;
import com.sbeccommerce.ecommercestore.feature.user.DTO.response.AddressResponsePage;
import com.sbeccommerce.ecommercestore.feature.user.model.Address;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AddressMapper {

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address toModel(AddressRequest addressRequest);

    AddressResponse toResponse(Address address);

    List<AddressResponse> toResponses(List<Address> addresses);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateAddressFromRequest(AddressRequest request, @MappingTarget Address address);

    default AddressResponsePage toResponsePage(Page<Address> addressPage) {
        AddressResponsePage responsePage = new AddressResponsePage();
        responsePage.setContent(toResponses(addressPage.getContent()));
        responsePage.setPageNumber(addressPage.getNumber());
        responsePage.setPageSize(addressPage.getSize());
        responsePage.setTotalElements(addressPage.getTotalElements());
        responsePage.setTotalPages(addressPage.getTotalPages());
        responsePage.setLastPage(addressPage.isLast());
        return responsePage;
    }

}
