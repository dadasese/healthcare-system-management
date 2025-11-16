package com.hsm.authservice.mapper;


import com.hsm.authservice.dto.user.CreateUserRequestDTO;
import com.hsm.authservice.dto.user.UserResponseDTO;
import com.hsm.authservice.dto.user.UpdateUserRequestDTO;
import com.hsm.authservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    /*
    * Maps CreateUserRequestDTO to User entity
    * Password encoding and timestamps are handled in the service layer
    * */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", ignore = true)
    User toEntityUser(CreateUserRequestDTO createUserRequestDTO);

    /*
    * Maps User entity to UserResponseDTO
    * Excludes password for security
    * */
    UserResponseDTO toUserDTO(User user);

    /*
    *  Updates existing User entity with data from UpdateUserRequestDTO
    *  Only updates non-null fields
    * */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateUserFromDto(UpdateUserRequestDTO dto, @MappingTarget User user);
}
