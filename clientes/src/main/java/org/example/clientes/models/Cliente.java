package org.example.clientes.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")       // 👈 igual que en el DER
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    // campo del DER
    @Column(name = "telefono")
    private String telefono;

    // en el DER se llama "mail"
    @Column(name = "mail")
    private String email;

    // 👉 estos son extras tuyos, los dejamos
    @Column(name = "dni")
    private String dni;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    // Relación con Contenedor (un cliente puede tener varios contenedores)
    @OneToMany(mappedBy = "cliente")
    private List<Contenedor> contenedores;
}
