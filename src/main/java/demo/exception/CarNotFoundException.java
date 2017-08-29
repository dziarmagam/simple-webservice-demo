package demo.exception;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Throws when application fails to found {@link demo.entity.Car} entity with given {@link UUID}.
 */
public class CarNotFoundException extends RuntimeException {

    public final UUID uuid;

    private final static String MESSAGE_PATTERN = "Car with uuid %s can not be found";

    public CarNotFoundException(UUID uuid) {
        super(String.format(MESSAGE_PATTERN, uuid));
        this.uuid = uuid;
    }


    public CarNotFoundException(Throwable e, UUID uuid) {
        super(String.format(MESSAGE_PATTERN, uuid), e);
        this.uuid = uuid;
    }
}
