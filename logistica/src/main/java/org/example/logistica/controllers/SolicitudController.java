package org.example.logistica.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.logistica.models.EstadoSolicitud;
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

    // ====================== LISTAR TODAS ======================
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

    // ====================== DETALLE POR ID ======================
    @Operation(
            summary = "Obtener detalle de una solicitud",
            description = "Obtiene los detalles completos de una solicitud específica incluyendo costos, tiempos y ruta asociada"
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

    // ====================== CREAR SOLICITUD ======================
    @Operation(
            summary = "Crear una nueva solicitud",
            description = "Registra una nueva solicitud de transporte de contenedor. " +
                    "Si el cliente no existe se registra, se asocia el contenedor y se calculan costo y tiempo estimados."
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
    public ResponseEntity<Solicitud> crear(
            @Parameter(description = "Datos de la solicitud a crear (clienteId, contenedorId, etc.)", required = true)
            @RequestBody Solicitud solicitud
    ) {
        Solicitud creada = service.crear(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // ====================== BUSCAR POR CONTENEDOR (SEGUIMIENTO CLIENTE) ======================
    @Operation(
            summary = "Obtener solicitud por contenedor",
            description = "Permite al cliente consultar el estado del transporte de su contenedor."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Solicitud encontrada para el contenedor indicado",
                    content = @Content(schema = @Schema(implementation = Solicitud.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No existe una solicitud para el contenedor indicado"
            )
    })
    @GetMapping("/contenedor/{contenedorId}")
    public ResponseEntity<Solicitud> buscarPorContenedor(
            @Parameter(description = "ID del contenedor", required = true, example = "10")
            @PathVariable Long contenedorId
    ) {
        return service.buscarPorContenedor(contenedorId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ====================== LISTAR PENDIENTES / CON FILTROS ======================
    @Operation(
            summary = "Listar solicitudes pendientes de entrega",
            description = "Permite al Operador/Administrador consultar las solicitudes pendientes, " +
                    "con filtros opcionales por estado y cliente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de solicitudes pendientes obtenida exitosamente",
                    content = @Content(schema = @Schema(implementation = Solicitud.class))
            )
    })
    @GetMapping("/pendientes")
    public List<Solicitud> listarPendientes(
            @Parameter(description = "Estado de la solicitud (BORRADOR, PROGRAMADA, EN_TRANSITO, ENTREGADA)", required = false)
            @RequestParam(required = false) EstadoSolicitud estado,

            @Parameter(description = "ID del cliente", required = false, example = "5")
            @RequestParam(required = false) Long clienteId
    ) {
        return service.listarPendientes(estado, clienteId);
    }

    // ====================== ACTUALIZAR ESTADO DE LA SOLICITUD ======================
    @Operation(
            summary = "Actualizar estado de una solicitud",
            description = "Permite actualizar el estado de la solicitud (por ejemplo: PROGRAMADA, EN_TRANSITO, ENTREGADA). " +
                    "Al pasar a ENTREGADA se pueden registrar tiempoReal y costoFinal."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado de la solicitud actualizado correctamente",
                    content = @Content(schema = @Schema(implementation = Solicitud.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Solicitud no encontrada"
            )
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Solicitud> actualizarEstado(
            @Parameter(description = "ID de la solicitud", required = true, example = "1")
            @PathVariable Long id,

            @Parameter(description = "Nuevo estado de la solicitud", required = true, example = "EN_TRANSITO")
            @RequestParam EstadoSolicitud nuevoEstado
    ) {
        return service.actualizarEstado(id, nuevoEstado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
