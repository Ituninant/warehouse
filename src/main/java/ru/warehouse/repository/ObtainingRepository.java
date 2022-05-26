package ru.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.warehouse.model.Obtaining;


public interface ObtainingRepository extends JpaRepository<Obtaining, Integer> {
}