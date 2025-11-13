package org.example.logistica.repositories;

import org.example.logistica.models.EstadoSolicitud;
import org.example.logistica.models.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    // Buscar la solicitud asociada a un contenedor (seguimiento del cliente)
    Optional<Solicitud> findByContenedorId(Long contenedorId);

    // Filtrar por estado + cliente (operador/administrador)
    List<Solicitud> findByEstadoAndClienteId(EstadoSolicitud estado, Long clienteId);

    // Filtrar por estado
    List<Solicitud> findByEstado(EstadoSolicitud estado);

    // Filtrar por cliente
    List<Solicitud> findByClienteId(Long clienteId);

    // Para listar pendientes: todas las que NO están ENTREGADAS
    List<Solicitud> findByEstadoNot(EstadoSolicitud estado);
}

