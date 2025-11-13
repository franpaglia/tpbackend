package org.example.logistica.repositories;

import org.example.logistica.models.EstadoTramo;
import org.example.logistica.models.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TramoRepository extends JpaRepository<Tramo, Long> {

    // Tramos que pertenecen a una ruta
    List<Tramo> findByRutaIdRuta(Long idRuta);

    // Tramos asignados a un camión específico
    List<Tramo> findByCamionId(Long camionId);

    // Tramos por estado (ESTIMADO, ASIGNADO, INICIADO, FINALIZADO)
    List<Tramo> findByEstado(EstadoTramo estado);
}
