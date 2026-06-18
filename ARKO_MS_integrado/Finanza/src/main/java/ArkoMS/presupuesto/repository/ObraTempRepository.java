package ArkoMS.presupuesto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ArkoMS.presupuesto.model.ObraTemp;

@Repository
public interface ObraTempRepository extends JpaRepository<ObraTemp, Long> {
    
}
