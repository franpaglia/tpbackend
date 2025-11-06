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

    // 👉 campito extra tuyo, no molesta
    private String codigo;

    private String tipo;

    // en el DER: volumen DOUBLE
    @Column(name = "volumen")
    private Double volumen;

    // en el DER: peso DOUBLE
    @Column(name = "peso")
    private Double peso;

    // FK a estado.id_estado
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;

    // FK a cliente.id_cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // FK a deposito.id_deposito
    //@ManyToOne
    //@JoinColumn(name = "deposito_id")
    //private Deposito deposito;
}
