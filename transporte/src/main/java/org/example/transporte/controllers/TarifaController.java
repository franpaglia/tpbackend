package org.example.transporte.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.transporte.models.Tarifa;
import org.example.transporte.services.TarifaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
@Tag(name = "Tarifas", description = "API para gestionar tarifas y costos del sistema de transporte")
public class TarifaController {

    private final TarifaService service;

    public TarifaController(TarifaService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar todas las tarifas",
            description = "Obtiene todas las tarifas configuradas en el sistema (costo por litro, etc.)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de tarifas obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Tarifa.class))
            )
    })
    @GetMapping
    public List<Tarifa> listar() {
        return service.listarTodas();
    }

    @Operation(
            summary = "Obtener una tarifa por ID",
            description = "Devuelve la tarifa indicada por su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tarifa encontrada",
                    content = @Content(schema = @Schema(implementation = Tarifa.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tarifa no encontrada"
            )
    })
    @GetMapping("/{idTarifa}")
    public ResponseEntity<Tarifa> detalle(
            @Parameter(description = "ID de la tarifa", required = true, example = "1")
            @PathVariable Long idTarifa
    ) {
        return service.buscarPorId(idTarifa)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear una nueva tarifa",
            description = "Registra una nueva tarifa en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tarifa creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Tarifa.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos en la solicitud"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tarifa crear(
            @Parameter(description = "Datos de la tarifa a crear", required = true)
            @RequestBody Tarifa tarifa
    ) {
        return service.crear(tarifa);
    }

    @Operation(
            summary = "Actualizar una tarifa existente",
            description = "Modifica los valores de una tarifa específica (ej: actualizar costo del combustible)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tarifa actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = Tarifa.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tarifa no encontrada"
            )
    })
    @PutMapping("/{idTarifa}")
    public ResponseEntity<Tarifa> actualizar(
            @Parameter(description = "ID de la tarifa", required = true, example = "1")
            @PathVariable Long idTarifa,
            @Parameter(description = "Nuevos valores de la tarifa", required = true)
            @RequestBody Tarifa cambios
    ) {
        Tarifa actualizada = service.actualizar(idTarifa, cambios);
        return ResponseEntity.ok(actualizada);
    }

    @Operation(
            summary = "Eliminar una tarifa",
            description = "Elimina una tarifa del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Tarifa eliminada correctamente"
            )
    })
    @DeleteMapping("/{idTarifa}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la tarifa", required = true, example = "1")
            @PathVariable Long idTarifa
    ) {
        service.eliminar(idTarifa);
        return ResponseEntity.noContent().build();
    }
}
