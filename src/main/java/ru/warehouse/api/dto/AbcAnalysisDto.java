package ru.warehouse.api.dto;

import java.math.BigDecimal;

public interface AbcAnalysisDto {
    Integer getId();

    String getName();

    BigDecimal getProfit();

    BigDecimal getContribution();

    BigDecimal getTotal();

    String getRank();
}
