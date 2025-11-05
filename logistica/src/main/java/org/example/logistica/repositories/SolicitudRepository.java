package org.example.logistica.repositories;

import org.example.logistica.models.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> { }
