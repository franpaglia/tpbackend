package org.example.clientes.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estados")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstado;

    private String descripcion;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
}
