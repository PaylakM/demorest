package com.example.demorest.endpoint;

import com.example.demorest.model.Category;
import com.example.demorest.model.Listing;
import com.example.demorest.model.User;
import com.example.demorest.repository.CategoryRepository;
import com.example.demorest.repository.UserRepository;
import com.example.demorest.service.ListingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ListingControllerTest {
    @MockBean
    private ListingService listingService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired

    @Test
    public void allListing() throws Exception {
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
        List<Listing> listingList = Arrays.asList(listing, listing1, listing2);
        when(listingService.allListing()).thenReturn(listingList);
        mockMvc.perform(get("/listings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    public void getByUserEmail() throws Exception {
        User user = User.builder()
                .name("vandam")
                .email("vandam@gmail.com")
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
        Listing listing1 = emailListing.get(0);
        when(listingService.getByUserEmail(listing.getUser().getEmail())).thenReturn(emailListing);
        mockMvc.perform(get("/listings/byUser/" + listing1.getUser().getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("*.user.email").value(listing1.getUser().getEmail()))
                .andExpect(jsonPath("*.title").value(listing1.getTitle()))
                .andExpect(jsonPath("*.price").value(listing1.getPrice()))
                .andExpect(jsonPath("*.category.name").value(category.getName()));
    }

    @Test
    public void getListingByCategory() throws Exception {
        User user = User.builder()
                .name("vandam")
                .email("vandam@gmail.com")
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
        when(listingService.getListingByCategory(listing.getCategory().getId())).thenReturn(Optional.of(listing));
        mockMvc.perform(get("/listings/byCategory/" + listing.getCategory().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category.id").value(listing.getCategory().getId()));
    }

    @Test
    public void getListingById() throws Exception {
        User user = User.builder()
                .name("vandam")
                .email("vandam@gmail.com")
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
        when(listingService.findById(listing.getId())).thenReturn(Optional.of(listing));
        mockMvc.perform(get("/listings/" + listing.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(listing.getTitle()));
    }

    @Test
    public void addListing() throws Exception {
        User user = User.builder()
                .name("vandam")
                .email("vandam@gmail.com")
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
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(listing);
        when(listingService.addListing(listing)).thenReturn(listing);
        mockMvc.perform(post("/listings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(listing.getTitle()));
    }

    @Test
    public void deleteListingById() throws Exception {
        User user = User.builder()
                .name("vandam")
                .email("vandam@gmail.com")
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
        when(listingService.findById(listing.getId())).thenReturn(Optional.of(listing));
        mockMvc.perform(delete("/listings/"+listing.getId()))
                .andExpect(status().isOk());
        verify(listingService).deleteListingById(listing.getId());
    }

    @Test
    public void changeListing() throws Exception {
        User user = User.builder()
                .name("vandam")
                .email("vandam@gmail.com")
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
        when(listingService.findById(listing.getId())).thenReturn(Optional.of(listing));
        listing = Listing.builder()
                .title("aaaaaa")
                .description("aaaaaaaaa")
                .price(159)
                .user(user)
                .category(category)
                .build();
        when(listingService.changeListing(listing.getId(),listing)).thenReturn(Optional.of(listing));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(listing);
        mockMvc.perform(put("/listings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .param("id", Integer.toString(listing.getId())))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
