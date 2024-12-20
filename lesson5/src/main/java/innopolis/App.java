package innopolis;

import innopolis.entity.Car;
import innopolis.entity.Client;
import innopolis.entity.Order;
import innopolis.repository.impl.CarRepositoryImpl;
import innopolis.repository.impl.ClientRepositoryImpl;
import innopolis.repository.impl.OrderRepositoryImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Hello world!
 */
public class App {
    public static CarRepositoryImpl carRepository = new CarRepositoryImpl();
    public static ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
    public static OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();

    public static void main(String[] args) {
        System.out.println("Запросы к таблице cars");
        System.out.println(carRepository.findAllCars());
        System.out.println(carRepository.findCarById(1));
        System.out.println(carRepository.findCarByNumber("B222BB"));
        carRepository.createCar(new Car(null, 1, "LADA", "Vesta", "A323B26RUS"));
        System.out.println(carRepository.findCarByNumber("A323B26RUS"));
        //carRepository.deleteCarById(3);
        carRepository.updateCarNumber(1, "A007B26RUS");
        System.out.println(carRepository.findCarById(1));
        System.out.println(carRepository.findAllCars());

        System.out.println();
        System.out.println("Запросы к таблице clients");
        System.out.println(clientRepository.findAllClients());
        clientRepository.createClient(new Client(null, 82934234, "Тестов", "Тесторович", "Тест", Timestamp.valueOf(LocalDateTime.now())));
        System.out.println(clientRepository.findAllClients());
        clientRepository.updateClientPhoneNumber(1, 77777777);
        System.out.println(clientRepository.findAllClients());
        clientRepository.deleteClientById(3);
        System.out.println(clientRepository.findAllClients());

        System.out.println();
        System.out.println("Запросы к таблице orders");
        System.out.println(orderRepository.findAllOrder());
        orderRepository.createOrder(new Order(null, 4, 1,3000));
        System.out.println(orderRepository.findAllOrder());
        orderRepository.deleteOrderById(2);
        orderRepository.updateCost(1, 99999);
        System.out.println(orderRepository.findAllOrder());

    }
}
