package ru.warehouse.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.warehouse.model.Client;
import ru.warehouse.model.Obtaining;
import ru.warehouse.model.ObtainingItem;
import ru.warehouse.model.Product;
import ru.warehouse.model.Sell;
import ru.warehouse.model.SellItem;
import ru.warehouse.model.Supplier;
import ru.warehouse.model.User;
import ru.warehouse.model.Warehouse;
import ru.warehouse.model.WarehouseState;
import ru.warehouse.repository.ClientRepository;
import ru.warehouse.repository.ObtainingItemRepository;
import ru.warehouse.repository.ObtainingRepository;
import ru.warehouse.repository.ProductRepository;
import ru.warehouse.repository.SellItemRepository;
import ru.warehouse.repository.SellRepository;
import ru.warehouse.repository.SupplierRepository;
import ru.warehouse.repository.UserRepository;
import ru.warehouse.repository.WarehouseRepository;
import ru.warehouse.repository.WarehouseStateRepository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DbFiller {

    private final WarehouseRepository warehouseRepository;
    private final UserRepository userRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final WarehouseStateRepository warehouseStateRepository;
    private final ObtainingRepository obtainingRepository;
    private final ObtainingItemRepository obtainingItemRepository;
    private final ClientRepository clientRepository;
    private final SellRepository sellRepository;
    private final SellItemRepository sellItemRepository;

    @PostConstruct
    void fill() {
        Warehouse warehouse1 = warehouseRepository.save(Warehouse.builder()
                .name("Склад 1")
                .address("test")
                .addDate(LocalDate.now())
                .build());
        Warehouse warehouse2 = warehouseRepository.save(Warehouse.builder()
                .name("Склад 2")
                .address("test")
                .addDate(LocalDate.now())
                .build());

        User employee = userRepository.save(User.builder()
                .login("employee")
                .password("123")
                .role(User.Role.EMPLOYEE)
                .name("test")
                .phone("test")
                .position("test")
                .address("test")
                .build());

        Supplier supplier = supplierRepository.save(Supplier.builder()
                .name("test")
                .address("test")
                .country("test")
                .city("test")
                .phone("test")
                .directorName("test")
                .addDate(LocalDate.now())
                .build());

        Product product = productRepository.save(Product.builder()
                .barecode(UUID.randomUUID())
                .type("test")
                .name("test")
                .group("test")
                .purchasePrice(BigDecimal.TEN)
                .sellPrice(BigDecimal.valueOf(20))
                .wholesalePrice(BigDecimal.valueOf(15))
                .units("test")
                .build());

        WarehouseState warehouseState1 = warehouseStateRepository.save(WarehouseState.builder()
                .warehouse(warehouse1)
                .product(product)
                .count(10)
                .build());
        WarehouseState warehouseState2 = warehouseStateRepository.save(WarehouseState.builder()
                .warehouse(warehouse2)
                .product(product)
                .count(10)
                .build());

        Obtaining obtaining = obtainingRepository.save(Obtaining.builder()
                .operationType(Obtaining.OperationType.ARRIVAL)
                .documentDate(LocalDate.now())
                .warehouse(warehouse1)
                .supplier(supplier)
                .employee(employee)
                .paymentStatus(Obtaining.PaymentStatus.PAYED)
                .build());

        ObtainingItem obtainingItem = obtainingItemRepository.save(ObtainingItem.builder()
                .obtaining(obtaining)
                .product(product)
                .count(10)
                .build());

        Client client = clientRepository.save(Client.builder()
                .name("test")
                .fullName("test")
                .type(Client.Type.UL)
                .industry("test")
                .address("test")
                .postAddress("test")
                .phone("test")
                .director("test")
                .addDate(LocalDate.now())
                .build());

        Sell sell = sellRepository.save(Sell.builder()
                .sellType(Sell.SellType.RETAIL)
                .paymentType(Sell.PaymentType.TRANSFER)
                .documentNumber("test")
                .date(LocalDate.now())
                .warehouse(warehouse1)
                .client(client)
                .employee(employee)
                .status(Sell.Status.SALES)
                .address("test")
                .build());

        SellItem sellItem = sellItemRepository.save(SellItem.builder()
                .product(product)
                .sell(sell)
                .count(10)
                .build());

    }

}
