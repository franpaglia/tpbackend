package org.example.logistica.controllers;

import org.example.logistica.models.Solicitud;
import org.example.logistica.services.SolicitudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService service;

    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    @GetMapping
    public List<Solicitud> listar() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> detalle(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Solicitud> crear(@RequestBody Solicitud solicitud) {
        Solicitud creada = service.crear(solicitud);
        return ResponseEntity.ok(creada);
    }
}
