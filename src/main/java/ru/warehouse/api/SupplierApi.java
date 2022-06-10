package ru.warehouse.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.warehouse.model.Supplier;
import ru.warehouse.repository.SupplierRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierApi {

    private final SupplierRepository supplierRepository;

    @GetMapping
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @GetMapping("{id}")
    public Supplier getById(@PathVariable Integer id) {
        return supplierRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier with id=" + id + " not found"));
    }

    @PostMapping
    public Supplier save(@RequestBody Supplier supplier) {
        return supplierRepository.save(supplier);
    }
}
