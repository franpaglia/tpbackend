package org.example.logistica.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solicitudes")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitud;

    // IDs que vienen del microservicio de clientes
    private Long contenedorId;
    private Long clienteId;

    private BigDecimal costoEstimado;
    private BigDecimal costoFinal;

    private Double tiempoEstimado; // en horas o minutos, como decidas
    private Double tiempoReal;

    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL)
    private Ruta ruta;
}
