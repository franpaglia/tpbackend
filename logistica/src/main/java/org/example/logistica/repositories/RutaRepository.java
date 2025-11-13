package org.example.logistica.repositories;

import org.example.logistica.models.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface RutaRepository extends JpaRepository<Ruta, Long> {

    // Obtener ruta asociada a una solicitud
    Optional<Ruta> findBySolicitudIdSolicitud(Long idSolicitud);

    // Buscar rutas por número de solicitud (si usás un código externo)
    Optional<Ruta> findBySolicitudNumero(String numeroSolicitud);

    // Buscar todas las rutas que tengan tramos asociados
    List<Ruta> findByTramosIsNotNull();
}

