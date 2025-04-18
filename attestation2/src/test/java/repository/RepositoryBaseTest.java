package repository;

import innopolis.config.DataBaseConfig;
import innopolis.impl.CarRepositoryImpl;
import innopolis.impl.ClientRepositoryImpl;
import innopolis.impl.ClientXCarRepositoryImpl;
import innopolis.impl.OrderRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Execution(ExecutionMode.SAME_THREAD)
public class RepositoryBaseTest {
    protected final CarRepositoryImpl carRepository = new CarRepositoryImpl();
    protected final ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
    protected final OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();
    protected final ClientXCarRepositoryImpl clientXCarRepository = new ClientXCarRepositoryImpl();

    @BeforeAll
    static void initDB() throws IOException {
        var jdbcTemplate = new JdbcTemplate(DataBaseConfig.getPostgresqlDataSource());
        jdbcTemplate.execute(new String(Files.readAllBytes(Path.of("src/main/resources/schema.sql"))));
        jdbcTemplate.execute(new String(Files.readAllBytes(Path.of("src/main/resources/data.sql"))));
    }
}
