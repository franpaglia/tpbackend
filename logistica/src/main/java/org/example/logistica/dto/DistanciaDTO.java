package org.example.logistica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanciaDTO {
    private Double distanciaKm;
    private Double duracionHoras;
    private String distanciaTexto;
    private String duracionTexto;
}