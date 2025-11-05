package org.example.clientes.services;

import org.example.clientes.models.Cliente;
import org.example.clientes.repositories.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> listarTodos() {
        return repo.findAll();
    }

    public Cliente registrar(Cliente c) {
        return repo.save(c);
    }
}
