package org.example.transporte.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tarifas")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarifa;

    @ManyToOne
    @JoinColumn(name = "camion_id", nullable = false)
    private Camion camion;

    // Por ahora costo base por litro o por tipo de camión, según tu DER
    private BigDecimal costoLtBase;

    // Si después querés, podés agregar otros componentes:
    // private BigDecimal costoGestion;
    // private BigDecimal costoKmBase;
}
