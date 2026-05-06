package cl.Arko.asistencia.Repository;


import cl.Arko.asistencia.modelo.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {

    @Query("SELECT a FROM asistencia a WHERE a.numrun_emp = :numrun_emp AND a.fecha = :fecha")
    Optional<Asistencia> findByNumrunAndFecha(@Param("numrun_emp") Integer numrun_emp, @Param("fecha") Date fecha);

    @Query("SELECT a FROM asistencia a WHERE a.fecha BETWEEN :fecha1 AND :fecha2")
    List<Asistencia> findByFechaBetween(@Param("fecha1") Date fecha1, @Param("fecha2") Date fecha2);
}
