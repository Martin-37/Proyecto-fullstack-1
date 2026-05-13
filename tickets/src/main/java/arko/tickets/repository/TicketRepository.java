package arko.tickets.repository;

import arko.tickets.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByTituloContainingIgnoreCase(String titulo);

    List<Ticket> findByEstado(String estado);

    List<Ticket> findByPrioridad(String prioridad);

    List<Ticket> findByResponsable(String responsable);

    @Query("SELECT t FROM Ticket t WHERE t.descripcion LIKE %:descripcion%")
    List<Ticket> findByDescripcionContaining(@Param("descripcion") String descripcion);

    @Query("SELECT t FROM Ticket t WHERE t.estado = :estado AND t.prioridad = :prioridad")
    List<Ticket> findByEstadoAndPrioridad(@Param("estado") String estado, @Param("prioridad") String prioridad);

    @Query("SELECT t FROM Ticket t WHERE t.responsable = :responsable AND t.estado = :estado")
    List<Ticket> findByResponsableAndEstado(@Param("responsable") String responsable, @Param("estado") String estado);

    Long countByEstado(String estado);

    Long countByPrioridad(String prioridad);
}
