package org.example.clientes.repositories;

import org.example.clientes.models.Contenedor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContenedorRepository extends JpaRepository<Contenedor, Long> {
    List<Contenedor> findByClienteId(Long idCliente);
}
