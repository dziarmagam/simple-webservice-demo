package demo.service.base;

import demo.entity.Car;
import demo.exception.AddingCarFailedException;
import demo.exception.CarDeleteFailedException;
import demo.exception.CarNotFoundException;
import demo.exception.CarUpdateFailedException;

import java.util.UUID;

/**
 * Service for {@link Car}.
 */
public interface CarService {

    /**
     * Remove {@link Car} entity from system.
     * @param uuid {@link UUID} for {@link Car} which will be removed
     * @throws CarDeleteFailedException if car was not deleted
     */
    void removeCar(UUID uuid) throws CarDeleteFailedException;

    /**
     * Update {@link Car} entity.<br>
     * Car has to have valid UUID. In other word, given car has already exist in the application.
     * @param car {@link Car} with UUID
     * @throws CarUpdateFailedException if car was not updated
     */
    void updateCar(Car car) throws CarUpdateFailedException;

    /**
     * Add car to system.
     * @param vendor
     * @param carType {@link Car.CarType}
     * @param color
     * @return Car with uuid
     * @throws AddingCarFailedException if adding car failed
     */
    Car addCar(String vendor, Car.CarType carType, String color) throws AddingCarFailedException;

    /**
     * Finds car by {@link UUID}.
     * @param uuid
     * @return Car if found
     * @throws CarNotFoundException if car was not found
     */
    Car findByUUID(UUID uuid) throws CarNotFoundException;

}
