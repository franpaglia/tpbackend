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
    @Column(name = "id_cliente")
    private Long id;

    private String nombre;
    private String dni;
    private String email;
    private String telefono;
    private String direccion;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro = LocalDate.now();

    // Relación 1:N con Contenedor
    @OneToMany(mappedBy = "cliente")
    private List<Contenedor> contenedores;

    // 🚫 POR AHORA SIN SolicitudEnvio, porque no tenés esa entidad todavía
    // Cuando la crees, ahí sí podés agregar:
    //
    // @OneToMany(mappedBy = "cliente")
    // private List<SolicitudEnvio> solicitudes;
}
