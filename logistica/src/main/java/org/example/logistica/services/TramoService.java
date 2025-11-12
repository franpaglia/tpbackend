package org.example.logistica.services;

import org.example.logistica.models.Tramo;
import org.example.logistica.repositories.TramoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TramoService {

    private final TramoRepository repo;

    public TramoService(TramoRepository repo) {
        this.repo = repo;
    }

    public List<Tramo> listarTodos() {  // 👈 NUEVO
        return repo.findAll();
    }

    public Optional<Tramo> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<Tramo> listarPorRuta(Long idRuta) {
        return repo.findByRutaIdRuta(idRuta);
    }

    public Tramo crear(Tramo tramo) {
        return repo.save(tramo);
    }

    public Tramo registrarInicio(Long idTramo, LocalDateTime fechaHoraInicio) {
        Tramo tramo = repo.findById(idTramo)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado"));
        tramo.setFechaHoraInicio(fechaHoraInicio);
        return repo.save(tramo);
    }

    public Tramo registrarFin(Long idTramo, LocalDateTime fechaHoraFin) {
        Tramo tramo = repo.findById(idTramo)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado"));
        tramo.setFechaHoraFin(fechaHoraFin);
        return repo.save(tramo);
    }
}