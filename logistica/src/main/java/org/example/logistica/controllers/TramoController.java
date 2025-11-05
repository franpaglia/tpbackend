package org.example.logistica.controllers;

import org.example.logistica.models.Tramo;
import org.example.logistica.services.TramoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tramos")
public class TramoController {

    private final TramoService service;

    public TramoController(TramoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tramo> detalle(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ruta/{idRuta}")
    public List<Tramo> listarPorRuta(@PathVariable Long idRuta) {
        return service.listarPorRuta(idRuta);
    }

    @PostMapping
    public Tramo crear(@RequestBody Tramo tramo) {
        return service.crear(tramo);
    }

    @PutMapping("/{id}/inicio")
    public ResponseEntity<Tramo> registrarInicio(@PathVariable Long id, @RequestBody String fechaHora) {
        Tramo actualizado = service.registrarInicio(id, LocalDateTime.parse(fechaHora));
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/{id}/fin")
    public ResponseEntity<Tramo> registrarFin(@PathVariable Long id, @RequestBody String fechaHora) {
        Tramo actualizado = service.registrarFin(id, LocalDateTime.parse(fechaHora));
        return ResponseEntity.ok(actualizado);
    }
}
