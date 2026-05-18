package cl.dsy1103.usuarios.config;

import cl.dsy1103.usuarios.model.Rol;
import cl.dsy1103.usuarios.model.Usuario;
import cl.dsy1103.usuarios.repository.RolRepository;
import cl.dsy1103.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (rolRepository.count() == 0) {

            Rol admin = rolRepository.save(new Rol(null,
                    "ADMIN",
                    "Acceso total al sistema",
                    "Activo"));

            Rol supervisor = rolRepository.save(new Rol(null,
                    "SUPERVISOR",
                    "Supervision de obras y trabajadores",
                    "Activo"));

            Rol operario = rolRepository.save(new Rol(null,
                    "OPERARIO",
                    "Ejecucion de tareas en terreno",
                    "Activo"));

            Rol auditor = rolRepository.save(new Rol(null,
                    "AUDITOR",
                    "Revision y control de registros",
                    "Activo"));

            usuarioRepository.save(new Usuario(null,
                    "jperez", admin,
                    "juan.perez@empresa.cl", passwordEncoder.encode("admin1234"), "Activo"));

            usuarioRepository.save(new Usuario(null,
                    "mlopez", supervisor,
                    "maria.lopez@empresa.cl", passwordEncoder.encode("super5678"), "Activo"));

            usuarioRepository.save(new Usuario(null,
                    "cgomez", operario,
                    "carlos.gomez@empresa.cl", passwordEncoder.encode("oper9012"), "Activo"));

            usuarioRepository.save(new Usuario(null,
                    "atorres", supervisor,
                    "ana.torres@empresa.cl", passwordEncoder.encode("super3456"), "Inactivo"));

            usuarioRepository.save(new Usuario(null,
                    "pramirez", auditor,
                    "pedro.ramirez@empresa.cl", passwordEncoder.encode("audi7890"), "Activo"));

            System.out.println(">>> DataInitializer: roles y usuarios de prueba cargados correctamente.");
        }
    }
}
