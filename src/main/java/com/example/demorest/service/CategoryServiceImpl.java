package com.example.demorest.service;

import com.example.demorest.model.Category;
import com.example.demorest.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> changeCategory(int id, String name) {
        if (findById(id).isPresent()) {
            Category category = findById(id).get();
            category.setName(name);
            return Optional.of(categoryRepository.save(category));
        }
        return Optional.empty();
    }


}
