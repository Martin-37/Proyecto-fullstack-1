package cl.dsy1103.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolRequestDTO {

    @NotBlank(message = "El nombre del rol no puede estar vacio")
    private String nombreRol;

    private String descripcion;

    @NotBlank(message = "El estado no puede estar vacio")
    private String estado;
}
