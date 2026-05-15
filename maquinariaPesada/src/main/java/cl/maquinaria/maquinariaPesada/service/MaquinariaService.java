package cl.maquinaria.maquinariaPesada.service;

import cl.maquinaria.maquinariaPesada.dto.MaquinariaRequestDTO;
import cl.maquinaria.maquinariaPesada.dto.MaquinariaResponseDTO;
import cl.maquinaria.maquinariaPesada.model.Maquinaria;
import cl.maquinaria.maquinariaPesada.repository.MaquinariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ═══════════════════════════════════════════════════
 * MaquinariaService.java
 * RESPONSABILIDADES:
 *   1. Los métodos reciben/devuelven DTOs, no Maquinaria
 *   2. Se usa mapToDTO() para convertir entidad → DTO
 *   3. guardar() y actualizar() reciben MaquinariaRequestDTO
 *   4. Las excepciones son capturadas por GlobalExceptionHandler
 *      y devueltas como 400 Bad Request al cliente
 * ═══════════════════════════════════════════════════
 */
@Service
@RequiredArgsConstructor
public class MaquinariaService {

    private final MaquinariaRepository maquinariaRepository;

    // ── MAPEO PRIVADO: Entidad → ResponseDTO ─────────
    private MaquinariaResponseDTO mapToDTO(Maquinaria maquinaria) {
        return new MaquinariaResponseDTO(
                maquinaria.getIdMaquinaria(),
                maquinaria.getNombre(),
                maquinaria.getEquipo(),
                maquinaria.getEstado(),
                maquinaria.getUbicacion(),
                maquinaria.getResponsable(),
                maquinaria.getFechaMantenimiento()
        );
    }

    // ── OBTENER TODOS ────────────────────────────────
    public List<MaquinariaResponseDTO> obtenerTodos() {
        return maquinariaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ── OBTENER POR ID ───────────────────────────────
    public Optional<MaquinariaResponseDTO> obtenerPorId(Long id) {
        return maquinariaRepository.findById(id).map(this::mapToDTO);
    }

    // ── GUARDAR ──────────────────────────────────────
    public MaquinariaResponseDTO guardar(MaquinariaRequestDTO dto) {
        Maquinaria maquinaria = new Maquinaria(
                null,
                dto.getNombre(),
                dto.getEquipo(),
                dto.getEstado(),
                dto.getUbicacion(),
                dto.getResponsable(),
                dto.getFechaMantenimiento()
        );
        return mapToDTO(maquinariaRepository.save(maquinaria));
    }

    // ── ACTUALIZAR ───────────────────────────────────
    public Optional<MaquinariaResponseDTO> actualizar(Long id, MaquinariaRequestDTO dto) {
        return maquinariaRepository.findById(id).map(existente -> {
            existente.setNombre(dto.getNombre());
            existente.setEquipo(dto.getEquipo());
            existente.setEstado(dto.getEstado());
            existente.setUbicacion(dto.getUbicacion());
            existente.setResponsable(dto.getResponsable());
            existente.setFechaMantenimiento(dto.getFechaMantenimiento());
            return mapToDTO(maquinariaRepository.save(existente));
        });
    }

    // ── ELIMINAR ─────────────────────────────────────
    public void eliminar(Long id) {
        maquinariaRepository.deleteById(id);
    }

    // ── BÚSQUEDAS PERSONALIZADAS ────────────────────
    public List<MaquinariaResponseDTO> buscarPorNombre(String nombre) {
        return maquinariaRepository.findByNombreContainingIgnoreCase(nombre)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<MaquinariaResponseDTO> buscarPorEstado(String estado) {
        return maquinariaRepository.findByEstado(estado)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<MaquinariaResponseDTO> buscarPorResponsable(String responsable) {
        return maquinariaRepository.findByResponsableContainingIgnoreCase(responsable)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<MaquinariaResponseDTO> buscarPorEquipo(String equipo) {
        return maquinariaRepository.findByEquipoContainingIgnoreCase(equipo)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<MaquinariaResponseDTO> buscarPorUbicacion(String ubicacion) {
        return maquinariaRepository.buscarPorUbicacion(ubicacion)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<MaquinariaResponseDTO> buscarConMantenimientoHasta(LocalDate fecha) {
        return maquinariaRepository.buscarConMantenimientoHasta(fecha)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<MaquinariaResponseDTO> busquedaGlobal(String texto) {
        return maquinariaRepository.busquedaGlobal(texto)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
