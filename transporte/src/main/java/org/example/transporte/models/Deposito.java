package org.example.transporte.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "depositos")
public class Deposito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeposito;

    private String nombre;
    private String direccion;

    private Double coordDepositoLong;
    private Double coordDepositoLat;

    private BigDecimal costoEstadiaDiaria;
}
