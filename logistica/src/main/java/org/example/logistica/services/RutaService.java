package org.example.logistica.services;

import org.example.logistica.models.Ruta;
import org.example.logistica.repositories.RutaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutaService {

    private final RutaRepository repo;

    public RutaService(RutaRepository repo) {
        this.repo = repo;
    }

    public List<Ruta> listarTodas() {  // 👈 NUEVO
        return repo.findAll();
    }

    public Optional<Ruta> buscarPorId(Long idRuta) {
        return repo.findById(idRuta);
    }

    public Ruta guardar(Ruta ruta) {
        return repo.save(ruta);
    }
}