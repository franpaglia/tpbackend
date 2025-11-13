package org.example.transporte.repositories;

import org.example.transporte.models.Camion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface CamionRepository extends JpaRepository<Camion, String> {

    // Camiones disponibles (disponibilidad = true)
    List<Camion> findByDisponibilidadTrue();

    // Opcional: camiones que soporten al menos cierto peso y volumen
    List<Camion> findByDisponibilidadTrueAndCapacidadPesoGreaterThanEqualAndCapacidadVolumenGreaterThanEqual(
            BigDecimal peso, BigDecimal volumen
    );
}
