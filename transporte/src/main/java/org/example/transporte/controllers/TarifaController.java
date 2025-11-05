package org.example.transporte.controllers;

import org.example.transporte.models.Tarifa;
import org.example.transporte.services.TarifaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    private final TarifaService service;

    public TarifaController(TarifaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tarifa> listar() {
        return service.listarTodas();
    }

    @PostMapping
    public Tarifa crear(@RequestBody Tarifa tarifa) {
        return service.crear(tarifa);
    }

    @PutMapping("/{idTarifa}")
    public ResponseEntity<Tarifa> actualizar(@PathVariable Long idTarifa,
                                             @RequestBody Tarifa cambios) {
        Tarifa actualizada = service.actualizar(idTarifa, cambios);
        return ResponseEntity.ok(actualizada);
    }
}
