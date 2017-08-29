package demo.dao;

import demo.dao.base.CarDao;
import demo.entity.Car;
import demo.exception.AddingCarFailedException;
import demo.exception.CarDeleteFailedException;
import demo.exception.CarNotFoundException;
import demo.exception.CarUpdateFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

import static demo.util.FieldUtils.getValueOrNull;

@Repository
public class JdbcCarDao implements CarDao {

    private DataSource dataSource;

    private static final String DELETE_QUERY = "DELETE CAR WHERE UUID = ? ";
    private static final String UPDATE_QUERY = "UPDATE CAR SET VENDOR=?, COLOR=?, CAR_TYPE=? WHERE UUID = ? ";
    private static final String INSERT_QUERY = "INSERT INTO CAR VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_UUID_QUERY = "SELECT VENDOR, COLOR, CAR_TYPE FROM CAR WHERE UUID = ?";

    @Autowired
    public JdbcCarDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void removeCar(UUID uuid) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setString(1, uuid.toString());
            if (preparedStatement.executeUpdate() != 1) {
                throw new CarDeleteFailedException(uuid);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void updateCar(Car car) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, car.vendor);
            preparedStatement.setString(2, car.color);
            preparedStatement.setString(3, car.type.name());
            preparedStatement.setString(4, car.uuid.toString());
            if (preparedStatement.executeUpdate() != 1) {
                throw new CarUpdateFailedException(car.uuid);
            }
        } catch (SQLException e) {
            throw new CarUpdateFailedException(e, car.uuid);
        }
    }

    @Override
    public Car addCar(String vendor, Car.CarType carType, String color) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            UUID uuid = UUID.randomUUID();
            preparedStatement.setString(1, uuid.toString());
            preparedStatement.setString(2, vendor);
            preparedStatement.setString(3, color);
            preparedStatement.setString(4, getValueOrNull(carType, Enum::name));
            if (preparedStatement.executeUpdate() != 1) {
                throw new AddingCarFailedException();
            }
            return new Car(uuid, vendor, color, carType);
        } catch (SQLException e) {
            throw new AddingCarFailedException(e);
        }

    }

    @Override
    public Car findByUUID(UUID uuid) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_UUID_QUERY)) {
            preparedStatement.setString(1, uuid.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String vendor = resultSet.getString(1);
                    String color = resultSet.getString(2);
                    String carType = resultSet.getString(3);
                    return new Car(uuid, vendor, color, getValueOrNull(carType, Car.CarType::valueOf));
                }
                throw new CarNotFoundException(uuid);
            }
        } catch (SQLException e) {
            throw new CarNotFoundException(e, uuid);
        }
    }

}
