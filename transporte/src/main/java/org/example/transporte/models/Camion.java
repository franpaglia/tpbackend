package org.example.transporte.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "camiones")
public class Camion {

    @Id
    @Column(length = 20)
    private String patente;   // PK según tu DER

    private String nombreTransportista;
    private String telefono;

    private BigDecimal capacidadPeso;
    private BigDecimal capacidadVolumen;

    private Boolean disponibilidad;

    private BigDecimal costoKm;
    private BigDecimal consumoCombustible;
}
