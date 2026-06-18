package ArkoMS.presupuesto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ArkoMS.presupuesto.dto.PresupuestoRequestDTO;
import ArkoMS.presupuesto.dto.PresupuestoResponseDTO;
import ArkoMS.presupuesto.model.Presupuesto;
import ArkoMS.presupuesto.repository.PresupuestoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PresupuestoService {
    private final PresupuestoRepository presupuestoRepository;

    public Presupuesto crearPresupuesto(PresupuestoRequestDTO presupuestoDto) {
        if (presupuestoDto.getEstado() == null || presupuestoDto.getEstado().isEmpty()) {
            presupuestoDto.setEstado("Pendiente");
        }
        Presupuesto presupuesto = new Presupuesto(
                null, // ID se generará automáticamente
                presupuestoDto.getObra(), // Obra se asignará posteriormente
                presupuestoDto.getCostoMaterial(),
                presupuestoDto.getCostoManoObra(),
                presupuestoDto.getCostoTotal(),
                presupuestoDto.getEstado()
        );
        return presupuestoRepository.save(presupuesto);
    }

    public PresupuestoResponseDTO mapToDTO(Presupuesto presupuesto) {
        return new PresupuestoResponseDTO(
                presupuesto.getId(),
                presupuesto.getObra(),
                presupuesto.getCostoMaterial(),
                presupuesto.getCostoManoObra(),
                presupuesto.getCostoTotal(),
                presupuesto.getEstado()
        );
    }
    public Optional<PresupuestoResponseDTO> obtenerPresupuestoPorId(Long id) {
        return presupuestoRepository.findById(id).map(this::mapToDTO);
    }

    public List<PresupuestoResponseDTO> obtenerTodosLosPresupuestos() {
        return presupuestoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Integer calcularCostoTotal(Long id) {
        Presupuesto presupuesto = presupuestoRepository.findById(id).orElse(null);
        if (presupuesto != null) {
            return presupuesto.getCostoMaterial() + presupuesto.getCostoManoObra();
        }
        return null;
    }

    public Optional<PresupuestoResponseDTO> actualizarPresupuesto(Long id, PresupuestoRequestDTO presupuestoActualizado) {
        Presupuesto presupuestoExistente = presupuestoRepository.findById(id).orElse(null);
        if (presupuestoExistente != null) {
            presupuestoExistente.setCostoMaterial(presupuestoActualizado.getCostoMaterial());
            presupuestoExistente.setCostoManoObra(presupuestoActualizado.getCostoManoObra());
            presupuestoExistente.setCostoTotal(calcularCostoTotal(id));
            presupuestoExistente.setEstado(presupuestoActualizado.getEstado());
            return Optional.of(mapToDTO(presupuestoRepository.save(presupuestoExistente)));
        }

        return null;
    }

    public void eliminarPresupuesto(Long id) {
        presupuestoRepository.deleteById(id);
    }

    public String CambiarEstadoPresupuesto(Long id, String nuevoEstado) {
        Presupuesto presupuesto = presupuestoRepository.findById(id).orElse(null);
        if (presupuesto != null) {
            presupuesto.setEstado(nuevoEstado);
            presupuestoRepository.save(presupuesto);
            switch (nuevoEstado) {
                case "Aprobado":
                    return "Estado del presupuesto Aprobado exitosamente.";
                case "Rechazado":
                    // Lógica para estado rechazado
                    return "Estado del presupuesto Rechazado exitosamente.";
                default:
                    //En caso de que se ingrese sin mayusculas o con un estado que no sea ni aprobado ni rechazado
                    return "Estado del presupuesto actualizado exitosamente.";
            }
        }
        return "Presupuesto no encontrado.";
    }


}
