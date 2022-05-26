package ru.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.warehouse.model.ObtainingItem;


public interface ObtainingItemRepository extends JpaRepository<ObtainingItem, Integer> {
}