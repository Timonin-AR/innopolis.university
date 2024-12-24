package innopolis.mappers;


import innopolis.entity.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Car.builder()
                .id(rs.getInt("id"))
                .clientId(rs.getInt("client_id"))
                .name(rs.getString("car_name"))
                .model(rs.getString("car_model"))
                .number(rs.getString("car_number"))
                .build();
    }
}
