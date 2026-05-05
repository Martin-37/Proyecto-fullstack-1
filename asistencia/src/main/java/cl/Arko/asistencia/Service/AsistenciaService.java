package cl.Arko.asistencia.Service;

import cl.Arko.asistencia.Repository.AsistenciaRepository;
import cl.Arko.asistencia.modelo.Asistencia;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    //Variable local de la debido a multiples usos de esta misma
    LocalTime horaActual = LocalTime.now();

    public Asistencia marcarEntrada(Integer numrun_emp, Date fecha){
        Asistencia asistencia = new Asistencia(numrun_emp,fecha, horaActual,null,false);

        return asistenciaRepository.save(asistencia);
    }

    public Asistencia marcarSalida(Integer numrun_emp, Date fecha){
        Asistencia asistencia = asistenciaRepository.findByNumrunAndFecha(numrun_emp,fecha).orElseThrow(() ->new RuntimeException("No se encontró registro de entrada para este usuario hoy."));
        if (asistencia.isEstado()){
            System.out.println("Ya se marco la salida");
            return asistencia;
        }
        asistencia.setHora_salida(horaActual);
        asistencia.setEstado(true);

        return asistenciaRepository.save(asistencia);
    }


}
