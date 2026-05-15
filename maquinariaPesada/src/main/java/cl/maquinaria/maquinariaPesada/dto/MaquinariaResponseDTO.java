package cl.maquinaria.maquinariaPesada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * MaquinariaResponseDTO: Sin anotaciones de validación.
 * Este DTO es de SALIDA, el servidor lo construye,
 * no viene del cliente y por eso NO necesita @Valid
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaquinariaResponseDTO {

    private Long idMaquinaria;
    private String nombre;
    private String equipo;
    private String estado;
    private String ubicacion;
    private String responsable;
    private LocalDate fechaMantenimiento;

}
