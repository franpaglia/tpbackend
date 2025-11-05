package org.example.logistica.controllers;

import org.example.logistica.models.Ruta;
import org.example.logistica.services.RutaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    private final RutaService service;

    public RutaController(RutaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ruta> detalle(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ruta crear(@RequestBody Ruta ruta) {
        return service.guardar(ruta);
    }
}
