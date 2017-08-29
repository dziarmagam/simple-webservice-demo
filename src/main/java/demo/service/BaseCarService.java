package demo.service;

import demo.dao.base.CarDao;
import demo.entity.Car;
import demo.service.base.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class BaseCarService implements CarService {

    private CarDao carDao;

    @Autowired
    public BaseCarService(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public void removeCar(UUID uuid) {
        Objects.requireNonNull(uuid);
        carDao.removeCar(uuid);
    }

    @Override
    public void updateCar(Car car) {
        Objects.requireNonNull(car);
        Objects.requireNonNull(car.uuid);
        carDao.updateCar(car);
    }

    @Override
    public Car addCar(String vendor, Car.CarType carType, String color) {
        return carDao.addCar(vendor, carType, color);
    }

    @Override
    public Car findByUUID(UUID uuid) {
        Objects.requireNonNull(uuid);
        return carDao.findByUUID(uuid);
    }
}
