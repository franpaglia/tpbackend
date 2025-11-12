package org.example.logistica.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.logistica.models.Tramo;
import org.example.logistica.services.TramoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tramos")
@Tag(name = "Tramos", description = "API para gestionar tramos de transporte y su seguimiento")
public class TramoController {

    private final TramoService service;

    public TramoController(TramoService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar todos los tramos",
            description = "Obtiene una lista completa de todos los tramos registrados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de tramos obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Tramo.class))
            )
    })
    @GetMapping
    public List<Tramo> listar() {
        return service.listarTodos();
    }

    @Operation(
            summary = "Obtener detalle de un tramo",
            description = "Obtiene los detalles completos de un tramo específico incluyendo camión asignado y coordenadas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tramo encontrado",
                    content = @Content(schema = @Schema(implementation = Tramo.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tramo no encontrado"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Tramo> detalle(
            @Parameter(description = "ID del tramo", required = true, example = "1")
            @PathVariable Long id
    ) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Listar tramos de una ruta",
            description = "Obtiene todos los tramos que pertenecen a una ruta específica"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de tramos obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Tramo.class))
            )
    })
    @GetMapping("/ruta/{idRuta}")
    public List<Tramo> listarPorRuta(
            @Parameter(description = "ID de la ruta", required = true, example = "1")
            @PathVariable Long idRuta
    ) {
        return service.listarPorRuta(idRuta);
    }

    @Operation(
            summary = "Crear un nuevo tramo",
            description = "Crea un tramo de transporte con origen, destino y camión asignado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Tramo creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Tramo.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos en la solicitud"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tramo crear(
            @Parameter(description = "Datos del tramo a crear (coordenadas, idRuta, idCamion, tipoTramo)", required = true)
            @RequestBody Tramo tramo
    ) {
        return service.crear(tramo);
    }

    @Operation(
            summary = "Registrar inicio de tramo",
            description = "Marca el inicio de un tramo cuando el transportista comienza el traslado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Inicio de tramo registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = Tramo.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tramo no encontrado"
            )
    })
    @PutMapping("/{id}/inicio")
    public ResponseEntity<Tramo> registrarInicio(
            @Parameter(description = "ID del tramo", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Fecha y hora de inicio en formato ISO (YYYY-MM-DDTHH:mm:ss)", required = true, example = "2025-11-11T08:30:00")
            @RequestBody String fechaHora
    ) {
        Tramo actualizado = service.registrarInicio(id, LocalDateTime.parse(fechaHora));
        return ResponseEntity.ok(actualizado);
    }

    @Operation(
            summary = "Registrar fin de tramo",
            description = "Marca el fin de un tramo cuando el transportista completa el traslado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Fin de tramo registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = Tramo.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tramo no encontrado"
            )
    })
    @PutMapping("/{id}/fin")
    public ResponseEntity<Tramo> registrarFin(
            @Parameter(description = "ID del tramo", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Fecha y hora de fin en formato ISO (YYYY-MM-DDTHH:mm:ss)", required = true, example = "2025-11-11T18:30:00")
            @RequestBody String fechaHora
    ) {
        Tramo actualizado = service.registrarFin(id, LocalDateTime.parse(fechaHora));
        return ResponseEntity.ok(actualizado);
    }
}