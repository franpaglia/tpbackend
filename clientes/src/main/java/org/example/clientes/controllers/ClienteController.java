package org.example.clientes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.clientes.models.Cliente;
import org.example.clientes.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "API para gestionar clientes del sistema de logística")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar todos los clientes",
            description = "Obtiene una lista completa de todos los clientes registrados en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de clientes obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Cliente.class))
            )
    })
    @GetMapping
    public List<Cliente> listar() {
        return service.listarTodos();
    }

    @Operation(
            summary = "Registrar un nuevo cliente",
            description = "Crea un nuevo cliente en el sistema con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Cliente.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos en la solicitud"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente crear(
            @Parameter(description = "Datos del cliente a registrar", required = true)
            @RequestBody Cliente cliente
    ) {
        return service.registrar(cliente);
    }
}
