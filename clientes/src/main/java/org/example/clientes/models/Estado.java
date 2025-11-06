package org.example.clientes.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "estado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Long id;

    @Column(name = "fechaHoraInicio")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fechaHoraFin")
    private LocalDateTime fechaHoraFin;
}
