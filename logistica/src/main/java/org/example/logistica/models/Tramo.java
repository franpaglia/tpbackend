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

    private Double coordOrigenLong;
    private Double coordOrigenLat;

    private Double coordDestinoLong;
    private Double coordDestinoLat;

    // Referencia al estado (tabla compartida o ID remoto)
    private Long estadoId;

    private BigDecimal costoAprox;
    private BigDecimal costoReal;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    @ManyToOne
    @JoinColumn(name = "ruta_id")
    @JsonIgnoreProperties("tramos")  // 👈 Evita referencia circular
    private Ruta ruta;

    @ManyToOne
    @JoinColumn(name = "tipo_tramo_id")
    private TipoTramo tipoTramo;

    // ID del camión en el microservicio de Transporte
    private Long camionId;
}