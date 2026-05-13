package cl.provee.proveedoresObra.service;

import cl.provee.proveedoresObra.dto.ProveedorRequestDTO;
import cl.provee.proveedoresObra.dto.ProveedorResponseDTO;
import cl.provee.proveedoresObra.model.Proveedor;
import cl.provee.proveedoresObra.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    private ProveedorResponseDTO mapToDTO(Proveedor proveedor) {
        return new ProveedorResponseDTO(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getContacto(),
                proveedor.getTelefono(),
                proveedor.getDireccion(),
                proveedor.getEstado()
        );
    }

    public List<ProveedorResponseDTO> obtenerTodos() {
        return proveedorRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProveedorResponseDTO> obtenerPorId(Long id) {
        return proveedorRepository.findById(id).map(this::mapToDTO);
    }

    public ProveedorResponseDTO guardar(ProveedorRequestDTO dto) {
        Proveedor proveedor = new Proveedor(
                null,
                dto.getNombre(),
                dto.getContacto(),
                dto.getTelefono(),
                dto.getDireccion(),
                dto.getEstado()
        );
        return mapToDTO(proveedorRepository.save(proveedor));
    }

    public Optional<ProveedorResponseDTO> actualizar(Long id, ProveedorRequestDTO dto) {
        return proveedorRepository.findById(id).map(existente -> {
            existente.setNombre(dto.getNombre());
            existente.setContacto(dto.getContacto());
            existente.setTelefono(dto.getTelefono());
            existente.setDireccion(dto.getDireccion());
            existente.setEstado(dto.getEstado());
            return mapToDTO(proveedorRepository.save(existente));
        });
    }

    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }

    public List<ProveedorResponseDTO> buscarPorNombre(String nombre) {
        return proveedorRepository.findByNombreContainingIgnoreCase(nombre)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ProveedorResponseDTO> buscarPorEstado(String estado) {
        return proveedorRepository.findByEstado(estado)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ProveedorResponseDTO> buscarPorContacto(String contacto) {
        return proveedorRepository.findByContactoContainingIgnoreCase(contacto)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ProveedorResponseDTO> buscarPorTelefono(String telefono) {
        return proveedorRepository.findByTelefono(telefono)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ProveedorResponseDTO> buscarPorDireccion(String direccion) {
        return proveedorRepository.buscarPorDireccion(direccion)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ProveedorResponseDTO> busquedaGlobal(String texto) {
        return proveedorRepository.busquedaGlobal(texto)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
