package cl.dsy1103.usuarios.repository;

import cl.dsy1103.usuarios.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    List<Rol> findByEstado(String estado);

    Optional<Rol> findByNombreRolIgnoreCase(String nombreRol);

    @Query("SELECT r FROM Rol r WHERE UPPER(r.descripcion) LIKE UPPER(CONCAT('%', :texto, '%'))")
    List<Rol> buscarPorDescripcion(@Param("texto") String texto);

    @Query(
        value = "SELECT * FROM roles WHERE nombre_rol LIKE CONCAT('%', :texto, '%') OR descripcion LIKE CONCAT('%', :texto, '%')",
        nativeQuery = true
    )
    List<Rol> busquedaGlobal(@Param("texto") String texto);
}
