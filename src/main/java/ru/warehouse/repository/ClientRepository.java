package ru.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.warehouse.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}