package ru.warehouse.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.warehouse.model.Sell;

import java.util.List;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellSaveDto {
    Integer warehouseId;

    Sell.SellType sellType;

    Sell.PaymentType paymentType;

    String documentNumber;

    Integer employeeId;

    Integer clientId;

    String address;

    Sell.Status status;

    List<ProductCountDto> sellingProducts;
}
