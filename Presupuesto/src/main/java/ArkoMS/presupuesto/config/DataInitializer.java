package ArkoMS.presupuesto.config;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ArkoMS.presupuesto.model.ObraTemp;
import ArkoMS.presupuesto.repository.ObraTempRepository;





/**
 * DataInitializer.java
 * Carga datos de prueba al arrancar la aplicacion.
 * Util para desarrollo en clase (ddl-auto=create-drop).
 * En produccion se elimina o se desactiva.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ObraTempRepository obraRepository;

    @Override
    public void run(String... args) {
        if (obraRepository.count() == 0) {
            ObraTemp t1 = new ObraTemp("Edificio Central Valparaiso","Av. Brasil 1234, Valparaiso");
          
            ObraTemp t2 = new ObraTemp("Puente Los Alamos","Ruta 68 Km 45, Region Metropolitana");
            ObraTemp t3 = new ObraTemp("Hospital Regional Concepcion","Calle O'Higgins 567, Concepcion");
            ObraTemp t4 = new ObraTemp("Centro Comercial Plaza Norte","Av. Américo Vespucio 789, Santiago");
            
            obraRepository.save(t1);
            obraRepository.save(t2);
            obraRepository.save(t3);
            obraRepository.save(t4);
            System.out.println(">>> DataInitializer: datos de prueba cargados correctamente.");
        } else {
            System.out.println(">>> DataInitializer: datos ya existen, omitiendo carga.");
        }
    }
}
