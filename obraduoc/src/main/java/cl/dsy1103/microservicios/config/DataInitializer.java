package cl.dsy1103.microservicios.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.dsy1103.microservicios.model.Obra;
import cl.dsy1103.microservicios.model.Trabajador;
import cl.dsy1103.microservicios.repository.ObraRepository;
import cl.dsy1103.microservicios.repository.TrabajadorRepository;

import java.time.LocalDate;

/**
 * DataInitializer.java
 * Carga datos de prueba al arrancar la aplicacion.
 * Util para desarrollo en clase (ddl-auto=create-drop).
 * En produccion se elimina o se desactiva.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TrabajadorRepository trabajadorRepository;
    private final ObraRepository obraRepository;

    @Override
    public void run(String... args) {

        // ── TRABAJADORES ──────────────────────────────────────────────────
        Trabajador t1 = new Trabajador(
                12345678L, "K",
                "Juan", "Antonio",
                "Perez", "Gonzalez",
                "Jefe de Obra",
                912345678L, "juan.perez@empresa.cl"
        );
        Trabajador t2 = new Trabajador(
                98765432L, "5",
                "Maria", null,
                "Lopez", "Rojas",
                "Supervisora",
                987654321L, "maria.lopez@empresa.cl"
        );
        trabajadorRepository.save(t1);
        trabajadorRepository.save(t2);

        // ── OBRAS ─────────────────────────────────────────────────────────
        obraRepository.save(new Obra(
                "Edificio Central Valparaiso",
                "Av. Brasil 1234, Valparaiso",
                t1,
                LocalDate.of(2025, 3, 15),
                "En ejecucion"
        ));
        obraRepository.save(new Obra(
                "Puente Los Alamos",
                "Ruta 68 Km 45, Region Metropolitana",
                t2,
                LocalDate.of(2025, 6, 1),
                "Planificacion"
        ));
        obraRepository.save(new Obra(
                "Remodelacion Estadio Municipal",
                "Calle Independencia 500, Quilpue",
                t1,
                LocalDate.of(2024, 11, 10),
                "Finalizada"
        ));

        System.out.println(">>> DataInitializer: datos de prueba cargados correctamente.");
    }
}
