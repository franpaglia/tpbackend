package org.example.logistica.controllers;

import org.example.logistica.models.TipoTramo;
import org.example.logistica.services.TipoTramoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-tramo")
public class TipoTramoController {

    private final TipoTramoService service;

    public TipoTramoController(TipoTramoService service) {
        this.service = service;
    }

    @GetMapping
    public List<TipoTramo> listar() {
        return service.listarTodos();
    }
}
