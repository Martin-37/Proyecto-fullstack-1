package cl.Arko.asistencia.Service;

import cl.Arko.asistencia.Repository.AsistenciaRepository;
import cl.Arko.asistencia.modelo.Asistencia;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;

    public Asistencia marcarEntrada(Integer numrun_emp, Date fecha){
        Asistencia asistencia = new Asistencia(numrun_emp,fecha,null,null,false);
        asistencia.setHora_llegada(LocalTime.now());
        return asistenciaRepository.save(asistencia);
    }

    public Asistencia marcarSalida(Integer numrun_emp, Date fecha){
        Asistencia asistencia = asistenciaRepository.findByNumrunAndFecha(numrun_emp,fecha).orElseThrow(() ->new RuntimeException("No se encontró registro de entrada para este usuario hoy."));
        if (asistencia.isEstado()){
            System.out.println("Ya se marco la salida");
            return asistencia;
        }
        asistencia.setHora_salida(LocalTime.now());
        asistencia.setEstado(true);

        return asistenciaRepository.save(asistencia);
    }

    public double calcularHorasTrabajadas(Integer numrun_emp, Date fecha){
        Asistencia asistencia = asistenciaRepository.findByNumrunAndFecha(numrun_emp,fecha).orElseThrow(() ->new RuntimeException("No se encontró registro de entrada para este usuario hoy."));
        if (!asistencia.isEstado()){
            System.out.println("Aún no se ha marcado la salida");
            return 0.0;
        }
        LocalTime horaEntrada = asistencia.getHora_llegada();
        LocalTime horaSalida = asistencia.getHora_salida();

        long horasTrabajadas = java.time.Duration.between(horaEntrada, horaSalida).toHours();
        return horasTrabajadas;
    }



    public double calcularHorasTotalesTrabajadas(Integer numrun_emp, Date fecha1, Date fecha2){
        List<Asistencia> asistencias = asistenciaRepository.findByFechaBetween(fecha1, fecha2);
        double horasTotales = 0.0;
        for (Asistencia asistencia : asistencias) {
            if (asistencia.getNumrun_emp().equals(numrun_emp) && asistencia.isEstado()) {
                LocalTime horaEntrada = asistencia.getHora_llegada();
                LocalTime horaSalida = asistencia.getHora_salida();
                horasTotales += Duration.between(horaEntrada, horaSalida).toHours();
            }
        }
        return horasTotales;
    }


}
