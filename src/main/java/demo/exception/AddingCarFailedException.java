package demo.exception;

/**
 * Thrown when application fails to add new {@link demo.entity.Car}.
 */
public class AddingCarFailedException extends RuntimeException {

    private static final String MESSAGE = "Failed to add vehicle";

    public AddingCarFailedException(Throwable e) {
        super(MESSAGE, e);
    }

    public AddingCarFailedException() {
        super(MESSAGE);
    }
}
