package cl.dsy1103.usuarios.service;

import cl.dsy1103.usuarios.dto.LoginRequestDTO;
import cl.dsy1103.usuarios.dto.LoginResponseDTO;
import cl.dsy1103.usuarios.model.Usuario;
import cl.dsy1103.usuarios.repository.UsuarioRepository;
import cl.dsy1103.usuarios.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioAuthService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioAuthService.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioAuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        logger.info("Intento de login para usuario: {}", request.getNombreUsuario());

        Usuario usuario = usuarioRepository.findByNombreUsuario(request.getNombreUsuario())
                .orElseThrow(() -> {
                    logger.warn("Usuario no encontrado: {}", request.getNombreUsuario());
                    return new RuntimeException("Usuario o contraseña inválidos");
                });

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            logger.warn("Contraseña incorrecta para usuario: {}", request.getNombreUsuario());
            throw new RuntimeException("Usuario o contraseña inválidos");
        }

        if (!"Activo".equals(usuario.getEstado())) {
            logger.warn("Usuario inactivo: {}", request.getNombreUsuario());
            throw new RuntimeException("Usuario inactivo");
        }

        String token = jwtUtil.generateToken(usuario.getNombreUsuario(), usuario.getRol().getNombreRol());
        logger.info("Login exitoso para usuario: {}", request.getNombreUsuario());

        return new LoginResponseDTO(token, usuario.getNombreUsuario(), usuario.getRol().getNombreRol());
    }
}
