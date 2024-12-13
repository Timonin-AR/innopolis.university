package innopolis.db;

import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseService extends DataBaseConfig {

    public final JdbcTemplate template;

    public

    DatabaseService() {
        this.template = new JdbcTemplate(getPostgresqlDataSource());
    }
}
