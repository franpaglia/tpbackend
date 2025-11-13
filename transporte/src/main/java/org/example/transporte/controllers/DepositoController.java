package org.example.transporte.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.transporte.models.Deposito;
import org.example.transporte.services.DepositoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depositos")
@Tag(name = "Depósitos", description = "API para gestionar depósitos de almacenamiento temporal de contenedores")
public class DepositoController {

    private final DepositoService service;

    public DepositoController(DepositoService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar todos los depósitos",
            description = "Obtiene todos los depósitos registrados con su ubicación y costos de estadía"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de depósitos obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Deposito.class))
            )
    })
    @GetMapping
    public List<Deposito> listar() {
        return service.listarTodos();
    }

    @Operation(
            summary = "Obtener un depósito por ID",
            description = "Devuelve el depósito indicado por su identificador"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Depósito encontrado",
                    content = @Content(schema = @Schema(implementation = Deposito.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Depósito no encontrado"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Deposito> detalle(
            @Parameter(description = "ID del depósito", required = true, example = "1")
            @PathVariable Long id
    ) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear un nuevo depósito",
            description = "Registra un nuevo depósito en el sistema con su ubicación geográfica y costo de estadía"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Depósito creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Deposito.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos en la solicitud"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Deposito crear(
            @Parameter(description = "Datos del depósito a crear (nombre, dirección, coordenadas, costoEstadiaDiaria)", required = true)
            @RequestBody Deposito deposito
    ) {
        return service.crear(deposito);
    }

    @Operation(
            summary = "Actualizar un depósito existente",
            description = "Modifica los datos de un depósito (nombre, dirección, coordenadas, costo de estadía)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Depósito actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = Deposito.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Depósito no encontrado"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Deposito> actualizar(
            @Parameter(description = "ID del depósito", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del depósito", required = true)
            @RequestBody Deposito cambios
    ) {
        Deposito actualizado = service.actualizar(id, cambios);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(
            summary = "Eliminar un depósito",
            description = "Elimina un depósito del sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Depósito eliminado correctamente"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del depósito", required = true, example = "1")
            @PathVariable Long id
    ) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
