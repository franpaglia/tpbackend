package org.example.logistica.models;

import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    private Integer cantTramos;
    private Integer cantDepositos;

    private Double tiempoEstimado;
    private String distanciaTotal; // podés usar Double km, pero en el DER lo tenías VARCHAR
}
