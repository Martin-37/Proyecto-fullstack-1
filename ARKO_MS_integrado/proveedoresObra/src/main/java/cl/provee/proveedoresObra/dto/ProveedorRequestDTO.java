package cl.provee.proveedoresObra.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorRequestDTO {

    
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;

    @NotBlank(message = "El contacto no puede estar vacío")
    @Size(max = 100, message = "El contacto no puede exceder 100 caracteres")
    private String contacto;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "El teléfono debe contener entre 8 y 15 dígitos")
    private String telefono;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    private String direccion;

    @NotBlank(message = "El estado no puede estar vacío")
    @Pattern(regexp = "^(Activo|Inactivo|Pendiente)$", message = "El estado debe ser: Activo, Inactivo o Pendiente")
    private String estado;
}
