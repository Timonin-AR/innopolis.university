package innopolis.mappers;

import innopolis.entity.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Order.builder()
                .id(rs.getInt("id"))
                .costOfWork(rs.getInt("cost_of_work"))
                .carId(rs.getInt("car_id"))
                .build();
    }
}
