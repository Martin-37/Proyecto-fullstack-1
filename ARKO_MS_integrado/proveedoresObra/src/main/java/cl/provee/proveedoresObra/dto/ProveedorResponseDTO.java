package cl.provee.proveedoresObra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorResponseDTO {

    private Long id;
    private String nombre;
    private String contacto;
    private String telefono;
    private String direccion;
    private String estado;

}
