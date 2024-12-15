package innopolis.db;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


public class DataBaseConfig {
    //todo Вынести данные в проперти
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Nbv%^1234";
    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";

    public DataSource getPostgresqlDataSource() {
        var dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        dataSource.setDriverClassName(POSTGRESQL_DRIVER);
        return dataSource;
    }
}
