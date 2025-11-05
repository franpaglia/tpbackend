package org.example.logistica.services;

import org.example.logistica.models.Solicitud;
import org.example.logistica.repositories.SolicitudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitudService {

    private final SolicitudRepository repo;

    public SolicitudService(SolicitudRepository repo) {
        this.repo = repo;
    }

    public List<Solicitud> listarTodas() {
        return repo.findAll();
    }

    public Optional<Solicitud> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Solicitud crear(Solicitud solicitud) {
        // acá después podés calcular costoEstimado, tiempoEstimado, etc.
        return repo.save(solicitud);
    }
}
