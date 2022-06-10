package ru.warehouse.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.warehouse.api.dto.ObtainingDto;
import ru.warehouse.api.dto.ObtainingSaveDto;
import ru.warehouse.api.dto.ProductCountDto;
import ru.warehouse.model.Obtaining;
import ru.warehouse.model.ObtainingItem;
import ru.warehouse.repository.ObtainingItemRepository;
import ru.warehouse.repository.ObtainingRepository;
import ru.warehouse.repository.ProductRepository;
import ru.warehouse.repository.SupplierRepository;
import ru.warehouse.repository.UserRepository;
import ru.warehouse.repository.WarehouseRepository;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/obtainings")
@RequiredArgsConstructor
public class ObtainingApi {

    private final ObtainingRepository obtainingRepository;
    private final ObtainingItemRepository obtainingItemRepository;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    @GetMapping
    public List<ObtainingDto> getAll() {
        return obtainingRepository.findAll().stream().map(this::mapToDto).collect(toList());
    }

    @GetMapping("{id}")
    public ObtainingDto getById(@PathVariable Integer id) {
        return mapToDto(obtainingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Obtaining with id=" + id + " not found")));
    }

    @PostMapping
    public void save(@RequestBody ObtainingSaveDto obtainingSaveDto) {
        Obtaining savedObtaining = obtainingRepository.save(Obtaining.builder()
                .paymentStatus(obtainingSaveDto.getPaymentStatus())
                .operationType(obtainingSaveDto.getOperationType())
                .employee(userRepository.findById(obtainingSaveDto.getEmployeeId()).get())
                .supplier(supplierRepository.findById(obtainingSaveDto.getSupplierId()).get())
                .warehouse(warehouseRepository.findById(obtainingSaveDto.getWarehouseId()).get())
                .build());
        obtainingSaveDto.getObtainingProducts().forEach(p -> obtainingItemRepository.save(ObtainingItem.builder()
                .count(p.getCount())
                .obtaining(savedObtaining)
                .product(productRepository.findById(p.getProductId()).get())
                .build())
        );
    }

    private ObtainingDto mapToDto(Obtaining obtaining) {
        return ObtainingDto.builder()
                .id(obtaining.getId())
                .operationType(obtaining.getOperationType())
                .documentDate(obtaining.getDocumentDate())
                .warehouse(obtaining.getWarehouse().getName())
                .supplier(obtaining.getSupplier().getName())
                .employee(obtaining.getEmployee().getName())
                .paymentStatus(obtaining.getPaymentStatus())
                .sum(obtaining.getObtainingItems().stream()
                        .map(oi -> oi.getProduct()
                                .getPurchasePrice()
                                .multiply(BigDecimal.valueOf(oi.getCount()))
                        ).reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO)
                ).obtainingProducts(obtaining.getObtainingItems().stream()
                        .map(oi -> ProductCountDto.builder()
                                .count(oi.getCount())
                                .product(oi.getProduct().getName())
                                .build()
                        ).collect(toList()))
                .build();
    }
}
