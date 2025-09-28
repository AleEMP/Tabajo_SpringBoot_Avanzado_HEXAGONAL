package com.tecsup.example.hexagonal.application.service;

import com.tecsup.example.hexagonal.application.port.output.UserRepository;
import com.tecsup.example.hexagonal.domain.exception.UserNotFoundException;
import com.tecsup.example.hexagonal.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Arrays;
import javax.management.AttributeList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        // Aquí puedes inicializar los mocks y el servicio
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void createUser() {
        Long ID = 50L;
        String NAME = "Juana";
        String LASTNAME= "Rodriguez";
        String EMAIL = "juana@demo.com";

        User newUser = new User(null, NAME, EMAIL,LASTNAME); // UserRequest
        User savedUser = new User(ID, NAME, EMAIL,LASTNAME);  // Save UserEntity

        // Mocking the repository behavior
        when(userRepository.save(newUser)).thenReturn(savedUser);

        // Execute the service method
        User realUser = userService.createUser(newUser);

        // Validate the results
        assertNotNull(realUser);
        assertEquals(ID, realUser.getId());
        assertEquals(NAME, realUser.getName());
        assertEquals(EMAIL, realUser.getEmail());

    }

    @Test
    void findUser() {
        Long ID = 100L;
        String NAME = "Jaime";
        String EMAIL = "jaime@demo.com";
        String LASTNAME = "Rodriguez";

        User existingUser = new User(ID,NAME,EMAIL,LASTNAME);
        when(userRepository.findById(100L)).thenReturn(Optional.of(existingUser));
        User realUser =userService.findUser(100L);
        assertNotNull(realUser);
        assertEquals(ID,realUser.getId());
        assertEquals(NAME,realUser.getName());
        assertEquals(EMAIL,realUser.getEmail());
    }
    @Test
    void findUserByLastName() {
        String LASTNAME = "Rodriguez";
        User user1 = new User(10L, "Jaime", "jaime@demo.com", LASTNAME);
        User user2 = new User(23L, "Juana", "juana@demo.com", LASTNAME);
        List<User> userList = Arrays.asList(user1, user2);
        when(userRepository.findByLastName(LASTNAME)).thenReturn(userList);
        List<User> realUserList = userService.getUsersByLastname(LASTNAME);

        assertNotNull(realUserList);
        assertFalse(realUserList.isEmpty());
        assertEquals(2, realUserList.size());
    }
    @Test
    void findUser_NotFound() {
        Long ID_UNKNOW = 999L;

        when(userRepository.findById(ID_UNKNOW)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.findUser(ID_UNKNOW));
    }
}