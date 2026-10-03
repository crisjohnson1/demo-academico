package co.edu.demoacademico.config;

import co.edu.demoacademico.model.Estudiante;
import co.edu.demoacademico.repository.EstudianteRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class DataSeeder {

    @Value("${app.seed.enabled:true}")
    private boolean enabled;

    @Value("${app.seed.cantidad:100}")
    private int cantidad;

    @Bean
    CommandLineRunner seedEstudiantes(EstudianteRepository repo) {
        return args -> {
            if (!enabled) return;
            if (repo.count() > 0) return;

            Faker faker = new Faker(new Locale("es"));
            int creados = 0;

            while (creados < cantidad) {
                String nombre = faker.name().fullName();
                String email = ("est" + creados + "_" + faker.internet().emailAddress())
                        .toLowerCase()
                        .replace(" ", "")
                        .replace("..", ".");

                Estudiante e = new Estudiante();
                e.setNombre(nombre);
                e.setEmail(email);

                try {
                    repo.save(e);
                    creados++;
                } catch (Exception ex) {
                    // si el email es único y falla, reintenta
                }
            }
        };
    }
}
