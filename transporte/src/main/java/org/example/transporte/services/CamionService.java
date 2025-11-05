package org.example.transporte.services;

import org.example.transporte.models.Camion;
import org.example.transporte.repositories.CamionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CamionService {

    private final CamionRepository repo;

    public CamionService(CamionRepository repo) {
        this.repo = repo;
    }

    public List<Camion> listarTodos() {
        return repo.findAll();
    }

    public List<Camion> listarDisponibles() {
        return repo.findByDisponibilidadTrue();
    }

    public Optional<Camion> buscarPorPatente(String patente) {
        return repo.findById(patente);
    }

    public Camion crear(Camion camion) {
        // acá podrías validar que no exista la patente, etc.
        return repo.save(camion);
    }

    public Camion actualizarDisponibilidad(String patente, boolean disponible) {
        Camion camion = repo.findById(patente)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado"));
        camion.setDisponibilidad(disponible);
        return repo.save(camion);
    }
}
