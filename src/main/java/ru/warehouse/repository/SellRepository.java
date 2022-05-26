package ru.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.warehouse.model.Sell;


public interface SellRepository extends JpaRepository<Sell, Integer> {
}