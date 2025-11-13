package org.example.transporte.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.transporte.models.Camion;
import org.example.transporte.services.CamionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/camiones")
@Tag(name = "Camiones", description = "API para gestionar camiones y su disponibilidad")
public class CamionController {

    private final CamionService service;

    public CamionController(CamionService service) {
        this.service = service;
    }

    // ====================== LISTAR TODOS ======================
    @Operation(summary = "Listar todos los camiones")
    @GetMapping
    public List<Camion> listarTodos() {
        return service.listarTodos();
    }

    // ====================== DETALLE POR PATENTE ======================
    @Operation(summary = "Obtener detalle de un camión por patente")
    @GetMapping("/{patente}")
    public ResponseEntity<Camion> detalle(
            @Parameter(description = "Patente del camión", example = "AA123BB")
            @PathVariable String patente
    ) {
        return service.buscarPorPatente(patente)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ====================== CREAR CAMIÓN ======================
    @Operation(summary = "Registrar un nuevo camión")
    @PostMapping
    public ResponseEntity<Camion> crear(@RequestBody Camion camion) {
        Camion creado = service.crear(camion);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // ====================== ACTUALIZAR CAMIÓN ======================
    @Operation(summary = "Actualizar datos de un camión existente")
    @PutMapping("/{patente}")
    public ResponseEntity<Camion> actualizar(
            @PathVariable String patente,
            @RequestBody Camion datos
    ) {
        Camion actualizado = service.actualizar(patente, datos);
        return ResponseEntity.ok(actualizado);
    }

    // ====================== ELIMINAR CAMIÓN ======================
    @Operation(summary = "Eliminar un camión")
    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminar(@PathVariable String patente) {
        service.eliminar(patente);
        return ResponseEntity.noContent().build();
    }

    // ====================== LISTAR DISPONIBLES ======================
    @Operation(summary = "Listar camiones disponibles")
    @GetMapping("/disponibles")
    public List<Camion> listarDisponibles() {
        return service.listarDisponibles();
    }

    // ====================== VALIDAR CAPACIDAD ======================
    @Operation(
            summary = "Validar capacidad de un camión",
            description = "Verifica si el camión puede transportar el peso y volumen indicados."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Resultado de la validación",
                    content = @Content(schema = @Schema(implementation = Boolean.class))
            )
    })
    @GetMapping("/{patente}/validar-capacidad")
    public ResponseEntity<Boolean> validarCapacidad(
            @PathVariable String patente,
            @RequestParam BigDecimal peso,
            @RequestParam BigDecimal volumen
    ) {
        boolean puede = service.tieneCapacidad(patente, peso, volumen);
        return ResponseEntity.ok(puede);
    }
}
