package innopolis.repository.impl;

import innopolis.db.DatabaseService;
import innopolis.entity.Car;
import innopolis.mappers.CarMapper;
import innopolis.repository.CarRepository;

import java.util.List;

public class CarRepositoryImpl implements CarRepository {
    private final CarMapper carMapper = new CarMapper();
    private final DatabaseService db = new DatabaseService();
    private static final String FIND_ALL_CARS = "select * from \"tireService\".cars ";
    private static final String FIND_CAR_BY_ID = "select * from \"tireService\".cars c where c.id=?";
    private static final String FIND_CAR_BY_NUMBER = "select * from \"tireService\".cars c where c.car_number=?";
    private static final String ADD_NEW_CAR = "INSERT INTO \"tireService\".cars (client_id, car_name, car_model, car_number) VALUES (?,?,?,?)";
    private static final String DELETE_CAR = "DELETE FROM \"tireService\".cars c where c.id=?";
    private static final String SET_CAR_NUMBER = "UPDATE \"tireService\".cars c SET car_number=? WHERE c.id=?";

    @Override
    public List<Car> findAllCars() {
        return db.template.query(FIND_ALL_CARS, carMapper);
    }

    @Override
    public Car findCarById(Integer id) {
        return db.template.query(FIND_CAR_BY_ID, carMapper, id).stream().findFirst().orElse(null);
    }

    @Override
    public Car findCarByNumber(String number) {
        return db.template.query(FIND_CAR_BY_NUMBER, carMapper, number).stream().findFirst().orElse(null);
    }

    @Override
    public void addNewCar(Car car) {
        db.template.update(ADD_NEW_CAR, car.getClientId(), car.getName(), car.getModel(), car.getNumber());
    }

    @Override
    public void deleteCarById(Integer id) {
        db.template.update(DELETE_CAR, id);
    }

    @Override
    public void updateCarNumber(Integer id, String newNumber) {
        db.template.update(SET_CAR_NUMBER, newNumber, id);
    }


}
