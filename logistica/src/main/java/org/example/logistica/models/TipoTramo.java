package org.example.logistica.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tipos_tramo")
public class TipoTramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoTramo;

    private String descripcion;
}
