package ru.warehouse.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.warehouse.api.dto.ProductCountDto;
import ru.warehouse.api.dto.SellDto;
import ru.warehouse.api.dto.SellSaveDto;
import ru.warehouse.model.Sell;
import ru.warehouse.model.SellItem;
import ru.warehouse.repository.ClientRepository;
import ru.warehouse.repository.ProductRepository;
import ru.warehouse.repository.SellItemRepository;
import ru.warehouse.repository.SellRepository;
import ru.warehouse.repository.UserRepository;
import ru.warehouse.repository.WarehouseRepository;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/sells")
@RequiredArgsConstructor
public class SellApi {

    private final SellRepository sellRepository;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final SellItemRepository sellItemRepository;

    @GetMapping
    public List<SellDto> getAll() {
        return sellRepository.findAll().stream().map(this::mapToDto).collect(toList());
    }

    @GetMapping("{id}")
    public SellDto getById(@PathVariable Integer id) {
        return mapToDto(sellRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sell with id=" + id + " not found")));
    }

    @PostMapping
    public void save(@RequestBody SellSaveDto sellSaveDto) {
        Sell savedSell = sellRepository.save(Sell.builder()
                .status(sellSaveDto.getStatus())
                .address(sellSaveDto.getAddress())
                .sellType(sellSaveDto.getSellType())
                .paymentType(sellSaveDto.getPaymentType())
                .documentNumber(sellSaveDto.getDocumentNumber())
                .client(clientRepository.findById(sellSaveDto.getClientId()).get())
                .employee(userRepository.findById(sellSaveDto.getEmployeeId()).get())
                .warehouse(warehouseRepository.findById(sellSaveDto.getWarehouseId()).get())
                .build());
        sellSaveDto.getSellingProducts().forEach(sp -> sellItemRepository.save(SellItem.builder()
                .sell(savedSell)
                .count(sp.getCount())
                .product(productRepository.findById(sp.getProductId()).get())
                .build())
        );
    }

    private SellDto mapToDto(Sell sell) {
        BigDecimal sellSum = getSellSum(sell);
        BigDecimal purchaseSum = getPurchaseSum(sell);
        return SellDto.builder()
                .id(sell.getId())
                .sellType(sell.getSellType())
                .paymentType(sell.getPaymentType())
                .documentNumber(sell.getDocumentNumber())
                .date(sell.getDate())
                .sum(sellSum)
                .warehouse(sell.getWarehouse().getName())
                .client(sell.getClient().getName())
                .employee(sell.getEmployee().getName())
                .status(sell.getStatus())
                .address(sell.getAddress())
                .purchaseSum(purchaseSum)
                .profit(sellSum.subtract(purchaseSum))
                .sellingProducts(sell.getSellItems().stream()
                        .map(si -> ProductCountDto.builder()
                                .count(si.getCount())
                                .product(si.getProduct().getName())
                                .build()
                        ).collect(toList()))
                .build();
    }

    private BigDecimal getSellSum(Sell sell) {
        return sell.getSellItems()
                .stream()
                .map(si -> (
                                sell.getSellType().equals(Sell.SellType.RETAIL) ?
                                si.getProduct().getSellPrice() :
                                si.getProduct().getWholesalePrice()
                            ).multiply(BigDecimal.valueOf(si.getCount()))
                )
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getPurchaseSum(Sell sell) {
        return sell.getSellItems()
                .stream()
                .map(si -> si.getProduct().getPurchasePrice().multiply(BigDecimal.valueOf(si.getCount())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
