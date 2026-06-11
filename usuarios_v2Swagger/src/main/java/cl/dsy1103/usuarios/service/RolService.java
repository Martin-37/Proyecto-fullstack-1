package cl.dsy1103.usuarios.service;

import cl.dsy1103.usuarios.dto.RolRequestDTO;
import cl.dsy1103.usuarios.dto.RolResponseDTO;
import cl.dsy1103.usuarios.model.Rol;
import cl.dsy1103.usuarios.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    private RolResponseDTO mapToDTO(Rol rol) {
        return new RolResponseDTO(
                rol.getId(),
                rol.getNombreRol(),
                rol.getDescripcion(),
                rol.getEstado()
        );
    }

    public List<RolResponseDTO> obtenerTodos() {
        return rolRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<RolResponseDTO> obtenerPorId(Long id) {
        return rolRepository.findById(id).map(this::mapToDTO);
    }

    public RolResponseDTO guardar(RolRequestDTO dto) {
        if (rolRepository.findByNombreRolIgnoreCase(dto.getNombreRol()).isPresent()) {
            throw new RuntimeException("Ya existe un rol con el nombre: " + dto.getNombreRol());
        }
        Rol rol = new Rol(null, dto.getNombreRol(), dto.getDescripcion(), dto.getEstado());
        return mapToDTO(rolRepository.save(rol));
    }

    public Optional<RolResponseDTO> actualizar(Long id, RolRequestDTO dto) {
        return rolRepository.findById(id).map(existente -> {
            Optional<Rol> duplicado = rolRepository.findByNombreRolIgnoreCase(dto.getNombreRol());
            if (duplicado.isPresent() && !duplicado.get().getId().equals(id)) {
                throw new RuntimeException("Ya existe un rol con el nombre: " + dto.getNombreRol());
            }
            existente.setNombreRol(dto.getNombreRol());
            existente.setDescripcion(dto.getDescripcion());
            existente.setEstado(dto.getEstado());
            return mapToDTO(rolRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }

    public List<RolResponseDTO> buscarPorEstado(String estado) {
        return rolRepository.findByEstado(estado)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<RolResponseDTO> buscarPorDescripcion(String texto) {
        return rolRepository.buscarPorDescripcion(texto)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<RolResponseDTO> busquedaGlobal(String texto) {
        return rolRepository.busquedaGlobal(texto)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
