package com.example.demorest.endpoint;

import com.example.demorest.model.Category;
import com.example.demorest.repository.CategoryRepository;
import com.example.demorest.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

 private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> allCategory = categoryService.getAllCategory();
        return ResponseEntity.ok(allCategory);

    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category saveCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(saveCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable("id") int id) {
        Optional<Category> byId = categoryService.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") int id) {
        if (categoryService.findById(id).isPresent()) {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<Category> changeCategory(@RequestParam Integer id, @RequestParam String name) {
        Optional<Category> categoryById = categoryService.findById(id);
        if (categoryById.isPresent()) {
         categoryService.changeCategory(id,name);
        }
        return ResponseEntity.notFound().build();
    }

}
