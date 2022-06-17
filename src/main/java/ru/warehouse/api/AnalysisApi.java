package ru.warehouse.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.warehouse.api.dto.AbcAnalysisDto;
import ru.warehouse.api.dto.XyzAnalysisRowDto;
import ru.warehouse.model.Product;
import ru.warehouse.model.Sell;
import ru.warehouse.repository.SellRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisApi {

    private final SellRepository sellRepository;

    @GetMapping("abc")
    public List<AbcAnalysisDto> getAbcAnalysis() {
        return sellRepository.getAbcAnalysis();
    }

    @GetMapping("xyz")
    public List<XyzAnalysisRowDto>  getXyzAnalysis() {
        List<Sell> sells = sellRepository.findAll();
        List<XyzAnalysisRowDto> resultDto = new ArrayList<>();
        Map<Product, Map<Integer, Integer>> map = new HashMap<>();
        Map<Integer, Integer> defaultMap = getDefaultCountByMonthMap();
        sells.stream().flatMap(s -> s.getSellItems().stream()).forEach(si -> {
            if (si.getProduct().isDecommissioned()) {
                return;
            }
            Map<Integer, Integer> value = new HashMap<>(map.getOrDefault(si.getProduct(), defaultMap));
            value.merge(si.getSell().getDate().getMonthValue(), si.getCount(), Integer::sum);
            map.put(si.getProduct(), value);
        });
        map.forEach((product, countByMonthMap) -> {
            XyzAnalysisRowDto.XyzAnalysisRowDtoBuilder rowDtoBuilder = XyzAnalysisRowDto.builder();
            rowDtoBuilder.productId(product.getId());
            rowDtoBuilder.productName(product.getName());
            rowDtoBuilder.countByMonthMap(countByMonthMap);
            IntSummaryStatistics intSummaryStatistics = countByMonthMap.values().stream().mapToInt(i -> i).summaryStatistics();
            rowDtoBuilder.sum(intSummaryStatistics.getSum());
            rowDtoBuilder.avg(format(intSummaryStatistics.getAverage()));
            double rmsDeviation = getRmsDeviation(countByMonthMap, intSummaryStatistics.getAverage(), intSummaryStatistics.getCount());
            rowDtoBuilder.rmsDeviation(format(rmsDeviation));
            int variationCoef = BigDecimal.valueOf(rmsDeviation / intSummaryStatistics.getAverage() * 100).intValue();
            rowDtoBuilder.variationCoef(variationCoef);
            rowDtoBuilder.rank(getXyzRank(variationCoef));
            resultDto.add(rowDtoBuilder.build());
        });
        Collections.sort(resultDto);
        return resultDto;
    }

    private Double getRmsDeviation(Map<Integer, Integer> countByMonthMap, Double avg, Long count) {
        return Math.sqrt(countByMonthMap.values().stream().mapToDouble(i -> (double) i).reduce(0, (res, countByMonth) -> res + Math.pow(countByMonth - avg, 2)) / count);
    }

    private Map<Integer, Integer> getDefaultCountByMonthMap() {
        return Stream.iterate(1, i -> ++i).limit(12).collect(Collectors.toMap(Function.identity(), i -> 0));
    }

    private String getXyzRank(int variationCoef) {
        return variationCoef <= 15 ? "X" : variationCoef <= 25 ? "Y" : "Z";
    }

    private Double format(Double val) {
        return BigDecimal.valueOf(val).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
