package org.example.transporte.controllers;

import org.example.transporte.models.Camion;
import org.example.transporte.services.CamionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/camiones")
public class CamionController {

    private final CamionService service;

    public CamionController(CamionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Camion> listarTodos(
            @RequestParam(name = "soloDisponibles", required = false, defaultValue = "false")
            boolean soloDisponibles) {

        return soloDisponibles ? service.listarDisponibles() : service.listarTodos();
    }

    @GetMapping("/{patente}")
    public ResponseEntity<Camion> detalle(@PathVariable String patente) {
        return service.buscarPorPatente(patente)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Camion crear(@RequestBody Camion camion) {
        return service.crear(camion);
    }

    @PutMapping("/{patente}/disponibilidad")
    public ResponseEntity<Camion> cambiarDisponibilidad(
            @PathVariable String patente,
            @RequestParam boolean disponible) {

        Camion actualizado = service.actualizarDisponibilidad(patente, disponible);
        return ResponseEntity.ok(actualizado);
    }
}
