package ArkoMS.presupuesto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ArkoMS.presupuesto.dto.FinanzaRequestDTO;
import ArkoMS.presupuesto.dto.FinanzaResponseDTO;
import ArkoMS.presupuesto.model.Finanza;
import ArkoMS.presupuesto.repository.FinanzaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FinanzaService {
    private final FinanzaRepository presupuestoRepository;

    public Finanza crearPresupuesto(FinanzaRequestDTO finanzaDto) {
        if (finanzaDto.getEstado() == null || finanzaDto.getEstado().isEmpty()) {
            finanzaDto.setEstado("Pendiente");
        }
        Finanza finanza = new Finanza(
                null, // ID se generará automáticamente
                finanzaDto.getCostoMaterial(),
                finanzaDto.getCostoManoObra(),
                finanzaDto.getCostoTotal(),
                finanzaDto.getEstado()
        );
        return presupuestoRepository.save(finanza);
    }

    public FinanzaResponseDTO mapToDTO(Finanza finanza) {
        return new FinanzaResponseDTO(
                finanza.getId(),
                finanza.getCostoMaterial(),
                finanza.getCostoManoObra(),
                finanza.getCostoTotal(),
                finanza.getEstado()
        );
    }
    public Optional<FinanzaResponseDTO> obtenerPresupuestoPorId(Long id) {
        return presupuestoRepository.findById(id).map(this::mapToDTO);
    }

    public List<FinanzaResponseDTO> obtenerTodosLosPresupuestos() {
        return presupuestoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Integer calcularCostoTotal(Long id) {
        Finanza presupuesto = presupuestoRepository.findById(id).orElse(null);
        if (presupuesto != null) {
            return presupuesto.getCostoMaterial() + presupuesto.getCostoManoObra();
        }
        return null;
    }

    public Optional<FinanzaResponseDTO> actualizarPresupuesto(Long id, FinanzaRequestDTO presupuestoActualizado) {
        Finanza presupuestoExistente = presupuestoRepository.findById(id).orElse(null);
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
        Finanza presupuesto = presupuestoRepository.findById(id).orElse(null);
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
