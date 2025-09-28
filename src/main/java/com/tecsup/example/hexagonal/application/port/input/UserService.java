package com.tecsup.example.hexagonal.application.port.input;

import com.tecsup.example.hexagonal.domain.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User findUser(Long id);
    List<User> getUsersByLastname(String lastname);
}
