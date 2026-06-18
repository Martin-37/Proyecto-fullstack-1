package cl.provee.proveedoresObra.repository;

import cl.provee.proveedoresObra.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {


    List<Proveedor> findByNombreContainingIgnoreCase(String nombre);


    List<Proveedor> findByEstado(String estado);

    List<Proveedor> findByContactoContainingIgnoreCase(String contacto);

    @Query("SELECT p FROM Proveedor p WHERE p.telefono = :telefono")
    List<Proveedor> findByTelefono(@Param("telefono") String telefono);

    @Query("SELECT p FROM Proveedor p WHERE UPPER(p.direccion) LIKE UPPER(CONCAT('%', :direccion, '%'))")
    List<Proveedor> buscarPorDireccion(@Param("direccion") String direccion);

    @Query(
            value = "SELECT * FROM proveedores WHERE nombre LIKE CONCAT('%', :texto, '%') OR contacto LIKE CONCAT('%', :texto, '%')",
            nativeQuery = true
    )
    List<Proveedor> busquedaGlobal(@Param("texto") String texto);

}
