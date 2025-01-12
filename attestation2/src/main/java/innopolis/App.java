package innopolis;

import innopolis.config.DataBaseConfig;
import innopolis.repository.impl.CarRepositoryImpl;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Hello world!
 */
public class App {

    protected static final CarRepositoryImpl carRepository = new CarRepositoryImpl();
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        var jdbcTemplate = new JdbcTemplate(DataBaseConfig.getPostgresqlDataSource());
        jdbcTemplate.execute(new String(Files.readAllBytes(Path.of("src/main/resources/schema.sql"))));
        jdbcTemplate.execute(new String(Files.readAllBytes(Path.of("src/main/resources/data.sql"))));

        System.out.println(carRepository.findAll());
    }
}
