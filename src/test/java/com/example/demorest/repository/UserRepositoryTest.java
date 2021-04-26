package com.example.demorest.repository;

import com.example.demorest.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findById() {
        User user = User.builder()
                .name("vandam")
                .email("vandam@gmail.com")
                .password("janklodvandam")
                .build();
        userRepository.save(user);
        Optional<User> byId = userRepository.findById(user.getId());
        assertTrue(byId.isPresent());
        assertEquals("vandam@gmail.com", byId.get().getEmail());

    }

    @Test
    public void findAll() {
        List<User> allUsers = Arrays.asList(User.builder()
                        .name("poxos1")
                        .email("poxos1@yandex.ru")
                        .password("kondoloz3")
                        .build(),
                User.builder()
                        .name("poxos2")
                        .email("poxos2@yandex.ru")
                        .password("kondoloz2")
                        .build(),
                User.builder()
                        .name("poxos3")
                        .email("poxos3@yandex.ru")
                        .password("kondoloz1")
                        .build());
        userRepository.saveAll(allUsers);
        Assert.assertEquals(allUsers.size(),userRepository.findAll().size());
    }

}
