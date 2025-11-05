package org.example.transporte.repositories;

import org.example.transporte.models.Camion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CamionRepository extends JpaRepository<Camion, String> {

    List<Camion> findByDisponibilidadTrue();
}
