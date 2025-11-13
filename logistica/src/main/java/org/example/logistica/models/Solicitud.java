package org.example.logistica.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(nullable = false, unique = true)
    private String numero;              // Código visible de solicitud

    // IDs que vienen del microservicio de clientes
    @Column(nullable = false)
    private Long contenedorId;

    @Column(nullable = false)
    private Long clienteId;

    private BigDecimal costoEstimado;
    private BigDecimal costoFinal;

    // en horas, por ejemplo
    private Double tiempoEstimado;
    private Double tiempoReal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;     // BORRADOR, PROGRAMADA, EN_TRANSITO, ENTREGADA

    @OneToOne(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Ruta ruta;
}
