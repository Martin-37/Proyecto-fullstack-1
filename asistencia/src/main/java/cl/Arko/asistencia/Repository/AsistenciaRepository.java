package cl.Arko.asistencia.Repository;


import cl.Arko.asistencia.modelo.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Date;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, String> {


    Asistencia marcarLLegada(String rutEmp, Date fecha, LocalTime hora_llegada);

    Asistencia marcarSalida(String rutEmp,Date fecha, LocalTime hora_salida);

    Integer calcularHoras(Asistencia asistencia);
}
