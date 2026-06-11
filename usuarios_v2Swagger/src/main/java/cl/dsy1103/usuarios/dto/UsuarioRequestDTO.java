package cl.dsy1103.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @Size(min = 3, max = 60, message = "El nombre de usuario debe tener entre 3 y 60 caracteres")
    private String nombreUsuario;

    @NotNull(message = "El id del rol es obligatorio")
    private Long rolId;

    @NotBlank(message = "El email no puede estar vacio")
    @Email(message = "El email no tiene un formato valido")
    private String email;

    @NotBlank(message = "La contrasena no puede estar vacia")
    @Size(min = 8, message = "La contrasena debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "El estado no puede estar vacio")
    private String estado;
}
