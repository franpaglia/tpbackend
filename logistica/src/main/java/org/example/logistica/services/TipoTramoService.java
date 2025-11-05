package org.example.logistica.services;

import org.example.logistica.models.TipoTramo;
import org.example.logistica.repositories.TipoTramoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoTramoService {

    private final TipoTramoRepository repo;

    public TipoTramoService(TipoTramoRepository repo) {
        this.repo = repo;
    }

    public List<TipoTramo> listarTodos() {
        return repo.findAll();
    }
}
