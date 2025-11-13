package org.example.logistica.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tramos")
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTramo;

    // Coordenadas origen
    private Double coordOrigenLong;
    private Double coordOrigenLat;

    // Coordenadas destino
    private Double coordDestinoLong;
    private Double coordDestinoLat;

    // Distancia del tramo en kilómetros (calculada con la API externa)
    private Double distanciaKm;

    // Estado lógico del tramo: ESTIMADO, ASIGNADO, INICIADO, FINALIZADO
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTramo estado;

    private BigDecimal costoAprox;
    private BigDecimal costoReal;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    @ManyToOne
    @JoinColumn(name = "ruta_id", nullable = false)
    @JsonIgnoreProperties("tramos")
    private Ruta ruta;

    @ManyToOne
    @JoinColumn(name = "tipo_tramo_id", nullable = false)
    private TipoTramo tipoTramo;

    // ID del camión en el microservicio de Transporte
    private Long camionId;
}
