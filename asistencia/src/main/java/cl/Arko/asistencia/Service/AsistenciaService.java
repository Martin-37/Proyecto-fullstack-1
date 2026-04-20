package cl.Arko.asistencia.Service;

import cl.Arko.asistencia.Repository.AsistenciaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AsistenciaService {
    @Autowired
    private AsistenciaRepository asistenciaRepository;


}
