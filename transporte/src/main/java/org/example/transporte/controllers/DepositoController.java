package org.example.transporte.controllers;

import org.example.transporte.models.Deposito;
import org.example.transporte.services.DepositoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depositos")
public class DepositoController {

    private final DepositoService service;

    public DepositoController(DepositoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Deposito> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public Deposito crear(@RequestBody Deposito deposito) {
        return service.crear(deposito);
    }
}
