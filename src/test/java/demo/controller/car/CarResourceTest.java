package demo.controller.car;

import demo.controller.BaseControllerTest;
import demo.entity.Car;
import demo.exception.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.UUID;


public class CarResourceTest extends BaseControllerTest {

    private static final String DELETE_CARS_QUERY = "DELETE FROM CAR";

    @Autowired
    CarResource carResource;

    @Before
    public void init() {
        try (Connection connection = DriverManager.getConnection(databaseUrl, username, password);
             Statement statement = connection.createStatement()) {
            statement.execute(DELETE_CARS_QUERY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void givenCar_whenCarIsInserted_thenResourceReturnCarWithUUID() {
        //given
        CarRepresentation car = new CarRepresentation(null, "vendor", "color",
                Car.CarType.SMALL.name());
        //when
        UUID uuid = carResource.addCar(car);
        CarRepresentation foundCar = carResource.findCar(uuid.toString());
        //then
        car.setUuid(uuid.toString());

        Assertions.assertThat(foundCar)
                .isEqualTo(car);
    }

    @Test(expected = AddingCarFailedException.class)
    public void givenInvalidCar_whenCarIsInserted_thenExceptionIsThrown() {
        //given
        CarRepresentation car = new CarRepresentation(null, "vendor", String.valueOf(new char[256]),
                Car.CarType.SMALL.name());
        //when
        carResource.addCar(car);
        //then
        //AddingCarFailedException is thrown
    }

    @Test
    public void givenCarInDatabase_whenDeletingCar_thenCarIsDeleted() {
        //given
        CarRepresentation car = new CarRepresentation(null, "vendor", "color", Car.CarType.SMALL.name());
        UUID uuid = carResource.addCar(car);
        //when
        ResponseEntity responseEntity = carResource.removeCar(uuid.toString());
        //then
        Assertions.assertThat(responseEntity)
                .has(new Condition<>(response -> response.getStatusCode().equals(HttpStatus.OK),
                        "response status OK(200)"));
    }

    @Test(expected = CarDeleteFailedException.class)
    public void givenCarInDatabase_whenDeletingNotExistingCar_thenCarDeleteExceptionIsThrown() {
        //given
        CarRepresentation car = new CarRepresentation(UUID.randomUUID().toString(),
                "vendor", "color", Car.CarType.SMALL.name());
        //when
        carResource.removeCar(car.getUuid());
        //then
        //CarDeleteFailedException is thrown
    }

    @Test
    public void givenCarInDatabase_whenUpdateRequestIsSend_thenCarIsUpdated() {
        //given
        CarRepresentation car = new CarRepresentation(null, "vendor", "color",
                Car.CarType.SMALL.name());
        UUID uuid = carResource.addCar(car);
        CarRepresentation expected = new CarRepresentation(uuid.toString(), "newVencor", "newColor",
                Car.CarType.MPV.name());
        //when
        ResponseEntity responseEntity = carResource.updateCar(expected);
        CarRepresentation updateCar = carResource.findCar(uuid.toString());
        //then
        Assertions.assertThat(updateCar)
                .isEqualTo(expected);
    }

    @Test(expected = CarUpdateFailedException.class)
    public void givenCarInDatabase_whenUpdateRequestIsSendWithInvalidData_thenCarIsNotUpdated() {
        //given
        CarRepresentation car = new CarRepresentation(null, "vendor", "color",
                Car.CarType.SMALL.name());
        UUID uuid = carResource.addCar(car);
        CarRepresentation expected = new CarRepresentation(uuid.toString(), "newVencor",
                String.valueOf(new char[256]), Car.CarType.MPV.name());
        //when
        carResource.updateCar(expected);
        //then
        //CarUpdateFailedException is thrown
    }

    @Test(expected = CarNotFoundException.class)
    public void givenCarIsNotInDatabase_whenAskingForCar_CarIsNotFound() {
        //given
        UUID uuid = UUID.randomUUID();
        //when
        carResource.findCar(uuid.toString());
        //then
        //CarNotFoundException is thrown
    }

    @Test
    public void givenInvalidUuid_whenCarIsSearched_InvalidUuidExceptionIsThrown() {
        //given
        String invalidUuid = "invalidUuid";
        //when
        try {
            carResource.findCar(invalidUuid);
            Assertions.fail("findCar did not throw InvalidUuidException ");
        } catch (InvalidUuidException e) {
            //then
            Assertions.assertThat(e.uuid.equals(invalidUuid));
        }
    }

}
