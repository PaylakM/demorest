package com.example.demorest.service;

import com.example.demorest.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAllCategory();

    Category addCategory(Category category);

    Optional<Category> findById(int id);

    void deleteCategory(int id);

    Optional<Category> changeCategory(int id,String name);


}
