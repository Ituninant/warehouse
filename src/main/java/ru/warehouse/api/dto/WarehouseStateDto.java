package ru.warehouse.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WarehouseStateDto {

    Integer warehouseId;

    String warehouseName;

    Integer productId;

    String productFullName;

    String productGroup;

    String productUnits;

    Integer count;

    BigDecimal purchasePrice;

    BigDecimal sellPrice;

    BigDecimal wholesalePrice;

    BigDecimal totalPurchase;

}
