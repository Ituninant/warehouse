package ru.warehouse.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.warehouse.model.Sell;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellDto {

    Integer id;

    Sell.SellType sellType;

    Sell.PaymentType paymentType;

    String documentNumber;

    LocalDate date;

    BigDecimal sum;

    String warehouse;

    String client;

    String employee;

    Sell.Status status;

    String address;

    BigDecimal purchaseSum;

    BigDecimal profit;

    List<ProductCountDto> sellingProducts;

}
