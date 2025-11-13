package org.example.logistica.services;

import org.example.logistica.models.EstadoSolicitud;
import org.example.logistica.models.Solicitud;
import org.example.logistica.repositories.SolicitudRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SolicitudService {

    private final SolicitudRepository repo;

    public SolicitudService(SolicitudRepository repo) {
        this.repo = repo;
    }

    // ====================== BÁSICOS ======================

    public List<Solicitud> listarTodas() {
        return repo.findAll();
    }

    public Optional<Solicitud> buscarPorId(Long id) {
        return repo.findById(id);
    }

    // ====================== CREAR SOLICITUD ======================

    /**
     * Crea una nueva solicitud de transporte.
     * - Genera un número/código único si no se envía.
     * - Inicializa el estado en BORRADOR (o PROGRAMADA, según tu decisión).
     * - Deja preparado el lugar para calcular costoEstimado y tiempoEstimado.
     * - La lógica para registrar cliente/contenedor en el microservicio de clientes
     *   se implementaría aquí (llamadas HTTP).
     */
    public Solicitud crear(Solicitud solicitud) {

        // Generar número único de solicitud si no viene desde el cliente
        if (solicitud.getNumero() == null || solicitud.getNumero().isBlank()) {
            solicitud.setNumero("SOL-" + UUID.randomUUID());
        }

        // Estado inicial de la solicitud
        if (solicitud.getEstado() == null) {
            solicitud.setEstado(EstadoSolicitud.BORRADOR);
        }

        // TODO: invocar microservicio de CLIENTES para:
        //  - verificar si el cliente existe (clienteId)
        //  - crear el cliente si no existe
        //  - crear el contenedor con identificación única si no existe
        //  - eventualmente validar que el contenedor sea apto

        // TODO: invocar lógica de generación de ruta tentativa (Rutas + Tramos)
        //  - calcular distancia total con DistanciaService / API externa
        //  - calcular tiempoEstimado
        //  - calcular costoEstimado

        // Por ahora dejamos costo y tiempo estimados en null o 0 (según tu preferencia)
        if (solicitud.getCostoEstimado() == null) {
            solicitud.setCostoEstimado(BigDecimal.ZERO);
        }
        if (solicitud.getTiempoEstimado() == null) {
            solicitud.setTiempoEstimado(0.0);
        }

        return repo.save(solicitud);
    }

    // ====================== BUSCAR POR CONTENEDOR (SEGUIMIENTO CLIENTE) ======================

    /**
     * Permite obtener la solicitud asociada a un contenedor dado.
     * Esto se usa para que el cliente consulte el estado de su envío.
     */
    public Optional<Solicitud> buscarPorContenedor(Long contenedorId) {
        return repo.findByContenedorId(contenedorId);
    }

    // ====================== LISTAR PENDIENTES / FILTRADAS ======================

    /**
     * Lista solicitudes pendientes de entrega, con filtros opcionales por estado y cliente.
     * - Si se indica estado y clienteId, filtra por ambos.
     * - Si solo se indica estado, filtra por estado.
     * - Si solo se indica clienteId, filtra por cliente.
     * - Si no se indica nada, devuelve todas las que NO están ENTREGADAS.
     */
    public List<Solicitud> listarPendientes(EstadoSolicitud estado, Long clienteId) {

        if (estado != null && clienteId != null) {
            return repo.findByEstadoAndClienteId(estado, clienteId);
        } else if (estado != null) {
            return repo.findByEstado(estado);
        } else if (clienteId != null) {
            return repo.findByClienteId(clienteId);
        } else {
            return repo.findByEstadoNot(EstadoSolicitud.ENTREGADA);
        }
    }

    // ====================== ACTUALIZAR ESTADO ======================

    /**
     * Actualiza el estado de la solicitud. Si pasa a ENTREGADA,
     * se podría registrar tiempoReal y costoFinal.
     */
    public Optional<Solicitud> actualizarEstado(Long id, EstadoSolicitud nuevoEstado) {
        return repo.findById(id).map(solicitud -> {

            solicitud.setEstado(nuevoEstado);

            // Si la solicitud pasa a ENTREGADA, acá podrías consolidar tiempos y costos reales
            if (nuevoEstado == EstadoSolicitud.ENTREGADA) {
                // TODO: calcular tiempoReal en base a fechas de tramos
                // TODO: calcular costoFinal sumando:
                //  - costos reales de cada tramo
                //  - estadías en depósitos
                //  - consumo de combustible real de cada camión

                if (solicitud.getTiempoReal() == null) {
                    // Ejemplo de cálculo ficticio de tiempoReal (debería ser por tramos)
                    // aca lo dejamos en 0.0 o lo que corresponda
                    solicitud.setTiempoReal(0.0);
                }

                if (solicitud.getCostoFinal() == null) {
                    solicitud.setCostoFinal(solicitud.getCostoEstimado());
                }
            }

            return repo.save(solicitud);
        });
    }
}
