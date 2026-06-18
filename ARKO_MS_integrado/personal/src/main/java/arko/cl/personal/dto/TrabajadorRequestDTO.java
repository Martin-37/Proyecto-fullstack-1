package arko.cl.personal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrabajadorRequestDTO {

    @NotNull(message = "El RUN no puede ser nulo")
    @Min(value = 1000000, message = "El RUN debe tener al menos 7 dígitos")
    @Max(value = 99999999L, message = "El RUN no puede tener más de 8 dígitos")
    private Long numrunTrab;

    @NotBlank(message = "El DV no puede estar vacío")
    @Size(min = 1, max = 1, message = "El DV debe ser de exactamente 1 carácter (0-9 o K)")
    private String dvrunTrab;

    @NotBlank(message = "El primer nombre no puede estar vacío")
    @Size(min = 2, max = 30, message = "El primer nombre debe tener entre 2 y 30 caracteres")
    private String pnombreTrab;

    @NotBlank(message = "El segundo nombre no puede estar vacío")
    @Size(min = 2, max = 30, message = "El segundo nombre debe tener entre 2 y 30 caracteres")
    private String snombreTrab;

    @NotBlank(message = "El primer apellido no puede estar vacío")
    @Size(min = 2, max = 30, message = "El primer apellido debe tener entre 2 y 30 caracteres")
    private String papellidoTrab;

    @NotBlank(message = "El segundo apellido no puede estar vacío")
    @Size(min = 2, max = 30, message = "El segundo apellido debe tener entre 2 y 30 caracteres")
    private String sapellidoTrab;

    @NotBlank(message = "El cargo no puede estar vacío")
    @Size(min = 3, max = 30, message = "El cargo debe tener entre 3 y 30 caracteres")
    private String cargo;

    @Size(max = 15, message = "El teléfono debe tener máximo 15 caracteres")
    private String fono;

    @Size(max = 100, message = "El email debe tener máximo 100 caracteres")
    private String mailTrab;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}
