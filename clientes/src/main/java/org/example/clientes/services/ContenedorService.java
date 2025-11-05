package org.example.clientes.services;

import org.example.clientes.models.Contenedor;
import org.example.clientes.repositories.ContenedorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ContenedorService {

    private final ContenedorRepository repo;

    public ContenedorService(ContenedorRepository repo) {
        this.repo = repo;
    }

    public List<Contenedor> listarPorCliente(Long idCliente) {
        return repo.findByClienteIdCliente(idCliente);
    }

    public Contenedor registrar(Contenedor c) {
        return repo.save(c);
    }
}
