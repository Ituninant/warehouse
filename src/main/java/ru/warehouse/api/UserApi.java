package ru.warehouse.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.warehouse.model.Client;
import ru.warehouse.model.User;
import ru.warehouse.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {

    private final UserRepository userRepository;

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id=" + id + " not found"));
    }
}
