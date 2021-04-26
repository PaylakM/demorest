package com.example.demorest.repository;

import com.example.demorest.model.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    public void findByIdTest() {
        Category category = new Category();
        category.setName("NEWS");
        categoryRepository.save(category);
        Optional<Category> byId = categoryRepository.findById(category.getId());
        assertTrue(byId.isPresent());
        assertEquals(byId.get().getName(), category.getName());
    }

    @Test
    public void findAllTest() {
        List<Category> allCategory = Arrays.asList(Category.builder()
                        .name("asdfg")
                        .build(),
                Category.builder()
                        .name("qwertr")
                        .build(),
                Category.builder()
                        .name("iuiopio")
                        .build());
        categoryRepository.saveAll(allCategory);
        assertEquals(allCategory.size(), categoryRepository.findAll().size());

    }


}
