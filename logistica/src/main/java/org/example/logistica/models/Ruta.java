package org.example.logistica.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rutas")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRuta;

    @OneToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    @JsonBackReference
    private Solicitud solicitud;

    private Integer cantTramos;
    private Integer cantDepositos;

    private Double tiempoEstimado;      // suma de tiempos estimados de los tramos
    private Double distanciaTotalKm;    // distancia total en kilómetros

    private BigDecimal costoAproximadoTotal; // suma de costos aprox. de los tramos

    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tramo> tramos;
}
