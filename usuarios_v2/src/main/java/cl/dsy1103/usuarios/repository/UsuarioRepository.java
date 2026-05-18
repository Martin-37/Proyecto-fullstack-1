package cl.dsy1103.usuarios.repository;

import cl.dsy1103.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    List<Usuario> findByNombreUsuarioContainingIgnoreCase(String nombreUsuario);

    List<Usuario> findByEstado(String estado);

    @Query("SELECT u FROM Usuario u WHERE u.rol.id = :rolId")
    List<Usuario> findByRolId(@Param("rolId") Long rolId);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(@Param("email") String email);

    @Query(
        value = "SELECT * FROM usuarios WHERE nombre_usuario LIKE CONCAT('%', :texto, '%') OR email LIKE CONCAT('%', :texto, '%')",
        nativeQuery = true
    )
    List<Usuario> busquedaGlobal(@Param("texto") String texto);
}
