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
}