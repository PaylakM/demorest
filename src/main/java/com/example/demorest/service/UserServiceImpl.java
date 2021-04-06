package com.example.demorest.service;

import com.example.demorest.model.User;
import com.example.demorest.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> changeUser(int id, User user) {
        if (userRepository.findById(id).isPresent()) {
            User changedUser = userRepository.findById(id).get();
            changedUser.setName(user.getName());
            changedUser.setSurname(user.getSurname());
            changedUser.setEmail(user.getEmail());
            changedUser.setPassword(user.getPassword());
            changedUser.setRole(user.getRole());
            return Optional.of(changedUser);
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);

    }

    @Override
    public Optional<User> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}
