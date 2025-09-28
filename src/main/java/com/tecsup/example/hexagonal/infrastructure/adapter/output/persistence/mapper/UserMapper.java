package com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.mapper;
import com.tecsup.example.hexagonal.domain.model.User;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto.UserRequest;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto.UserResponse;
import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper (componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(User domain);
    User toDomain(UserEntity entity);
    User toDomain(UserRequest request);

    UserResponse toResponse(User createUser);

    List<User> toDomainList(List<UserEntity> userEntities);
}
