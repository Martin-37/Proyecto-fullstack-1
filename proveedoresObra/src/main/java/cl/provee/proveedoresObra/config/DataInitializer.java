package cl.provee.proveedoresObra.config;

import cl.provee.proveedoresObra.model.Proveedor;
import cl.provee.proveedoresObra.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProveedorRepository proveedorRepository;

    @Override
    public void run(String... args) throws Exception {
        if (proveedorRepository.count() == 0) {
            // proveedores de ejemplo pa testear
            proveedorRepository.save(new Proveedor(null, 
                "Constructora ABC", "Juan Pérez", "+56912345678", 
                "Av. Principal 123, Santiago", "Activo"));
            
            proveedorRepository.save(new Proveedor(null, 
                "Materiales López", "María López", "+56987654321", 
                "Calle Comercio 456, Valparaíso", "Activo"));
            
            proveedorRepository.save(new Proveedor(null, 
                "Ferreterías del Sur", "Carlos Gómez", "+56923456789", 
                "Ruta 5 Sur Km 120, Concepción", "Pendiente"));
            
            proveedorRepository.save(new Proveedor(null, 
                "Maderas y Tableros", "Ana Torres", "+56945678901", 
                "Parque Industrial 789, Temuco", "Activo"));
            
            proveedorRepository.save(new Proveedor(null, 
                "Proveedora Central", "Pedro Ramírez", "+56956789012", 
                "Los Aromos 321, Santiago", "Inactivo"));

            System.out.println("✅ Datos iniciales de Proveedores cargados correctamente");
        }
    }
}
