package ru.warehouse.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.warehouse.model.Obtaining;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ObtainingDto {

    Integer id;

    Obtaining.OperationType operationType;

    LocalDate documentDate;

    String warehouse;

    String supplier;

    String employee;

    Obtaining.PaymentStatus paymentStatus;

    BigDecimal sum;

    List<ProductCountDto> obtainingProducts;

}
