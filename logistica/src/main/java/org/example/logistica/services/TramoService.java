package org.example.logistica.services;

import org.example.logistica.dto.DistanciaDTO;
import org.example.logistica.models.EstadoTramo;
import org.example.logistica.models.Tramo;
import org.example.logistica.repositories.TramoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TramoService {

    private static final Logger log = LoggerFactory.getLogger(TramoService.class);

    // Costo base por kilómetro (podría venir de una tabla de tarifas configurables)
    private static final Double COSTO_BASE_POR_KM = 15.0;

    private final TramoRepository repo;
    private final DistanciaService distanciaService;

    public TramoService(TramoRepository repo, DistanciaService distanciaService) {
        this.repo = repo;
        this.distanciaService = distanciaService;
    }

    // ====================== CONSULTAS BÁSICAS ======================

    public List<Tramo> listarTodos() {
        return repo.findAll();
    }

    public Optional<Tramo> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public List<Tramo> listarPorRuta(Long idRuta) {
        return repo.findByRutaIdRuta(idRuta);
    }

    public List<Tramo> listarPorEstado(EstadoTramo estadoTramo) {
        return repo.findByEstado(estadoTramo);
    }

    // ====================== CREAR TRAMO ======================

    /**
     * Crea un tramo calculando automáticamente:
     * - distancia (usando DistanciaService: API externa + fallback Haversine)
     * - costo aproximado en función de la distancia
     * - estado inicial ESTIMADO
     */
    public Tramo crear(Tramo tramo) {
        log.info("Creando tramo con coordenadas origen: ({}, {}), destino: ({}, {})",
                tramo.getCoordOrigenLat(), tramo.getCoordOrigenLong(),
                tramo.getCoordDestinoLat(), tramo.getCoordDestinoLong());

        try {
            // Calcular distancia usando DistanciaService
            DistanciaDTO distancia = distanciaService.calcularDistancia(
                    tramo.getCoordOrigenLat(),
                    tramo.getCoordOrigenLong(),
                    tramo.getCoordDestinoLat(),
                    tramo.getCoordDestinoLong()
            );

            // Guardar distancia en el tramo (en km)
            tramo.setDistanciaKm(distancia.getDistanciaKm());

            // Calcular costo aproximado
            Double costoCalculado = distanciaService.calcularCostoTramo(
                    distancia.getDistanciaKm(),
                    COSTO_BASE_POR_KM
            );

            tramo.setCostoAprox(BigDecimal.valueOf(costoCalculado));

            log.info("Tramo creado con distancia: {} km, costo aproximado: ${}",
                    distancia.getDistanciaKm(), costoCalculado);

        } catch (Exception e) {
            log.error("Error al calcular distancia, usando valores por defecto", e);
            tramo.setDistanciaKm(0.0);
            tramo.setCostoAprox(BigDecimal.valueOf(1000.0));
        }

        // Estado inicial del tramo
        if (tramo.getEstado() == null) {
            tramo.setEstado(EstadoTramo.ESTIMADO);
        }

        return repo.save(tramo);
    }

    // ====================== ASIGNAR CAMIÓN ======================

    /**
     * Asigna un camión al tramo y cambia el estado a ASIGNADO.
     * La validación de peso/volumen del camión se haría consultando
     * al microservicio de Transporte (y eventualmente al de Clientes).
     */
    public Tramo asignarCamion(Long idTramo, Long camionId) {
        Tramo tramo = repo.findById(idTramo)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado"));

        tramo.setCamionId(camionId);

        // Cambiar el estado a ASIGNADO
        tramo.setEstado(EstadoTramo.ASIGNADO);

        log.info("Camión {} asignado al tramo {}. Estado -> ASIGNADO", camionId, idTramo);

        // TODO: aquí podría integrarse con el microservicio de Transporte para:
        //  - verificar peso y volumen máximo del camión
        //  - comprobar disponibilidad de horarios
        //  - registrar la asignación en Transporte

        return repo.save(tramo);
    }

    // ====================== REGISTRAR INICIO ======================

    /**
     * Marca el inicio real de un tramo, seteando la fecha/hora y el estado INICIADO.
     */
    public Tramo registrarInicio(Long idTramo, LocalDateTime fechaHoraInicio) {
        Tramo tramo = repo.findById(idTramo)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado"));

        tramo.setFechaHoraInicio(fechaHoraInicio);
        tramo.setEstado(EstadoTramo.INICIADO);

        log.info("Tramo {} iniciado a las {}. Estado -> INICIADO", idTramo, fechaHoraInicio);

        return repo.save(tramo);
    }

    // ====================== REGISTRAR FIN ======================

    /**
     * Marca el fin real de un tramo, seteando la fecha/hora de fin
     * y el estado FINALIZADO. El cálculo del costoReal podría hacerse aquí
     * si se integran más datos (combustible real, peajes, etc.).
     */
    public Tramo registrarFin(Long idTramo, LocalDateTime fechaHoraFin) {
        Tramo tramo = repo.findById(idTramo)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado"));

        tramo.setFechaHoraFin(fechaHoraFin);
        tramo.setEstado(EstadoTramo.FINALIZADO);

        if (tramo.getFechaHoraInicio() != null) {
            log.info("Tramo {} finalizado. Duración real registrada entre {} y {}",
                    idTramo, tramo.getFechaHoraInicio(), fechaHoraFin);
        }

        log.info("Tramo {} finalizado a las {}. Estado -> FINALIZADO", idTramo, fechaHoraFin);

        // TODO: aquí podrías calcular costoReal si tenés tarifas y datos de consumo
        // por ejemplo, multiplicando distanciaKm por un costo real por km.

        return repo.save(tramo);
    }

    // ====================== CALCULAR DISTANCIA DE UN TRAMO EXISTENTE ======================

    /**
     * Recalcula la distancia de un tramo ya guardado utilizando DistanciaService.
     */
    public DistanciaDTO calcularDistanciaTramo(Long idTramo) {
        Tramo tramo = repo.findById(idTramo)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado"));

        return distanciaService.calcularDistancia(
                tramo.getCoordOrigenLat(),
                tramo.getCoordOrigenLong(),
                tramo.getCoordDestinoLat(),
                tramo.getCoordDestinoLong()
        );
    }
}
