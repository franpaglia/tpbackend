package org.example.logistica.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.logistica.models.Ruta;
import org.example.logistica.services.RutaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutas")
@Tag(name = "Rutas", description = "API para gestionar rutas de transporte con sus tramos")
public class RutaController {

    private final RutaService service;

    public RutaController(RutaService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar todas las rutas",
            description = "Obtiene todas las rutas registradas en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de rutas obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Ruta.class))
            )
    })
    @GetMapping
    public List<Ruta> listar() {
        return service.listarTodas();
    }

    @Operation(
            summary = "Obtener detalle de una ruta",
            description = "Obtiene los detalles completos de una ruta incluyendo todos sus tramos y depósitos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ruta encontrada",
                    content = @Content(schema = @Schema(implementation = Ruta.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ruta no encontrada"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Ruta> detalle(
            @Parameter(description = "ID de la ruta", required = true, example = "1")
            @PathVariable Long id
    ) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear una nueva ruta",
            description = "Crea una ruta con sus tramos asociados a una solicitud de transporte"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Ruta creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Ruta.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos en la solicitud"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ruta crear(
            @Parameter(description = "Datos de la ruta a crear (idSolicitud, cantTramos, cantDepositos)", required = true)
            @RequestBody Ruta ruta
    ) {
        return service.guardar(ruta);
    }
}