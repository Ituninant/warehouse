package ru.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.warehouse.model.Supplier;


public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}