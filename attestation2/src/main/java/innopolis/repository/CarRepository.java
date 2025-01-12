package innopolis.repository;

import innopolis.entity.Car;

import java.util.List;

public interface CarRepository {


    Integer create(Car car);
    Car findById(Integer id);
    Car findByNumber(String number);
    List<Car> findAll();
    Integer update(Integer id, Car car);
    void deleteById(Integer id);
    void deleteAll();



}
