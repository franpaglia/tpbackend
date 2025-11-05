package org.example.transporte.services;

import org.example.transporte.models.Tarifa;
import org.example.transporte.repositories.TarifaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarifaService {

    private final TarifaRepository repo;

    public TarifaService(TarifaRepository repo) {
        this.repo = repo;
    }

    public List<Tarifa> listarTodas() {
        return repo.findAll();
    }

    public Tarifa actualizar(Long idTarifa, Tarifa cambios) {
        Tarifa tarifa = repo.findById(idTarifa)
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada"));

        tarifa.setCostoLtBase(cambios.getCostoLtBase());
        // acá podrías copiar más campos si agregás otros componentes de costo

        return repo.save(tarifa);
    }

    public Tarifa crear(Tarifa tarifa) {
        return repo.save(tarifa);
    }
}
