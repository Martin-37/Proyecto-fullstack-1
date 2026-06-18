package cl.dsy1103.usuarios.service;

import cl.dsy1103.usuarios.dto.UsuarioRequestDTO;
import cl.dsy1103.usuarios.dto.UsuarioResponseDTO;
import cl.dsy1103.usuarios.model.Rol;
import cl.dsy1103.usuarios.model.Usuario;
import cl.dsy1103.usuarios.repository.RolRepository;
import cl.dsy1103.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    private UsuarioResponseDTO mapToDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombreUsuario(),
                usuario.getRol().getNombreRol(),
                usuario.getEmail(),
                usuario.getEstado()
        );
    }

    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> obtenerPorId(Long id) {
        return usuarioRepository.findById(id).map(this::mapToDTO);
    }

    public UsuarioResponseDTO guardar(UsuarioRequestDTO dto) {
        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new RuntimeException(
                        "Rol no encontrado con id: " + dto.getRolId()));

        if (!usuarioRepository.findByNombreUsuarioContainingIgnoreCase(dto.getNombreUsuario()).isEmpty()) {
            throw new RuntimeException("Ya existe un usuario con el nombre: " + dto.getNombreUsuario());
        }
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con el email: " + dto.getEmail());
        }

        Usuario usuario = new Usuario(
                null,
                dto.getNombreUsuario(),
                rol,
                dto.getEmail(),
                dto.getPassword(),
                dto.getEstado()
        );
        return mapToDTO(usuarioRepository.save(usuario));
    }

    public Optional<UsuarioResponseDTO> actualizar(Long id, UsuarioRequestDTO dto) {
        return usuarioRepository.findById(id).map(existente -> {
            Rol rol = rolRepository.findById(dto.getRolId())
                    .orElseThrow(() -> new RuntimeException(
                            "Rol no encontrado con id: " + dto.getRolId()));

            if (!existente.getEmail().equalsIgnoreCase(dto.getEmail()) &&
                    usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new RuntimeException("El email ya esta registrado: " + dto.getEmail());
            }

            existente.setNombreUsuario(dto.getNombreUsuario());
            existente.setRol(rol);
            existente.setEmail(dto.getEmail());
            existente.setPassword(dto.getPassword());
            existente.setEstado(dto.getEstado());
            return mapToDTO(usuarioRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public List<UsuarioResponseDTO> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreUsuarioContainingIgnoreCase(nombre)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> buscarPorRol(Long rolId) {
        return usuarioRepository.findByRolId(rolId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> buscarPorEstado(String estado) {
        return usuarioRepository.findByEstado(estado)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Optional<UsuarioResponseDTO> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).map(this::mapToDTO);
    }

    public List<UsuarioResponseDTO> busquedaGlobal(String texto) {
        return usuarioRepository.busquedaGlobal(texto)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
