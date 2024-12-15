package innopolis.mappers;

import innopolis.entity.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class ClientMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        var dateOdBirth = rs.getString("date_of_birth");
        return Client.builder()
                .id(rs.getInt("id"))
                .firsName(rs.getString("first_name"))
                .midlName(rs.getString("midl_name"))
                .lastName(rs.getString("last_name"))
                .dateOfBirth(dateOdBirth.isEmpty() ? null : Timestamp.valueOf(dateOdBirth))
                .phoneNumber(rs.getInt("phone_number"))
                .build();

    }
}
