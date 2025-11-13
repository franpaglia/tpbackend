package org.example.logistica.services;

import org.example.logistica.dto.DistanciaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DistanciaService {

    private static final Logger log = LoggerFactory.getLogger(DistanciaService.class);

    // Radio de la Tierra en kilómetros
    private static final double RADIO_TIERRA_KM = 6371.0;

    // Velocidad promedio en carretera (para estimar duración)
    private static final double VELOCIDAD_PROMEDIO_KM_H = 80.0;

    /**
     * Calcula la distancia entre dos puntos:
     * - Primero intenta consultar una API externa de rutas (Google Maps Directions o similar).
     * - Si la API externa falla o no está disponible, utiliza la fórmula de Haversine como respaldo.
     */
    public DistanciaDTO calcularDistancia(Double lat1, Double lon1, Double lat2, Double lon2) {

        try {
            // 1) Intentar cálculo vía API externa (Google Maps / similar)
            DistanciaDTO desdeApiExterna = consultarApiExterna(lat1, lon1, lat2, lon2);
            log.info("Distancia calculada usando API externa de rutas");
            return desdeApiExterna;

        } catch (Exception ex) {
            log.warn("Fallo la API externa de rutas, utilizando cálculo local Haversine como respaldo", ex);
            // 2) Fallback a cálculo interno con Haversine
            return calcularConHaversine(lat1, lon1, lat2, lon2);
        }
    }

    /**
     * Punto de integración con la API externa de rutas (por ejemplo Google Maps Directions).
     * Aquí iría el uso de WebClient/RestTemplate para llamar a la API real.
     * De momento lanza una excepción para forzar el uso del fallback Haversine.
     */
    private DistanciaDTO consultarApiExterna(Double lat1, Double lon1, Double lat2, Double lon2) {
        // TODO: implementar llamada real a la API externa (Google Maps Directions, etc.)
        // Ejemplo:
        // - construir URL con origen/destino
        // - enviar request HTTP
        // - parsear distancia y duración desde la respuesta
        // - retornar un DistanciaDTO con esos valores

        throw new UnsupportedOperationException("Integración con API externa de rutas no implementada aún");
    }

    /**
     * Cálculo local de distancia usando la fórmula de Haversine.
     * Esta fórmula calcula la distancia en línea recta sobre la superficie de la Tierra
     * y luego se ajusta un 20% para aproximar un recorrido vial.
     */
    private DistanciaDTO calcularConHaversine(Double lat1, Double lon1, Double lat2, Double lon2) {
        log.info("Calculando distancia de ({}, {}) a ({}, {}) con Haversine",
                lat1, lon1, lat2, lon2);

        // Convertir grados a radianes
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Diferencias
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Fórmula de Haversine
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distancia en kilómetros
        double distanciaKm = RADIO_TIERRA_KM * c;

        // Ajuste del 20% para aproximar distancia por carretera
        distanciaKm = distanciaKm * 1.2;

        // Calcular duración estimada en horas
        double duracionHoras = distanciaKm / VELOCIDAD_PROMEDIO_KM_H;

        // Formatear textos
        String distanciaTexto = String.format("%.1f km", distanciaKm);
        String duracionTexto = formatearDuracion(duracionHoras);

        log.info("Distancia calculada (fallback Haversine): {} km, Duración estimada: {} horas",
                String.format("%.2f", distanciaKm), String.format("%.2f", duracionHoras));

        return new DistanciaDTO(
                Math.round(distanciaKm * 100.0) / 100.0,  // Redondear a 2 decimales
                Math.round(duracionHoras * 100.0) / 100.0,
                distanciaTexto,
                duracionTexto
        );
    }

    /**
     * Calcula el costo del tramo basado en la distancia.
     */
    public Double calcularCostoTramo(Double distanciaKm, Double costoPorKm) {
        return Math.round(distanciaKm * costoPorKm * 100.0) / 100.0;
    }

    /**
     * Formatea la duración en un texto legible.
     */
    private String formatearDuracion(double horas) {
        int horasEnteras = (int) horas;
        int minutos = (int) ((horas - horasEnteras) * 60);

        if (horasEnteras == 0) {
            return minutos + " mins";
        } else if (minutos == 0) {
            return horasEnteras + " hours";
        } else {
            return horasEnteras + " hours " + minutos + " mins";
        }
    }
}
