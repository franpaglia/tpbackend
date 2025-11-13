package org.example.transporte.services;

import org.example.transporte.models.Camion;
import org.example.transporte.repositories.CamionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CamionService {

    private final CamionRepository repo;

    public CamionService(CamionRepository repo) {
        this.repo = repo;
    }

    // ====================== CRUD BÁSICO ======================

    public List<Camion> listarTodos() {
        return repo.findAll();
    }

    public Optional<Camion> buscarPorPatente(String patente) {
        return repo.findById(patente);
    }

    public Camion crear(Camion camion) {
        // Por defecto, lo marcamos como disponible si viene null
        if (camion.getDisponibilidad() == null) {
            camion.setDisponibilidad(true);
        }
        return repo.save(camion);
    }

    public Camion actualizar(String patente, Camion datos) {
        return repo.findById(patente)
                .map(actual -> {
                    actual.setNombreTransportista(datos.getNombreTransportista());
                    actual.setTelefono(datos.getTelefono());
                    actual.setCapacidadPeso(datos.getCapacidadPeso());
                    actual.setCapacidadVolumen(datos.getCapacidadVolumen());
                    actual.setDisponibilidad(datos.getDisponibilidad());
                    actual.setCostoKm(datos.getCostoKm());
                    actual.setConsumoCombustible(datos.getConsumoCombustible());
                    return repo.save(actual);
                })
                .orElseThrow(() -> new RuntimeException("Camión no encontrado"));
    }

    public void eliminar(String patente) {
        repo.deleteById(patente);
    }

    // ====================== DISPONIBILIDAD ======================

    public List<Camion> listarDisponibles() {
        return repo.findByDisponibilidadTrue();
    }

    /**
     * Devuelve true si el camión puede transportar el peso y volumen indicados
     * (sin considerar si está disponible, eso se controla aparte).
     */
    public boolean tieneCapacidad(String patente, BigDecimal peso, BigDecimal volumen) {
        Camion camion = repo.findById(patente)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado"));

        boolean okPeso = camion.getCapacidadPeso() == null
                || camion.getCapacidadPeso().compareTo(peso) >= 0;

        boolean okVolumen = camion.getCapacidadVolumen() == null
                || camion.getCapacidadVolumen().compareTo(volumen) >= 0;

        return okPeso && okVolumen;
    }

    /**
     * Devuelve una lista de camiones DISPONIBLES que soporten ese peso y volumen.
     * Esto sirve para que LOGÍSTICA pida "camiones aptos" para un tramo.
     */
    public List<Camion> buscarDisponiblesConCapacidad(BigDecimal peso, BigDecimal volumen) {
        return repo.findByDisponibilidadTrueAndCapacidadPesoGreaterThanEqualAndCapacidadVolumenGreaterThanEqual(
                peso, volumen
        );
    }

    /**
     * Marca un camión como ocupado (por ejemplo, cuando se asigna a un tramo).
     */
    public Camion marcarOcupado(String patente) {
        Camion camion = repo.findById(patente)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado"));

        camion.setDisponibilidad(false);
        return repo.save(camion);
    }

    /**
     * Marca un camión como disponible nuevamente (cuando termina sus tramos).
     */
    public Camion marcarDisponible(String patente) {
        Camion camion = repo.findById(patente)
                .orElseThrow(() -> new RuntimeException("Camión no encontrado"));

        camion.setDisponibilidad(true);
        return repo.save(camion);
    }
}
