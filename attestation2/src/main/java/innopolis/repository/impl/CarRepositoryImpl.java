package innopolis.repository.impl;

import innopolis.entity.Car;
import innopolis.mappers.CarMapper;
import innopolis.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.NoSuchElementException;

import static innopolis.config.DataBaseConfig.getPostgresqlDataSource;
import static java.lang.String.format;

@Slf4j
public class CarRepositoryImpl implements CarRepository {
    private final CarMapper carMapper = new CarMapper();
    private static final JdbcTemplate jdbcTemplate = new JdbcTemplate(getPostgresqlDataSource());
    private static final String FIND_ALL_CARS = "select * from \"tire\".cars ";
    private static final String FIND_CAR_BY_ID = "select * from \"tire\".cars c where c.id=?";
    private static final String FIND_CAR_BY_NUMBER = "select * from \"tire\".cars c where c.car_number=?";
    private static final String INSERT_CAR_AND_RETURNING_ID = "INSERT INTO \"tire\".cars (client_id, car_name, car_model, car_number) VALUES (?,?,?,?) RETURNING id";
    private static final String DELETE_CAR = "DELETE FROM \"tire\".cars c where c.id=?";
    private static final String UPDATE_CAR = "UPDATE \"tire\".cars c SET car_number=?, client_id=?, car_name=?, car_model=?  WHERE c.id=?";
    private static final String DELETE_ALL = """
            with tempTable as (delete from tire.clients_x_cars cxc where cxc.car_id in (select id from tire.cars) returning cxc.car_id as id),
            	 tempOrder as (delete from tire.orders o where o.car_id in (select id from tempTable))
            delete from tire.cars c where c.id in (select id from tempTable);
            """;
    private static final String DELETE_TEST_DATA = "delete from tire.cars c where c.car_name ='test'";


    @Override
    public List<Car> findAll() {
        return jdbcTemplate.query(FIND_ALL_CARS, carMapper);
    }

    @Override
    public Car findById(Integer id) {
        return jdbcTemplate.query(FIND_CAR_BY_ID, carMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(format("%s с заданным идентификатором %d не существует", Car.class.getSimpleName(), id)));
    }

    @Override
    public Car findByNumber(String number) {
        return jdbcTemplate.query(FIND_CAR_BY_NUMBER, carMapper, number)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(format("%s с заданным идентификатором %s не существует", Car.class.getSimpleName(), number)));
    }

    @Override
    public Integer create(Car car) {
        return jdbcTemplate.query(INSERT_CAR_AND_RETURNING_ID,
                (resultSet, row) -> resultSet.getInt(1),
                car.getClientId(), car.getName(), car.getModel(), car.getNumber()).getFirst();
    }

    @Override
    public void deleteById(Integer id) {
        if (jdbcTemplate.update(DELETE_CAR, id) == 0)
            throw new NoSuchElementException(format("%s с заданным идентификатором %d не существует", Car.class.getSimpleName(), id));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public Integer update(Integer id, Car car) {
        try {
            var updatedRows = jdbcTemplate.update(UPDATE_CAR, car.getNumber(), car.getClientId(), car.getName(), car.getModel(), id);
            if (updatedRows == 0) {
                var newCarId = create(car);
                log.warn("{} объект не найден. Создан новый объект по переданному объекту id {}", Car.class.getSimpleName(), newCarId);
                return newCarId;
            }
            return updatedRows;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void clearCarWithTestName(){
        jdbcTemplate.execute(DELETE_TEST_DATA);
    }


}
