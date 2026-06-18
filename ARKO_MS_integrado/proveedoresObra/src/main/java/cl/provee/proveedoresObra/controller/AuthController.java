package cl.provee.proveedoresObra.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.provee.proveedoresObra.dto.LoginRequestDTO;
import cl.provee.proveedoresObra.dto.LoginResponseDTO;
import cl.provee.proveedoresObra.security.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Login y generación de tokens JWT")
public class AuthController {

    private final JwtUtil jwtUtil;


    private static final Map<String, String> CREDENCIALES = Map.of(
            "admin", "admin123",
            "usuario", "usuario123"
    );

    private static final Map<String, String> ROLES = Map.of(
            "admin", "ADMIN",
            "usuario", "USER"
    );

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        String passwordEsperada = CREDENCIALES.get(dto.getUsername());

        if (passwordEsperada == null || !passwordEsperada.equals(dto.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        }

        String rol = ROLES.get(dto.getUsername());
        String token = jwtUtil.generarToken(dto.getUsername(), rol);

        return ResponseEntity.ok(new LoginResponseDTO(token, "Bearer", dto.getUsername(), rol));
    }
}
