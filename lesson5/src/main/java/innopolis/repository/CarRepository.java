package innopolis.repository;

import innopolis.entity.Car;

import java.util.List;

public interface CarRepository {

    List<Car> findAllCars();
    Car findCarById(Integer id);
    Car findCarByNumber(String number);
    void createCar(Car car);
    void deleteCarById(Integer id);
    void updateCarNumber(Integer id, String newNumber);
}
