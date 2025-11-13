package org.example.clientes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.clientes.models.Contenedor;
import org.example.clientes.services.ContenedorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contenedores")
@Tag(name = "Contenedores", description = "API para gestionar contenedores y su seguimiento")
public class ContenedorController {

    private final ContenedorService service;

    public ContenedorController(ContenedorService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar contenedores de un cliente",
            description = "Obtiene todos los contenedores asociados a un cliente específico"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de contenedores obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Contenedor.class))
            )
    })
    @GetMapping("/cliente/{idCliente}")
    public List<Contenedor> listarPorCliente(
            @Parameter(description = "ID del cliente", required = true, example = "1")
            @PathVariable Long idCliente
    ) {
        return service.listarPorCliente(idCliente);
    }

    @Operation(
            summary = "Registrar un nuevo contenedor",
            description = "Crea un nuevo contenedor en el sistema asociado a un cliente y depósito"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Contenedor creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Contenedor.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos en la solicitud"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contenedor crear(
            @Parameter(description = "Datos del contenedor a registrar", required = true)
            @RequestBody Contenedor contenedor
    ) {
        return service.registrar(contenedor);
    }
}