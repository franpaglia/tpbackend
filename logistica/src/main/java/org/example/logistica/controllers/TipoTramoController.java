package org.example.logistica.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.logistica.models.TipoTramo;
import org.example.logistica.services.TipoTramoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-tramo")
@Tag(name = "Tipos de Tramo", description = "API para consultar los tipos de tramo disponibles (origen-depósito, depósito-depósito, depósito-destino, origen-destino)")
public class TipoTramoController {

    private final TipoTramoService service;

    public TipoTramoController(TipoTramoService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar tipos de tramo",
            description = "Obtiene todos los tipos de tramo disponibles en el sistema (ej: origen-depósito, depósito-destino)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de tipos de tramo obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = TipoTramo.class))
            )
    })
    @GetMapping
    public List<TipoTramo> listar() {
        return service.listarTodos();
    }
}