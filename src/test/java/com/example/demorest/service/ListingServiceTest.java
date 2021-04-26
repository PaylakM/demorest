package com.example.demorest.service;

import com.example.demorest.model.Category;
import com.example.demorest.model.Listing;
import com.example.demorest.model.User;
import com.example.demorest.repository.CategoryRepository;
import com.example.demorest.repository.ListingRepository;
import com.example.demorest.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListingServiceTest {

    @MockBean
    private ListingRepository listingRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    private ListingService listingService;


    @Test
    public void allListing() {
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
        List<Listing> allListing = Arrays.asList(listing, listing1, listing2);
        when(listingRepository.findAll()).thenReturn(allListing);
        List<Listing> listings = listingService.allListing();
        assertEquals(allListing.size(), listings.size());
    }

    @Test
    public void getByUserEmail() {
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
        List<Listing> emailListing = Collections.singletonList(listing);
        when(listingRepository.findByUserEmail(listing.getUser().getEmail())).thenReturn(emailListing);
        List<Listing> byUserEmail = listingService.getByUserEmail(listing.getUser().getEmail());
        assertEquals(1, byUserEmail.size());
        assertEquals(emailListing.get(0).getTitle(), byUserEmail.get(0).getTitle());
    }

    @Test
    public void getListingByCategoryId() {
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
        when(listingRepository.findByCategory_Id(category.getId())).thenReturn(Optional.of(listing));
        Optional<Listing> listingByCategory = listingService.getListingByCategory(listing.getId());
        assertTrue(listingByCategory.isPresent());
        assertEquals(listing.getId(), listingByCategory.get().getId());
    }

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
        when(listingRepository.findById(listing.getId())).thenReturn(Optional.of(listing));
        Optional<Listing> byId = listingService.findById(listing.getId());
        assertTrue(byId.isPresent());
        assertEquals(listing.getId(), byId.get().getId());
    }

    @Test
    public void addListing() {
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
        when(listingRepository.save(listing)).thenReturn(listing);
        Listing listing1 = listingService.addListing(listing);
        assertEquals(listing1, listing1);
    }

    @Test
    public void changeListing() {
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
        when(listingRepository.findById(listing.getId())).thenReturn(Optional.of(listing));
        when(listingRepository.save(listing)).thenReturn(listing);
        Optional<Listing> listing2 = listingService.changeListing(listing.getId(), listing1);
        assertTrue(listing2.isPresent());
        assertEquals(listing1.getTitle(),listing2.get().getTitle());
        assertEquals(listing1.getDescription(),listing2.get().getDescription());
        assertEquals(listing1.getUser().getEmail(),listing2.get().getUser().getEmail());
    }

    @Test
    public void deleteListingById(){
        Listing listing = Listing.builder()
                .title("asfsaf")
                .description("safdsaf")
                .price(159)
                .build();
        listingService.deleteListingById(listing.getId());
        verify(listingRepository).deleteById(listing.getId());
    }

}
