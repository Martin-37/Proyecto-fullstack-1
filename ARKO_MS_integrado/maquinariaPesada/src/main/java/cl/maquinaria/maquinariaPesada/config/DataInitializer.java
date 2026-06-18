package cl.maquinaria.maquinariaPesada.config;

import cl.maquinaria.maquinariaPesada.model.Maquinaria;
import cl.maquinaria.maquinariaPesada.repository.MaquinariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MaquinariaRepository maquinariaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (maquinariaRepository.count() == 0) {

            maquinariaRepository.save(new Maquinaria(null,
                    "Excavadora CAT 320", "Excavadora hidráulica",
                    "Operativo", "Sector A - Fundaciones",
                    "Luis Herrera", LocalDate.of(2025, 8, 15)));

            maquinariaRepository.save(new Maquinaria(null,
                    "Grúa Torre Liebherr 120K", "Grúa torre",
                    "Operativo", "Sector B - Torre principal",
                    "Camila Rojas", LocalDate.of(2025, 7, 30)));

            maquinariaRepository.save(new Maquinaria(null,
                    "Compactadora Bomag BW211", "Compactadora de suelos",
                    "En mantenimiento", "Taller central",
                    "Andrés Muñoz", LocalDate.of(2025, 6, 10)));

            maquinariaRepository.save(new Maquinaria(null,
                    "Retroexcavadora JCB 3CX", "Retroexcavadora mixta",
                    "Operativo", "Sector C - Zanjas",
                    "Patricia Soto", LocalDate.of(2025, 9, 5)));

            maquinariaRepository.save(new Maquinaria(null,
                    "Volquete Komatsu HD325", "Camión volquete",
                    "Fuera de servicio", "Patio de maquinaria",
                    "Roberto Fuentes", LocalDate.of(2025, 5, 20)));

            System.out.println("✅ Datos iniciales de Maquinaria Pesada cargados correctamente");
        }
    }
}
