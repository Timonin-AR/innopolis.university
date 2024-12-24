package innopolis;

import innopolis.config.DataBaseConfig;
import innopolis.repository.impl.CarRepositoryImpl;
import innopolis.repository.impl.ClientRepositoryImpl;
import innopolis.repository.impl.ClientXCarRepositoryImpl;
import innopolis.repository.impl.OrderRepositoryImpl;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Hello world!
 */
public class App {
    private static final String CARS_JOIN_CLIENTS = """
            select ca.car_name, ca.car_model, ca.car_number,
            cl.first_name, cl.midl_name, cl.last_name
            from "tire".cars ca
            left join "tire".clients cl on cl.id = ca.client_id;""";
    public static CarRepositoryImpl carRepository = new CarRepositoryImpl();
    public static ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
    public static OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();
    public static ClientXCarRepositoryImpl clientXCarRepository = new ClientXCarRepositoryImpl();

    static JdbcTemplate jdbcTemplate = new JdbcTemplate(DataBaseConfig.getPostgresqlDataSource());

    public static void main(String[] args) throws IOException {
        var createTables = new String(Files.readAllBytes(Path.of("attestation1/src/main/resources/schema.sql")));
        var insertData = new String(Files.readAllBytes(Path.of("attestation1/src/main/resources/data.sql")));
        jdbcTemplate.execute(createTables);
        jdbcTemplate.execute(insertData);
        System.out.println("База данных с данными создана");

        System.out.println("\nЗапрос объединения таблиц");
        System.out.println("Марка | Модель | Номер | Имя | Отчество | Фамилия");
        jdbcTemplate.query(CARS_JOIN_CLIENTS, (rs, rowNum) -> {
            System.out.format("%s | %s | %s | %s | %s | %s \n",
                    rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6));
            return null;
        });

        System.out.println("\nЗапрос таблицы cars");
        System.out.println(carRepository.findAllCars());

        System.out.println("\nЗапрос таблицы clients");
        System.out.println(clientRepository.findAllClients());

        System.out.println("\nЗапрос таблицы orders");
        System.out.println(orderRepository.findAllOrder());

        System.out.println("\nЗапрос таблицы clients_x_cars");
        System.out.println(clientXCarRepository.findAll());

    }
}
