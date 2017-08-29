package demo.exception;

import java.util.UUID;

/**
 * Thrown when application fails to update {@link demo.entity.Car} entity.
 */
public class CarUpdateFailedException extends RuntimeException {
    public final UUID uuid;

    private final static String MESSAGE_PATTERN = "Car with uuid %s can not be updated";

    public CarUpdateFailedException(UUID uuid) {
        super(String.format(MESSAGE_PATTERN, uuid));
        this.uuid = uuid;
    }

    public CarUpdateFailedException(Throwable e, UUID uuid) {
        super(String.format(MESSAGE_PATTERN, uuid), e);
        this.uuid = uuid;
    }
}
