package ru.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.warehouse.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {
}