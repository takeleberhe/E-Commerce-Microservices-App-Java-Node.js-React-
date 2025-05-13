package com.ecommerce.AuthService.mapper;

import com.ecommerce.AuthService.Dto.UserDTO;
import com.ecommerce.AuthService.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")  // This annotation tells MapStruct to generate a Spring bean.
public interface UserMapper {

    // Create a static instance of the mapper
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    // Convert UserDTO to User entity
    User userDTOToUser(UserDTO userDTO);

    // Convert User entity to UserDTO
    UserDTO userToUserDTO(User user);
    
}
