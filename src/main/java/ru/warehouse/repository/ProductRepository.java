package ru.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.warehouse.model.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {
}