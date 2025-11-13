package org.example.transporte.services;

import org.example.transporte.models.Tarifa;
import org.example.transporte.repositories.TarifaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifaService {

    private final TarifaRepository repo;

    public TarifaService(TarifaRepository repo) {
        this.repo = repo;
    }

    public List<Tarifa> listarTodas() {
        return repo.findAll();
    }

    public Optional<Tarifa> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Tarifa crear(Tarifa tarifa) {
        return repo.save(tarifa);
    }

    public Tarifa actualizar(Long id, Tarifa datos) {
        return repo.findById(id)
                .map(actual -> {
                    actual.setCamion(datos.getCamion());
                    actual.setCostoLtBase(datos.getCostoLtBase());
                    return repo.save(actual);
                })
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada"));
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
