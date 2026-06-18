package ArkoMS.presupuesto.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ArkoMS.presupuesto.model.Finanza;

@Repository
public interface FinanzaRepository extends JpaRepository<Finanza, Long> {

	// Buscar finanzas en una fecha concreta
	@Query("SELECT f FROM Finanza f WHERE f.fecha = :fecha")
	List<Finanza> findByFecha(LocalDate fecha);

	// Buscar finanzas entre dos fechas (inclusive)
	@Query("SELECT f FROM Finanza f WHERE f.fecha BETWEEN :inicio AND :fin")
	List<Finanza> findByFechaBetween(LocalDate inicio, LocalDate fin);

}
