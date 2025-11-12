package org.example.logistica.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.logistica.models.Solicitud;
import org.example.logistica.services.SolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@Tag(name = "Solicitudes", description = "API para gestionar solicitudes de transporte de contenedores")
public class SolicitudController {

    private final SolicitudService service;

    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar todas las solicitudes",
            description = "Obtiene una lista completa de todas las solicitudes de transporte registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de solicitudes obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Solicitud.class))
            )
    })
    @GetMapping
    public List<Solicitud> listar() {
        return service.listarTodas();
    }

    @Operation(
            summary = "Obtener detalle de una solicitud",
            description = "Obtiene los detalles completos de una solicitud específica incluyendo costos y tiempos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Solicitud encontrada",
                    content = @Content(schema = @Schema(implementation = Solicitud.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Solicitud no encontrada"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> detalle(
            @Parameter(description = "ID de la solicitud", required = true, example = "1")
            @PathVariable Long id
    ) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear una nueva solicitud",
            description = "Registra una nueva solicitud de transporte de contenedor en el sistema. Calcula automáticamente el costo y tiempo estimado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Solicitud creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Solicitud.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos en la solicitud"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Solicitud> crear(
            @Parameter(description = "Datos de la solicitud a crear (idCliente, idContenedor)", required = true)
            @RequestBody Solicitud solicitud
    ) {
        Solicitud creada = service.crear(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
}