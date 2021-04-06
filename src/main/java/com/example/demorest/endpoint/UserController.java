package com.example.demorest.endpoint;

import com.example.demorest.model.User;
import com.example.demorest.repository.UserRepository;
import com.example.demorest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService  userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        Optional<User> byId = userService.getUserById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PutMapping
    public ResponseEntity<User> changeUsers(@RequestParam int id, @RequestBody User user) {
        Optional<User> userUpdated = userService.changeUser(id, user);
        if (userUpdated.isPresent()) {
            return ResponseEntity.ok(userUpdated.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping({"id"})
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        if (userService.getUserById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
