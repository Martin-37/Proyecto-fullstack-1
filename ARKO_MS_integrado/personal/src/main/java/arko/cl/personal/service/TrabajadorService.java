package arko.cl.personal.service;

import arko.cl.personal.dto.TrabajadorRequestDTO;
import arko.cl.personal.dto.TrabajadorResponseDTO;
import arko.cl.personal.model.Trabajador;
import arko.cl.personal.repository.TrabajadorRepository;
import arko.cl.personal.webclient.AsistenciaClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final AsistenciaClient asistenciaClient;

    public TrabajadorService(TrabajadorRepository trabajadorRepository, AsistenciaClient asistenciaClient) {
        this.trabajadorRepository = trabajadorRepository;
        this.asistenciaClient = asistenciaClient;
    }

    public TrabajadorResponseDTO crearTrabajador(TrabajadorRequestDTO dto) {
        if (trabajadorRepository.findByMailTrab(dto.getMailTrab()).isPresent()) {
            throw new RuntimeException("El email " + dto.getMailTrab() + " ya existe");
        }
        Trabajador trabajador = new Trabajador();
        mapearDTOaEntidad(dto, trabajador);
        Trabajador guardado = trabajadorRepository.save(trabajador);
        return mapearEntidadaDTO(guardado);
    }

    public TrabajadorResponseDTO obtenerTrabajador(Long numrunTrab) {
        Trabajador trabajador = trabajadorRepository.findById(numrunTrab)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado: " + numrunTrab));
        return mapearEntidadaDTO(trabajador);
    }

    public List<TrabajadorResponseDTO> obtenerTodos() {
        return trabajadorRepository.findAll().stream()
                .map(this::mapearEntidadaDTO)
                .collect(Collectors.toList());
    }

    public TrabajadorResponseDTO actualizarTrabajador(Long numrunTrab, TrabajadorRequestDTO dto) {
        Trabajador trabajador = trabajadorRepository.findById(numrunTrab)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado: " + numrunTrab));

        if (!trabajador.getMailTrab().equals(dto.getMailTrab()) &&
                trabajadorRepository.findByMailTrab(dto.getMailTrab()).isPresent()) {
            throw new RuntimeException("El email " + dto.getMailTrab() + " ya existe");
        }

        mapearDTOaEntidad(dto, trabajador);
        Trabajador actualizado = trabajadorRepository.save(trabajador);
        return mapearEntidadaDTO(actualizado);
    }

    public void eliminarTrabajador(Long numrunTrab) {
        Trabajador trabajador = trabajadorRepository.findById(numrunTrab)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado: " + numrunTrab));
        trabajadorRepository.delete(trabajador);
    }

    public List<TrabajadorResponseDTO> buscarPorCargo(String cargo) {
        return trabajadorRepository.findByCargo(cargo).stream()
                .map(this::mapearEntidadaDTO)
                .collect(Collectors.toList());
    }

    public List<TrabajadorResponseDTO> buscarPorEstado(String estado) {
        return trabajadorRepository.findByEstado(estado).stream()
                .map(this::mapearEntidadaDTO)
                .collect(Collectors.toList());
    }

    public List<TrabajadorResponseDTO> buscarPorNombre(String nombre) {
        return trabajadorRepository.buscarPorNombre(nombre).stream()
                .map(this::mapearEntidadaDTO)
                .collect(Collectors.toList());
    }

    public List<TrabajadorResponseDTO> buscarPorCargoYEstado(String cargo, String estado) {
        return trabajadorRepository.findByCargoAndEstado(cargo, estado).stream()
                .map(this::mapearEntidadaDTO)
                .collect(Collectors.toList());
    }

    public Long contarPorCargo(String cargo) {
        return trabajadorRepository.contarPorCargo(cargo);
    }

    public Long contarPorEstado(String estado) {
        return trabajadorRepository.contarPorEstado(estado);
    }

    private void mapearDTOaEntidad(TrabajadorRequestDTO dto, Trabajador trabajador) {
        trabajador.setNumrunTrab(dto.getNumrunTrab());
        trabajador.setDvrunTrab(dto.getDvrunTrab());
        trabajador.setPnombreTrab(dto.getPnombreTrab());
        trabajador.setSnombreTrab(dto.getSnombreTrab());
        trabajador.setPapellidoTrab(dto.getPapellidoTrab());
        trabajador.setSapellidoTrab(dto.getSapellidoTrab());
        trabajador.setCargo(dto.getCargo());
        trabajador.setFono(dto.getFono());
        trabajador.setMailTrab(dto.getMailTrab());
        trabajador.setEstado(dto.getEstado());
    }

    private TrabajadorResponseDTO mapearEntidadaDTO(Trabajador trabajador) {
        TrabajadorResponseDTO dto = new TrabajadorResponseDTO();
        dto.setNumrunTrab(trabajador.getNumrunTrab());
        dto.setDvrunTrab(trabajador.getDvrunTrab());
        dto.setPnombreTrab(trabajador.getPnombreTrab());
        dto.setSnombreTrab(trabajador.getSnombreTrab());
        dto.setPapellidoTrab(trabajador.getPapellidoTrab());
        dto.setSapellidoTrab(trabajador.getSapellidoTrab());
        dto.setCargo(trabajador.getCargo());
        dto.setFono(trabajador.getFono());
        dto.setMailTrab(trabajador.getMailTrab());
        dto.setEstado(trabajador.getEstado());

        // Obtener datos de asistencia del microservicio de asistencia
        dto.setAsistenciaInfo(asistenciaClient.obtenerAsistenciaByTrabajador(trabajador.getNumrunTrab()));

        return dto;
    }
}
