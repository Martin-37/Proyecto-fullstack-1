package arko.cl.documentos.repository;

import arko.cl.documentos.model.Documento;
import arko.cl.documentos.model.Documento.EstadoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findByObraAsociada(Long obraAsociada);

    List<Documento> findByTipo(String tipo);

    List<Documento> findByEstado(EstadoDocumento estado);

    @Query("SELECT d FROM Documento d WHERE LOWER(d.nombrePlano) LIKE LOWER(CONCAT('%', :nombrePlano, '%'))")
    List<Documento> findByNombrePlanoContains(@Param("nombrePlano") String nombrePlano);

    long countByObraAsociada(Long obraAsociada);

    long countByEstado(EstadoDocumento estado);
}
