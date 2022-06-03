package ru.warehouse.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.warehouse.api.dto.WarehouseStateDto;
import ru.warehouse.model.Product;
import ru.warehouse.model.Warehouse;
import ru.warehouse.model.WarehouseState;
import ru.warehouse.repository.WarehouseStateRepository;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/warehouseStates")
@RequiredArgsConstructor
public class WarehouseStateApi {

    private final WarehouseStateRepository warehouseStateRepository;

    @GetMapping
    public List<WarehouseStateDto> getAll() {
        return warehouseStateRepository.findAll().stream().map(this::mapToDto).collect(toList());
    }

    @GetMapping("{id}")
    public WarehouseStateDto getById(@PathVariable Integer id) {
        return mapToDto(warehouseStateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("WarehouseState with id=" + id + " not found")));
    }

    private WarehouseStateDto mapToDto(WarehouseState warehouseState) {
        Warehouse warehouse = warehouseState.getWarehouse();
        Product product = warehouseState.getProduct();
        return WarehouseStateDto.builder()
                .warehouseId(warehouse.getId())
                .warehouseName(warehouse.getName())
                .productId(product.getId())
                .productFullName(product.getName())
                .productGroup(product.getGroup())
                .productUnits(product.getUnits())
                .count(warehouseState.getCount())
                .purchasePrice(product.getPurchasePrice())
                .sellPrice(product.getSellPrice())
                .wholesalePrice(product.getWholesalePrice())
                .totalPurchase(product.getPurchasePrice().multiply(BigDecimal.valueOf(warehouseState.getCount())))
                .build();
    }
}
