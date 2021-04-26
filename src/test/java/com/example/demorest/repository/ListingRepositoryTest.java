package com.example.demorest.repository;

import com.example.demorest.model.Category;
import com.example.demorest.model.Listing;
import com.example.demorest.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class ListingRepositoryTest {

    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findById() {
        User user = User.builder()
                .name("vandam")
                .email("vandam@gmail.com")
                .password("janklodvandam")
                .build();
        userRepository.save(user);
        Category category = Category.builder()
                .name("home")
                .build();
        categoryRepository.save(category);
        Listing listing = Listing.builder()
                .title("asfsaf")
                .description("safdsaf")
                .price(159)
                .user(user)
                .category(category)
                .build();
        listingRepository.save(listing);
        Optional<Listing> byId = listingRepository.findById(listing.getId());
        assertTrue(byId.isPresent());
        assertEquals("safdsaf", byId.get().getDescription());
    }

    @Test
    public void findAll() {
        User user = User.builder()
                .name("vandam")
                .email("vandam@gmail.com")
                .password("janklodvandam")
                .build();
        userRepository.save(user);
        Category category = Category.builder()
                .name("home")
                .build();
        categoryRepository.save(category);
        Listing listing = Listing.builder()
                .title("asfsaf")
                .description("safdsaf")
                .price(159)
                .user(user)
                .category(category)
                .build();
        Listing listing1 = Listing.builder()
                .title("asfsaf")
                .description("safdsaf")
                .price(159)
                .user(user)
                .category(category)
                .build();
        Listing listing2 = Listing.builder()
                .title("asfsaf")
                .description("safdsaf")
                .price(159)
                .user(user)
                .category(category)
                .build();
        List<Listing> allList = List.of(listing, listing1, listing2);
        listingRepository.saveAll(allList);
        assertEquals(allList.size(), listingRepository.findAll().size());


    }
}
