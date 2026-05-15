package cl.maquinaria.maquinariaPesada.repository;

import cl.maquinaria.maquinariaPesada.model.Maquinaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MaquinariaRepository extends JpaRepository<Maquinaria, Long> {

    // ── TIPO 1: QUERY METHODS [Convención de Nombre] ─────────────

    // → SELECT * FROM maquinaria WHERE UPPER(nombre) LIKE UPPER('%?%')
    List<Maquinaria> findByNombreContainingIgnoreCase(String nombre);

    // → SELECT * FROM maquinaria WHERE estado = ?
    List<Maquinaria> findByEstado(String estado);

    // → SELECT * FROM maquinaria WHERE UPPER(responsable) LIKE UPPER('%?%')
    List<Maquinaria> findByResponsableContainingIgnoreCase(String responsable);

    // → SELECT * FROM maquinaria WHERE UPPER(equipo) LIKE UPPER('%?%')
    List<Maquinaria> findByEquipoContainingIgnoreCase(String equipo);

    // ── TIPO 2: @QUERY CON JPQL ──────────────────────

    // Búsqueda por ubicación con JPQL
    @Query("SELECT m FROM Maquinaria m WHERE UPPER(m.ubicacion) LIKE UPPER(CONCAT('%', :ubicacion, '%'))")
    List<Maquinaria> buscarPorUbicacion(@Param("ubicacion") String ubicacion);

    // Maquinaria con mantenimiento próximo (hasta la fecha indicada)
    @Query("SELECT m FROM Maquinaria m WHERE m.fechaMantenimiento <= :fecha")
    List<Maquinaria> buscarConMantenimientoHasta(@Param("fecha") LocalDate fecha);

    // ── TIPO 3: SQL NATIVO ───────────────────────────

    // Búsqueda global en nombre, equipo o responsable
    @Query(
        value = "SELECT * FROM maquinaria WHERE nombre LIKE CONCAT('%', :texto, '%') " +
                "OR equipo LIKE CONCAT('%', :texto, '%') " +
                "OR responsable LIKE CONCAT('%', :texto, '%')",
        nativeQuery = true
    )
    List<Maquinaria> busquedaGlobal(@Param("texto") String texto);

}
