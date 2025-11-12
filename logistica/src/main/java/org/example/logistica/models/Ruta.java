package org.example.logistica.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @JsonBackReference
    private Solicitud solicitud;

    private Integer cantTramos;
    private Integer cantDepositos;

    private Double tiempoEstimado;
    private String distanciaTotal;

    @OneToMany(mappedBy = "ruta")
    private List<Tramo> tramos;
}