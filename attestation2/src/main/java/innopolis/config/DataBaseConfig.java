package innopolis.config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


public class DataBaseConfig {
    private static final String URL = "jdbc:postgresql://172.18.0.2:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "";
    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";

    public static DataSource getPostgresqlDataSource() {
        var dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        dataSource.setDriverClassName(POSTGRESQL_DRIVER);
        return dataSource;
    }
}

