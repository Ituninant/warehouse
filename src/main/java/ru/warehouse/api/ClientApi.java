package ru.warehouse.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.warehouse.model.Client;
import ru.warehouse.repository.ClientRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientApi {

    private final ClientRepository clientRepository;

    @GetMapping
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @GetMapping("{id}")
    public Client getById(@PathVariable Integer id) {
        return clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client with id=" + id + " not found"));
    }

    @PostMapping
    public Client save(@RequestBody Client client) {
        return clientRepository.save(client);
    }
}
