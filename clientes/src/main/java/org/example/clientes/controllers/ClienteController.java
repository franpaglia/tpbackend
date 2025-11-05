package org.example.clientes.controllers;

import org.example.clientes.models.Cliente;
import org.example.clientes.services.ClienteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cliente> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        return service.registrar(cliente);
    }
}
