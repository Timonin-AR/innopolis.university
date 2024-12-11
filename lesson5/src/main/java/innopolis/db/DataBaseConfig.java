package innopolis.db;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


public class DataBaseConfig {

    public DataSource getDataSource() {
        return new DriverManagerDataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "Nbv%^1234");
    }
}
