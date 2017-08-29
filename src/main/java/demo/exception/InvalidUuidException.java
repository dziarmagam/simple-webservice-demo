package demo.exception;

/**
 * Thrown when {@link java.util.UUID} is invalid.
 */
public class InvalidUuidException extends InvalidRequestInputException {

    private static final String MESSAGE_PATTERN = "UUID: %s is invalid";
    public final String uuid;

    public InvalidUuidException(String uuid) {
        super(String.format(MESSAGE_PATTERN, uuid));
        this.uuid = uuid;
    }
}
