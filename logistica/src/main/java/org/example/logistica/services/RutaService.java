package org.example.logistica.services;

import org.example.logistica.models.Ruta;
import org.example.logistica.models.Solicitud;
import org.example.logistica.models.Tramo;
import org.example.logistica.repositories.RutaRepository;
import org.example.logistica.repositories.SolicitudRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RutaService {

    private final RutaRepository rutaRepo;
    private final SolicitudRepository solicitudRepo;

    public RutaService(RutaRepository rutaRepo, SolicitudRepository solicitudRepo) {
        this.rutaRepo = rutaRepo;
        this.solicitudRepo = solicitudRepo;
    }

    // ====================== LISTAR TODAS ======================
    public List<Ruta> listarTodas() {
        return rutaRepo.findAll();
    }

    // ====================== BUSCAR POR ID ======================
    public Optional<Ruta> buscarPorId(Long idRuta) {
        return rutaRepo.findById(idRuta);
    }

    // ====================== BUSCAR RUTA POR SOLICITUD ======================
    public Optional<Ruta> buscarPorSolicitud(Long idSolicitud) {
        return rutaRepo.findBySolicitudIdSolicitud(idSolicitud);
    }

    // ====================== CREAR / GUARDAR RUTA ======================
    public Ruta guardar(Ruta ruta) {
        return rutaRepo.save(ruta);
    }

    // ====================== ASOCIAR RUTA A SOLICITUD ======================
    public Optional<Ruta> asignarRutaASolicitud(Long idSolicitud, Ruta ruta) {

        return solicitudRepo.findById(idSolicitud).map(solicitud -> {

            ruta.setSolicitud(solicitud);

            // Registrar cantidad de tramos y depósitos
            if (ruta.getTramos() != null) {
                ruta.setCantTramos(ruta.getTramos().size());
                long cantDepositos = ruta.getTramos().stream()
                        .filter(t -> t.getTipoTramo().getDescripcion().contains("DEPOSITO"))
                        .count();
                ruta.setCantDepositos((int) cantDepositos);

                // Calcular distancia y costo total de la ruta
                calcularTotalesRuta(ruta);
            }

            Ruta guardada = rutaRepo.save(ruta);

            solicitud.setRuta(guardada);
            solicitudRepo.save(solicitud);

            return guardada;
        });
    }

    // ====================== CALCULAR COSTOS Y TIEMPOS ======================
    /**
     * Calcula:
     * - tiempo estimado total (suma de tiempos de tramos)
     * - distancia total
     * - costo aproximado total
     */
    private void calcularTotalesRuta(Ruta ruta) {

        double distanciaTotal = 0.0;
        double tiempoEstimadoTotal = 0.0;
        BigDecimal costoAproxTotal = BigDecimal.ZERO;

        for (Tramo tramo : ruta.getTramos()) {

            if (tramo.getDistanciaKm() != null) {
                distanciaTotal += tramo.getDistanciaKm();
            }
            if (tramo.getRuta() != null && tramo.getCostoAprox() != null) {
                costoAproxTotal = costoAproxTotal.add(tramo.getCostoAprox());
            }

            // Si los tiempos estimados vienen desde el controller o servicio
            if (tramo.getFechaHoraInicio() != null && tramo.getFechaHoraFin() != null) {
                long minutos = java.time.Duration.between(
                        tramo.getFechaHoraInicio(),
                        tramo.getFechaHoraFin()
                ).toMinutes();
                tiempoEstimadoTotal += (minutos / 60.0);
            }
        }

        ruta.setDistanciaTotalKm(distanciaTotal);
        ruta.setTiempoEstimado(tiempoEstimadoTotal);
        ruta.setCostoAproximadoTotal(costoAproxTotal);
    }
}
