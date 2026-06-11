package cl.dsy1103.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Usuario es obligatorio")
    private String nombreUsuario;

    @NotBlank(message = "Contraseña es obligatoria")
    private String password;
}
