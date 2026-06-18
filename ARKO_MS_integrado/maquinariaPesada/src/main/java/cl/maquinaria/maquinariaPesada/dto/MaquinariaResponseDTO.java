package cl.maquinaria.maquinariaPesada.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
