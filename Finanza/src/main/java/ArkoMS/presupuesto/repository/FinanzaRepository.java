package ArkoMS.presupuesto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ArkoMS.presupuesto.model.Finanza;

@Repository
public interface FinanzaRepository extends JpaRepository<Finanza, Long> {

}
