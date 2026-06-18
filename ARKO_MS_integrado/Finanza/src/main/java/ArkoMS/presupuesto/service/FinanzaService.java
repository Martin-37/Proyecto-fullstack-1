package ArkoMS.presupuesto.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ArkoMS.presupuesto.model.Finanza;
import ArkoMS.presupuesto.repository.FinanzaRepository;
import ArkoMS.presupuesto.dto.FinanzaRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FinanzaService {
    private final FinanzaRepository presupuestoRepository;

    /**
     * Devuelve la lista de `Finanza` cuyas fechas están entre `inicio` y `fin` (inclusive).
     */
    public List<Finanza> obtenerFinanzasEntreFechas(LocalDate inicio, LocalDate fin) {
        return presupuestoRepository.findByFechaBetween(inicio, fin);
    }

    /**
     * Devuelve las finanzas que coinciden exactamente con la fecha indicada.
     */
    public List<Finanza> obtenerFinanzasPorFecha(LocalDate fecha) {
        return presupuestoRepository.findByFecha(fecha);
    }

    /**
     * Devuelve todas las finanzas almacenadas en la base de datos.
     */
    public List<Finanza> obtenerTodasFinanzas() {
        return presupuestoRepository.findAll();
    }

    /**
     * Crea una nueva finanza a partir del DTO.
     */
    public Finanza crearFinanza(FinanzaRequestDTO dto) {
        Finanza finanza = new Finanza(
                null,
                dto.getGastos(),
                dto.getIngreso(),
                dto.getFecha(),
                dto.getDescripcion()
        );
        return presupuestoRepository.save(finanza);
    }

    /**
     * Actualiza una finanza existente por su ID.
     */
    public Finanza actualizarFinanza(Long id, FinanzaRequestDTO dto) {
        Finanza finanza = presupuestoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Finanza no encontrada con ID: " + id));
        finanza.setGastos(dto.getGastos());
        finanza.setIngreso(dto.getIngreso());
        finanza.setFecha(dto.getFecha());
        finanza.setDescripcion(dto.getDescripcion());
        return presupuestoRepository.save(finanza);
    }

    /**
     * Elimina una finanza por su ID (simple, sin validaciones).
     */
    public boolean eliminarFinanza(Long id) {
        return presupuestoRepository.findById(id).map(f -> {
            presupuestoRepository.deleteById(id);
            return true;
        }).orElse(false);
    }

}
