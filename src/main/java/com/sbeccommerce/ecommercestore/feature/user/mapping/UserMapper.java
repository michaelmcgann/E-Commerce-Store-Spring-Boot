package com.sbeccommerce.ecommercestore.feature.user.mapping;

import com.sbeccommerce.ecommercestore.feature.user.DTO.response.UserResponse;
import com.sbeccommerce.ecommercestore.feature.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    UserResponse toResponse(User user);

}
