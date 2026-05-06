package cl.dsy1103.microservicios.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import cl.dsy1103.microservicios.dto.ObraRequestDTO;
import cl.dsy1103.microservicios.dto.ObraResponseDTO;
import cl.dsy1103.microservicios.model.Obra;
import cl.dsy1103.microservicios.model.Trabajador;
import cl.dsy1103.microservicios.repository.ObraRepository;
import cl.dsy1103.microservicios.repository.TrabajadorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ObraService.java
 * ObraService.java
 * Logica de negocio del microservicio de Obras.
 *   1. Los metodos reciben/devuelven DTOs, no entidades.
 *   2. mapToDTO() convierte Obra -> ObraResponseDTO (privado, solo este Service).
 *   3. guardar() y actualizar() reciben ObraRequestDTO.
 *   4. Si el numrun del responsable no existe, lanza RuntimeException
 *      -> GlobalExceptionHandler la devuelve como 400 Bad Request.
 */
@Service
@RequiredArgsConstructor
public class ObraService {

    private final ObraRepository obraRepository;
    // Necesario para buscar el Trabajador por numrun
    // cuando el DTO trae solo responsableNumrun (un Long).
    private final TrabajadorRepository trabajadorRepository;

    // ── MAPEO PRIVADO: Entidad -> ResponseDTO ──────────────────────────────
    // Solo lo usa este Service. El Controller y el Repository
    // nunca conocen el DTO ni la entidad del otro.
    private ObraResponseDTO mapToDTO(Obra obra) {
        String nombreCompleto = obra.getResponsable().getPnombreTrab()
                + " " + obra.getResponsable().getApaternoTrab();
        return new ObraResponseDTO(
                obra.getNombreObra(),
                obra.getUbicacion(),
                nombreCompleto,
                obra.getResponsable().getCargo(),
                obra.getFechaInicio(),
                obra.getEstado()
        );
    }

    // ── OBTENER TODAS ──────────────────────────────────────────────────────
    public List<ObraResponseDTO> obtenerTodas() {
        return obraRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ── OBTENER POR NOMBRE (PK) ────────────────────────────────────────────
    public Optional<ObraResponseDTO> obtenerPorNombre(String nombreObra) {
        return obraRepository.findById(nombreObra).map(this::mapToDTO);
    }

    // ── GUARDAR ────────────────────────────────────────────────────────────
    // Si el responsableNumrun no existe, lanza RuntimeException.
    // GlobalExceptionHandler la captura y devuelve 400.
    public ObraResponseDTO guardar(ObraRequestDTO dto) {
        Trabajador responsable = trabajadorRepository
                .findById(dto.getResponsableNumrun())
                .orElseThrow(() -> new RuntimeException(
                        "Trabajador no encontrado con numrun: " + dto.getResponsableNumrun()));

        Obra obra = new Obra(
                dto.getNombreObra(),
                dto.getUbicacion(),
                responsable,
                dto.getFechaInicio(),
                dto.getEstado()
        );
        return mapToDTO(obraRepository.save(obra));
    }

    // ── ACTUALIZAR ─────────────────────────────────────────────────────────
    public Optional<ObraResponseDTO> actualizar(String nombreObra, ObraRequestDTO dto) {
        return obraRepository.findById(nombreObra).map(existente -> {
            Trabajador responsable = trabajadorRepository
                    .findById(dto.getResponsableNumrun())
                    .orElseThrow(() -> new RuntimeException(
                            "Trabajador no encontrado con numrun: " + dto.getResponsableNumrun()));

            existente.setUbicacion(dto.getUbicacion());
            existente.setResponsable(responsable);
            existente.setFechaInicio(dto.getFechaInicio());
            existente.setEstado(dto.getEstado());
            return mapToDTO(obraRepository.save(existente));
        });
    }

    // ── ELIMINAR ───────────────────────────────────────────────────────────
    public void eliminar(String nombreObra) {
        obraRepository.deleteById(nombreObra);
    }

    // ── BUSQUEDAS ──────────────────────────────────────────────────────────
    public List<ObraResponseDTO> buscarPorEstado(String estado) {
        return obraRepository.findByEstado(estado)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ObraResponseDTO> buscarPorUbicacion(String ubicacion) {
        return obraRepository.findByUbicacionContainingIgnoreCase(ubicacion)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ObraResponseDTO> buscarPorResponsable(Long numrun) {
        return obraRepository.findByResponsableNumrun(numrun)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
