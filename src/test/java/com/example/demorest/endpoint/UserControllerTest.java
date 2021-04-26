package com.example.demorest.endpoint;

import com.example.demorest.model.User;
import com.example.demorest.service.UserService;
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
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllUsers() throws Exception {
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
        when(userService.getAllUsers()).thenReturn(allUsers);
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    public void getUserById() throws Exception {
        User user = User.builder()
                .name("poxos2")
                .email("poxos2@yandex.ru")
                .password("kondoloz2")
                .build();
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        mockMvc.perform(get("/user/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    public void addUser() throws Exception {
        User user = User.builder()
                .name("poxos2")
                .role("sadfas")
                .surname("Safgfsa")
                .email("poxos2@yandex.ru")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(user);
        when(userService.addUser(user)).thenReturn(user);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    public void changeUsers() throws Exception {
        User user = User.builder()
                .name("poxos2")
                .role("sadfas")
                .surname("Safgfsa")
                .email("poxos2@yandex.ru")
                .build();
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        user = User.builder()
                .name("poxos001")
                .role("sadfajhks")
                .surname("Safhgjgfsa")
                .email("poxos2@hgjyandex.ru")
                .build();
        when(userService.changeUser(user.getId(), user)).thenReturn(Optional.of(user));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(user);
        mockMvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .param("id", Integer.toString(user.getId())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteById() throws Exception {
        User user = User.builder()
                .id(1)
                .name("poxos2")
                .role("sadfas")
                .surname("Safgfsa")
                .email("poxos2@yandex.ru")
                .build();
        mockMvc.perform(delete("/delete/"+user.getId()))
                .andExpect(status().isNotFound());
    }
    @Test
    public void deleteByIdOk() throws Exception {
        User user = User.builder()
                .name("poxos2")
                .role("sadfas")
                .surname("Safgfsa")
                .email("poxos2@yandex.ru")
                .build();
        when(userService.findById(user.getId())).thenReturn(Optional.of(user));
        mockMvc.perform(delete("/user/"+user.getId()))
                .andExpect(status().isOk());
        verify(userService).deleteById(user.getId());

    }
}
