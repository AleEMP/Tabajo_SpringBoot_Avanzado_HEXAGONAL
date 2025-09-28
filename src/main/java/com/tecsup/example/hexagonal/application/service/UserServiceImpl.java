package com.tecsup.example.hexagonal.application.service;

import com.tecsup.example.hexagonal.application.port.input.UserService;
import com.tecsup.example.hexagonal.application.port.output.UserRepository;
import com.tecsup.example.hexagonal.domain.exception.InvalidUserDataException;
import com.tecsup.example.hexagonal.domain.exception.UserNotFoundException;
import com.tecsup.example.hexagonal.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(User newUser) {
        validateUserInput(newUser);
        User user=  this.userRepository.save(newUser);

        return user;
    }

    @Override
    public User findUser(Long id) {
        if(id==null|| id<=0){
            throw new IllegalArgumentException("Invalid user id");
        }
        User user = this.userRepository.findById(id)
                .orElseThrow( ()-> new UserNotFoundException(id) );
        return user;
    }

    @Override
    public List<User> getUsersByLastname(String lastname) {
        return this.userRepository.findByLastName(lastname);
    }

    private void validateUserInput(User newUser) {

        String email = newUser.getEmail();

        if (!newUser.hasValidName())
            throw new InvalidUserDataException("Invalid email");

        if (!newUser.hasValidEmail())
            throw new InvalidUserDataException("Invalid email");
    }
}
