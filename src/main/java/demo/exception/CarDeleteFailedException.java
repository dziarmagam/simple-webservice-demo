package demo.exception;

import java.util.UUID;

/**
 * Thrown when application fails to delete {@link demo.entity.Car} entity.
 */
public class CarDeleteFailedException extends RuntimeException{
    public final UUID uuid;
    private final static String MESSAGE_PATTERN = "Car with uuid %s can not be deleted";

    public CarDeleteFailedException(UUID uuid) {
        super(String.format(MESSAGE_PATTERN, uuid));
        this.uuid = uuid;
    }
}
