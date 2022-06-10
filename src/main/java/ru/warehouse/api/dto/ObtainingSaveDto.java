package ru.warehouse.api.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.warehouse.model.Obtaining;

import java.util.List;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ObtainingSaveDto {

    Obtaining.OperationType operationType;
    
    Integer warehouseId;

    Integer supplierId;

    Integer employeeId;

    Obtaining.PaymentStatus paymentStatus;

    List<ProductCountDto> obtainingProducts;

}
