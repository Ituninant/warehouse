package ru.warehouse.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@ToString
public class XyzAnalysisRowDto implements Comparable<XyzAnalysisRowDto> {

    Integer productId;

    String productName;

    Map<Integer, Integer> countByMonthMap;

    Long sum;

    Double avg;

    Double rmsDeviation;

    Integer variationCoef;

    String rank;

    @Override
    public int compareTo(XyzAnalysisRowDto o) {
        return variationCoef.compareTo(o.variationCoef);
    }
}
