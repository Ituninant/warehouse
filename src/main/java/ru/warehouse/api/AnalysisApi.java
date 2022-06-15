package ru.warehouse.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.warehouse.api.dto.AbcAnalysisDto;
import ru.warehouse.repository.SellRepository;

import java.util.List;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisApi {

    private final SellRepository sellRepository;

    @GetMapping("abc")
    public List<AbcAnalysisDto> getAbcAnalysis() {
        return sellRepository.getAbcAnalysis();
    }

}
