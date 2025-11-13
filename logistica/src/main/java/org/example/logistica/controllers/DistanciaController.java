package org.example.logistica.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.logistica.dto.DistanciaDTO;
import org.example.logistica.services.DistanciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/distancias")
@Tag(
        name = "Distancias",
        description = "API para calcular distancias entre puntos usando una API externa de rutas y, en caso de falla, fórmula de Haversine como respaldo"
)
public class DistanciaController {

    private final DistanciaService distanciaService;

    public DistanciaController(DistanciaService distanciaService) {
        this.distanciaService = distanciaService;
    }

    @Operation(
            summary = "Calcular distancia entre dos puntos",
            description = """
                    Calcula la distancia y el tiempo de viaje entre dos coordenadas geográficas.
                    - Intenta consultar primero una API externa de rutas (por ejemplo Google Maps Directions o similar).
                    - Si la API externa no está disponible, utiliza un cálculo local mediante la fórmula de Haversine como respaldo.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Distancia calculada exitosamente",
                    content = @Content(schema = @Schema(implementation = DistanciaDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Coordenadas inválidas"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error al calcular la distancia"
            )
    })
    @GetMapping("/calcular")
    public ResponseEntity<DistanciaDTO> calcularDistancia(
            @Parameter(description = "Latitud del origen", required = true, example = "-31.4201")
            @RequestParam Double origenLat,

            @Parameter(description = "Longitud del origen", required = true, example = "-64.1888")
            @RequestParam Double origenLng,

            @Parameter(description = "Latitud del destino", required = true, example = "-34.6037")
            @RequestParam Double destinoLat,

            @Parameter(description = "Longitud del destino", required = true, example = "-58.3816")
            @RequestParam Double destinoLng
    ) {
        DistanciaDTO distancia = distanciaService.calcularDistancia(
                origenLat, origenLng, destinoLat, destinoLng
        );

        return ResponseEntity.ok(distancia);
    }
}
