package ru.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.warehouse.model.SellItem;


public interface SellItemRepository extends JpaRepository<SellItem, Integer> {
}