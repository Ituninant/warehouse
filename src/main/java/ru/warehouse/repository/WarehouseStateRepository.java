package ru.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.warehouse.model.WarehouseState;


public interface WarehouseStateRepository extends JpaRepository<WarehouseState, Integer> {
}