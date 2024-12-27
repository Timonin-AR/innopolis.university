package innopolis.mappers;



import innopolis.entity.ClientXCar;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientXCarMapper implements RowMapper<ClientXCar> {
    @Override
    public ClientXCar mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ClientXCar.builder()
                .id(rs.getInt("id"))
                .clientId(rs.getInt("client_id"))
                .carId(rs.getInt("car_id"))
                .build();
    }
}
