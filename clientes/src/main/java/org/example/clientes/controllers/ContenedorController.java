package org.example.clientes.controllers;

import org.example.clientes.models.Contenedor;
import org.example.clientes.services.ContenedorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contenedores")
public class ContenedorController {

    private final ContenedorService service;

    public ContenedorController(ContenedorService service) {
        this.service = service;
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Contenedor> listarPorCliente(@PathVariable Long idCliente) {
        return service.listarPorCliente(idCliente);
    }

    @PostMapping
    public Contenedor crear(@RequestBody Contenedor contenedor) {
        return service.registrar(contenedor);
    }
}
