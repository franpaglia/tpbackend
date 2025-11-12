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

import java.util.List;

@RestController
@RequestMapping("/api/camiones")
@Tag(name = "Camiones", description = "API para gestionar la flota de camiones y su disponibilidad")
public class CamionController {

    private final CamionService service;

    public CamionController(CamionService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar camiones",
            description = "Obtiene todos los camiones o solo los disponibles según el parámetro"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de camiones obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Camion.class))
            )
    })
    @GetMapping
    public List<Camion> listarTodos(
            @Parameter(description = "Filtrar solo camiones disponibles", example = "false")
            @RequestParam(name = "soloDisponibles", required = false, defaultValue = "false")
            boolean soloDisponibles
    ) {
        return soloDisponibles ? service.listarDisponibles() : service.listarTodos();
    }

    @Operation(
            summary = "Obtener detalle de un camión",
            description = "Obtiene los detalles completos de un camión específico por su patente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Camión encontrado",
                    content = @Content(schema = @Schema(implementation = Camion.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Camión no encontrado"
            )
    })
    @GetMapping("/{patente}")
    public ResponseEntity<Camion> detalle(
            @Parameter(description = "Patente del camión", required = true, example = "ABC123")
            @PathVariable String patente
    ) {
        return service.buscarPorPatente(patente)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Registrar un nuevo camión",
            description = "Agrega un nuevo camión a la flota con sus capacidades y costos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Camión creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Camion.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos en la solicitud"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Camion crear(
            @Parameter(description = "Datos del camión a registrar (patente, capacidades, costos)", required = true)
            @RequestBody Camion camion
    ) {
        return service.crear(camion);
    }

    @Operation(
            summary = "Cambiar disponibilidad de un camión",
            description = "Actualiza el estado de disponibilidad de un camión (disponible/ocupado)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Disponibilidad actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = Camion.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Camión no encontrado"
            )
    })
    @PutMapping("/{patente}/disponibilidad")
    public ResponseEntity<Camion> cambiarDisponibilidad(
            @Parameter(description = "Patente del camión", required = true, example = "ABC123")
            @PathVariable String patente,
            @Parameter(description = "Nueva disponibilidad del camión", required = true, example = "true")
            @RequestParam boolean disponible
    ) {
        Camion actualizado = service.actualizarDisponibilidad(patente, disponible);
        return ResponseEntity.ok(actualizado);
    }
}