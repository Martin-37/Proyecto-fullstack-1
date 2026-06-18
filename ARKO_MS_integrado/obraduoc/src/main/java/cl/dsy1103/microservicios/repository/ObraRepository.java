package cl.dsy1103.microservicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.dsy1103.microservicios.model.Obra;

import java.util.List;

public interface ObraRepository extends JpaRepository<Obra, String> {

    // --- TIPO 1: QUERY METHODS (convencion de nombre) ---

    // SELECT * FROM obras WHERE estado = ?
    List<Obra> findByEstado(String estado);

    // SELECT * FROM obras WHERE UPPER(ubicacion) LIKE UPPER('%?%')
    List<Obra> findByUbicacionContainingIgnoreCase(String ubicacion);

    // --- TIPO 2: @QUERY con JPQL ---

    // Busca obras por el numrun del responsable
    @Query("SELECT o FROM Obra o WHERE o.responsable.numrunTrab = :numrun")
    List<Obra> findByResponsableNumrun(@Param("numrun") Long numrun);

    // Busca obras por estado, ordenadas por fecha de inicio descendente
    @Query("SELECT o FROM Obra o WHERE o.estado = :estado ORDER BY o.fechaInicio DESC")
    List<Obra> findByEstadoOrdenadas(@Param("estado") String estado);

    // --- TIPO 3: SQL NATIVO ---
    // Busca obras cuyo nombre contenga el texto indicado (nativeQuery)
    @Query(
        value = "SELECT * FROM obras WHERE nombre_obra LIKE CONCAT('%', :texto, '%')",
        nativeQuery = true
    )
    List<Obra> buscarPorNombreNativo(@Param("texto") String texto);
}
