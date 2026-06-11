package cl.dsy1103.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolResponseDTO {

    private Long id;
    private String nombreRol;
    private String descripcion;
    private String estado;
}
