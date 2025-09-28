package com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.repository;

import com.tecsup.example.hexagonal.application.port.output.UserRepository;
import com.tecsup.example.hexagonal.domain.model.User;
import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.entity.UserEntity;
import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserMapper userMapper;
    @Override
    public User save(User user) {
        //Domain -> Entity
        UserEntity userEntity = this.userMapper.toEntity(user);
        //Save Entity
        UserEntity entityCreated =  this.jpaRepository.save(userEntity);
        //Entity -> Domain
        return  this.userMapper.toDomain(userEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.jpaRepository.findById(id).map(this.userMapper::toDomain);
    }

    @Override
    public List<User> findByLastName(String lastName) {
        List<UserEntity> userEntities = this.jpaRepository.findByLastname(lastName);
        return userMapper.toDomainList(userEntities);
    }

}