package ArkoMS.presupuesto.config;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ArkoMS.presupuesto.model.Finanza;
import ArkoMS.presupuesto.model.ObraTemp;
import ArkoMS.presupuesto.repository.FinanzaRepository;
import ArkoMS.presupuesto.repository.ObraTempRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * DataInitializer.java
 *
 * Carga datos de prueba al arrancar la aplicación.
 * - Moneda utilizada: CLP (Peso Chileno). Los montos son enteros (sin decimales).
 * - Útil para desarrollo (ddl-auto = create-drop / update).
 * - En producción se elimina o se desactiva con un perfil de Spring:
 *   @Profile("!prod")
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ObraTempRepository obraRepository;
    private final FinanzaRepository  finanzaRepository;

    // ─────────────────────────────────────────────
    //  Punto de entrada
    // ─────────────────────────────────────────────
    @Override
    public void run(String... args) {
        cargarObras();
        cargarFinanzas();
    }

    // ─────────────────────────────────────────────
    //  Obras temporales (catálogo de proyectos)
    // ─────────────────────────────────────────────
    private void cargarObras() {
        if (obraRepository.count() > 0) {
            System.out.println(">>> DataInitializer [ObraTemp]: datos ya existen, omitiendo carga.");
            return;
        }

        List<ObraTemp> obras = List.of(
            new ObraTemp("Edificio Central Valparaíso",
                         "Av. Brasil 1234, Valparaíso – torre de 12 pisos, uso mixto oficinas/comercio"),
            new ObraTemp("Puente Los Álamos",
                         "Ruta 68 Km 45, Región Metropolitana – reemplazo de estructura metálica"),
            new ObraTemp("Hospital Regional Concepción",
                         "Calle O'Higgins 567, Concepción – ampliación pabellón de urgencias"),
            new ObraTemp("Centro Comercial Plaza Norte",
                         "Av. Américo Vespucio 789, Santiago – habilitación de locales comerciales piso 2"),
            new ObraTemp("Remodelación Mercado Municipal Temuco",
                         "Rodolfo Valenzuela 350, Temuco – restauración fachada y cubierta patrimonial"),
            new ObraTemp("Pavimentación Av. Los Carrera Rancagua",
                         "Tramo Av. Los Carrera 0-2000, Rancagua – 2,1 km de carpeta asfáltica"),
            new ObraTemp("Planta Tratamiento Aguas Antofagasta",
                         "Sector Industrial La Negra, Antofagasta – módulo secundario de 8 000 m³/día")
        );

        obraRepository.saveAll(obras);
        System.out.println(">>> DataInitializer [ObraTemp]: " + obras.size() + " obras cargadas.");
    }

    // ─────────────────────────────────────────────
    //  Registros financieros (presupuesto)
    //  Montos en CLP – enteros, sin decimales.
    // ─────────────────────────────────────────────
    private void cargarFinanzas() {
        if (finanzaRepository.count() > 0) {
            System.out.println(">>> DataInitializer [Finanza]: datos ya existen, omitiendo carga.");
            return;
        }

        // Helper para construir fechas limpias
        // Año 2025, meses 1-indexados
        List<Finanza> finanzas = List.of(

            // ── Enero 2025 ──────────────────────────────────────
            new Finanza(null,
                        3_500_000,   // gastos CLP
                        12_000_000,  // ingreso CLP
                        fecha(2025, 1, 5),
                        "Pago proveedores materiales – Edificio Central Valparaíso"),

            new Finanza(null,
                        1_200_000,
                        0,
                        fecha(2025, 1, 15),
                        "Arriendo maquinaria – Puente Los Álamos"),

            new Finanza(null,
                        0,
                        8_500_000,
                        fecha(2025, 1, 20),
                        "Anticipo cliente – Hospital Regional Concepción (30%)"),

            // ── Febrero 2025 ────────────────────────────────────
            new Finanza(null,
                        4_750_000,
                        0,
                        fecha(2025, 2, 3),
                        "Subcontrato instalaciones eléctricas – Plaza Norte"),

            new Finanza(null,
                        890_000,
                        0,
                        fecha(2025, 2, 14),
                        "Compra EPP y señalética seguridad – varios proyectos"),

            new Finanza(null,
                        0,
                        15_000_000,
                        fecha(2025, 2, 28),
                        "Estado de pago N°1 – Edificio Central Valparaíso"),

            // ── Marzo 2025 ──────────────────────────────────────
            new Finanza(null,
                        2_300_000,
                        0,
                        fecha(2025, 3, 7),
                        "Hormigón premezclado H30 – Puente Los Álamos"),

            new Finanza(null,
                        680_000,
                        0,
                        fecha(2025, 3, 12),
                        "Combustible maquinaria pesada – marzo"),

            new Finanza(null,
                        0,
                        6_200_000,
                        fecha(2025, 3, 25),
                        "Estado de pago N°1 – Pavimentación Av. Los Carrera"),

            // ── Abril 2025 ──────────────────────────────────────
            new Finanza(null,
                        5_100_000,
                        0,
                        fecha(2025, 4, 2),
                        "Remuneraciones cuadrilla – Mercado Municipal Temuco"),

            new Finanza(null,
                        320_000,
                        0,
                        fecha(2025, 4, 10),
                        "Inspección técnica y ensayos laboratorio"),

            new Finanza(null,
                        0,
                        22_500_000,
                        fecha(2025, 4, 30),
                        "Estado de pago N°2 – Edificio Central Valparaíso"),

            // ── Mayo 2025 ────────────────────────────────────────
            new Finanza(null,
                        9_800_000,
                        0,
                        fecha(2025, 5, 8),
                        "Estructura metálica – Planta Tratamiento Antofagasta"),

            new Finanza(null,
                        1_450_000,
                        0,
                        fecha(2025, 5, 19),
                        "Transporte materiales región Bío-Bío"),

            new Finanza(null,
                        0,
                        11_000_000,
                        fecha(2025, 5, 31),
                        "Estado de pago N°2 – Hospital Regional Concepción"),

            // ── Junio 2025 ──────────────────────────────────────
            new Finanza(null,
                        3_200_000,
                        18_700_000,
                        fecha(2025, 6, 15),
                        "Liquidación mensual junio – gastos operacionales y estado de pago N°3"),

            // ── Julio 2025 ──────────────────────────────────────
            new Finanza(null,
                        7_600_000,
                        0,
                        fecha(2025, 7, 4),
                        "Carpeta asfáltica MC-30 – Pavimentación Av. Los Carrera"),

            new Finanza(null,
                        0,
                        9_300_000,
                        fecha(2025, 7, 22),
                        "Anticipo N°2 – Planta Tratamiento Antofagasta"),

            // ── Agosto 2025 ─────────────────────────────────────
            new Finanza(null,
                        4_050_000,
                        25_000_000,
                        fecha(2025, 8, 31),
                        "Cierre periodo agosto – gastos generales y estado de pago final Puente Los Álamos")
        );

        finanzaRepository.saveAll(finanzas);
        System.out.println(">>> DataInitializer [Finanza]: " + finanzas.size() + " registros cargados.");
    }

    // ─────────────────────────────────────────────
    //  Utilidad: construye un LocalDate (sin hora ni zona horaria,
    //  evitando el bug de desfase de dia que ocurria con
    //  Calendar/Date al depender de la zona horaria de la JVM).
    //  mes es 1-indexado (enero = 1)
    // ─────────────────────────────────────────────
    private LocalDate fecha(int anio, int mes, int dia) {
        return LocalDate.of(anio, mes, dia);
    }
}