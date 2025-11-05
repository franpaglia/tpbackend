package org.example.transporte.services;

import org.example.transporte.models.Deposito;
import org.example.transporte.repositories.DepositoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositoService {

    private final DepositoRepository repo;

    public DepositoService(DepositoRepository repo) {
        this.repo = repo;
    }

    public List<Deposito> listarTodos() {
        return repo.findAll();
    }

    public Deposito crear(Deposito deposito) {
        return repo.save(deposito);
    }
}
