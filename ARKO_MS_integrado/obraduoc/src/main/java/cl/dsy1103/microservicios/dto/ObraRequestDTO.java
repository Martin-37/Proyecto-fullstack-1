package cl.dsy1103.microservicios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * ObraRequestDTO.java
 * DTO de ENTRADA: lo que el cliente envia en el body del POST o PUT.
 *
 * REGLA CLARA:
 *   Las validaciones (@NotBlank, @NotNull, etc.) viven AQUI y SOLO AQUI,
 *   NO en la entidad Obra.java.
 *   Cuando el Controller usa @Valid, Spring valida este DTO.
 *   Si falla, GlobalExceptionHandler captura el error y devuelve
 *   un 400 con el mapa { "campo": "mensaje de error" }.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObraRequestDTO {

    // El cliente envia el nombre, que tambien es la PK
    @NotBlank(message = "El nombre de la obra no puede estar vacio")
    private String nombreObra;

    @NotBlank(message = "La ubicacion no puede estar vacia")
    private String ubicacion;

    // Solo el RUT del responsable. El Service busca
    // el objeto Trabajador en la BD usando este valor.
    @NotNull(message = "El RUT del responsable es obligatorio")
    private Long responsableNumrun;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotBlank(message = "El estado no puede estar vacio")
    private String estado;
}
