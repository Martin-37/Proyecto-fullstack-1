package arko.comentarios.repository;

import arko.comentarios.model.ComentarioTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioTicket, Long> {

    List<ComentarioTicket> findByTicketId(Long ticketId);

    List<ComentarioTicket> findByAutorRun(Long autorRun);

    Long countByTicketId(Long ticketId);
}
