package org.example.transporte.services;

import org.example.transporte.models.Deposito;
import org.example.transporte.repositories.DepositoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepositoService {

    private final DepositoRepository repo;

    public DepositoService(DepositoRepository repo) {
        this.repo = repo;
    }

    public List<Deposito> listarTodos() {
        return repo.findAll();
    }

    public Optional<Deposito> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Deposito crear(Deposito deposito) {
        return repo.save(deposito);
    }

    public Deposito actualizar(Long id, Deposito datos) {
        return repo.findById(id)
                .map(actual -> {
                    actual.setNombre(datos.getNombre());
                    actual.setDireccion(datos.getDireccion());
                    actual.setCoordDepositoLat(datos.getCoordDepositoLat());
                    actual.setCoordDepositoLong(datos.getCoordDepositoLong());
                    actual.setCostoEstadiaDiaria(datos.getCostoEstadiaDiaria());
                    return repo.save(actual);
                })
                .orElseThrow(() -> new RuntimeException("Depósito no encontrado"));
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
