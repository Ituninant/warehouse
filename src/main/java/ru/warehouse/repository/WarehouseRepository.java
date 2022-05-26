package ru.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.warehouse.model.Warehouse;


public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
}