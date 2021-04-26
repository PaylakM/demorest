package com.example.demorest.service;

import com.example.demorest.model.Category;
import com.example.demorest.repository.CategoryRepository;
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
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void getAllTest() {
        List<Category> categories = Arrays.asList(Category.builder()
                        .name("asdfg")
                        .build(),
                Category.builder()
                        .name("qwertr")
                        .build(),
                Category.builder()
                        .name("iuiopio")
                        .build());
        when(categoryRepository.findAll()).thenReturn(categories);
        List<Category> allCategory = categoryService.getAllCategory();
        assertEquals(categories.size(), allCategory.size());
    }

    @Test
    public void addCategory() {
        Category category = Category.builder()
                .name("MAGAZINE")
                .build();
        when(categoryRepository.save(category)).thenReturn(category);
        Category category1 = categoryService.addCategory(category);
        assertEquals(category.getId(), category1.getId());
    }

    @Test
    public void findById() {
        Category category = Category.builder()
                .name("MAGAZINE")
                .build();
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        Optional<Category> byId = categoryService.findById(category.getId());
        assertTrue(byId.isPresent());
        assertEquals(byId, Optional.of(category));
    }

    @Test
    public void change() {
        String name = "AAA";
        Category category = Category.builder()
                .name("MAGAZINE")
                .build();
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(category);
        Category category1 = categoryService.changeCategory(category.getId(), name);
        assertEquals(name, category1.getName());
    }

    @Test
    public void deleteCategory(){
        Category category = Category.builder()
                .name("MAGAZINE")
                .build();
        categoryService.deleteCategory(category.getId());
        verify(categoryRepository).deleteById(category.getId());

    }



}
