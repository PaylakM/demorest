package com.example.demorest.service;

import com.example.demorest.model.Category;
import com.example.demorest.model.User;
import com.example.demorest.repository.CategoryRepository;
import com.example.demorest.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void getAll() {
        List<User> users = Arrays.asList(User.builder()
                        .name("poxos")
                        .email("poxos@mail.ru")
                        .password("poxosyanik")
                        .build(),
                User.builder()
                        .name("valod")
                        .email("valod@mail.ru")
                        .password("chgitem inch")
                        .build(),
                User.builder()
                        .name("vandam")
                        .email("vandam@mail.ru")
                        .password("vandam jan")
                        .build());
        when(userRepository.findAll()).thenReturn(users);
        List<User> allUsers = userService.getAllUsers();
        assertEquals(users.size(), allUsers.size());
    }

    @Test
    public void getUserById() {
        User user = User.builder()
                .name("vandam")
                .email("vandam@mail.ru")
                .password("vandam jan")
                .build();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<User> userById = userService.findById(user.getId());
        assertTrue(userById.isPresent());
        assertEquals(user.getId(),userById.get().getId());
    }

    @Test
    public void addUser(){
        User user = User.builder()
                .name("vandam")
                .email("vandam@mail.ru")
                .password("vandam jan")
                .build();
        when(userRepository.save(user)).thenReturn(user);
        User user1 = userService.addUser(user);
        assertEquals(user.getId(),user1.getId());
    }

    @Test
    public void findByEmail(){
        User user = User.builder()
                .name("vandam")
                .email("vandam@mail.ru")
                .password("vandam jan")
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Optional<User> byEmail = userService.findByEmail(user.getEmail());
        assertTrue(byEmail.isPresent());
        assertEquals(user.getEmail(),byEmail.get().getEmail());
    }

    @Test
    public void change(){
        int id=0;
        User user = User.builder()
                .name("vandam")
                .email("vandam@mail.ru")
                .password("vandam jan")
                .build();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        Optional<User> user1 = userService.changeUser(id, user);
        assertTrue(user1.isPresent());
        assertEquals(user.getEmail(),user1.get().getEmail());
    }

    @Test
    public void deleteById(){
        User user = User.builder()
                .name("vandam")
                .email("vandam@mail.ru")
                .password("vandam jan")
                .build();
        userService.deleteById(user.getId());
        verify(userRepository).deleteById(user.getId());
    }
}
