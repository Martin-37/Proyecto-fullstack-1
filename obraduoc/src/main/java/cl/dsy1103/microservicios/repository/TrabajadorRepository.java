package cl.dsy1103.microservicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.dsy1103.microservicios.model.Trabajador;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
    // JpaRepository ya incluye findById(Long), findAll(), save(), deleteById()
}
