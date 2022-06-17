package ru.warehouse.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.warehouse.model.Product;
import ru.warehouse.repository.ProductRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductRepository productRepository;

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAllByDecommissioned(false);
    }

    @GetMapping("decommissioned")
    public List<Product> getAllDecommissioned() {
        return productRepository.findAllByDecommissioned(true);
    }

    @GetMapping("{id}")
    public Product getById(@PathVariable Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with id=" + id + " not found"));
    }

    @PostMapping
    public Product save(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PatchMapping("{id}/discard")
    public Product discard(@PathVariable Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with id=" + id + " not found"));
        product.setDecommissioned(true);
        return productRepository.save(product);
    }
}
