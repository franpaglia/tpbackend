package org.example.clientes.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contenedores")
public class Contenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContenedor;

    private Double peso;
    private Double volumen;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

    private Long depositoId; // FK referencial al servicio de transporte (relación externa)
}
