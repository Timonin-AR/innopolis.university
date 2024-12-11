package innopolis.db;

import innopolis.models.Client;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DatabaseService extends DataBaseConfig {

    DatabaseService database = new DatabaseService();

    public List<Client> getAllClients() {
        return null;
    }

    public DatabaseService() {
        var jdbcTemplate = new JdbcTemplate(getDataSource());
    }
}
