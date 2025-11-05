package org.example.logistica.repositories;

import org.example.logistica.models.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TramoRepository extends JpaRepository<Tramo, Long> {
    List<Tramo> findByRutaIdRuta(Long idRuta);
}
