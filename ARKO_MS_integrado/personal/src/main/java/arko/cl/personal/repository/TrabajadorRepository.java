package arko.cl.personal.repository;

import arko.cl.personal.model.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {

    Optional<Trabajador> findByMailTrab(String mailTrab);

    List<Trabajador> findByCargo(String cargo);

    List<Trabajador> findByEstado(String estado);

    @Query("SELECT t FROM Trabajador t WHERE LOWER(CONCAT(t.pnombreTrab, ' ', t.snombreTrab, ' ', t.papellidoTrab, ' ', t.sapellidoTrab)) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Trabajador> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT t FROM Trabajador t WHERE t.cargo = :cargo AND t.estado = :estado")
    List<Trabajador> findByCargoAndEstado(@Param("cargo") String cargo, @Param("estado") String estado);

    @Query("SELECT COUNT(t) FROM Trabajador t WHERE t.cargo = :cargo")
    Long contarPorCargo(@Param("cargo") String cargo);

    @Query("SELECT COUNT(t) FROM Trabajador t WHERE t.estado = :estado")
    Long contarPorEstado(@Param("estado") String estado);
}
