package org.example.clientes.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "contenedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contenedor")
    private Long id;

    private String codigo;
    private String tipo;

    @Column(name = "capacidad_volumen")
    private BigDecimal capacidadVolumen;

    @Column(name = "capacidad_peso")
    private BigDecimal capacidadPeso;

    private String estado;

    @Column(name = "ubicacion_actual")
    private String ubicacionActual;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
