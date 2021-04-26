package com.example.demorest.endpoint;

import com.example.demorest.model.Category;
import com.example.demorest.service.CategoryService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findAll() throws Exception {
        List<Category> categories = Arrays.asList(Category.builder()
                        .name("asdfg")
                        .build(),
                Category.builder()
                        .name("qwertr")
                        .build(),
                Category.builder()
                        .name("iuiopio")
                        .build());
        when(categoryService.getAllCategory()).thenReturn(categories);
        mockMvc.perform(get("/category/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    public void add() throws Exception {
        Category category = Category.builder()
                .name("qwertr")
                .build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(category);
        when(categoryService.addCategory(category)).thenReturn(category);
        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(category.getName()));
    }

    @Test
    public void getCategory() throws Exception {
        Category category = Category.builder()
                .name("qwertr")
                .build();
        when(categoryService.findById(category.getId())).thenReturn(Optional.of(category));
        mockMvc.perform(get("/category/" + category.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(category.getName()));

    }

    @Test
    public void deleteCategoryNotFound() throws Exception {
        Category category = Category.builder()
                .name("qwertr")
                .build();
        mockMvc.perform(delete("/category/" + category.getId()))
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteCategorySuccess() throws Exception {
        Category category = Category.builder()
                .id(1)
                .name("qwertr")
                .build();
        when(categoryService.findById(category.getId())).thenReturn(Optional.of(category));
        mockMvc.perform(delete("/category/" + category.getId()))
                .andExpect(status().isOk());
        verify(categoryService).deleteCategory(category.getId());
    }

    @Test
    public void changeCategory() throws Exception {
        String changedName = "sport";
        Category category = Category.builder()
                .name("MAGAZINE")
                .build();
        when(categoryService.findById(category.getId())).thenReturn(Optional.of(category));
        category = Category.builder()
                .name(changedName)
                .build();
        when(categoryService.changeCategory(category.getId(), changedName)).thenReturn(category);
        mockMvc.perform(put("/category")
                .param("id", Integer.toString(category.getId()))
                .param("name", changedName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(category.getName()));

    }
}
