package demo.exception;

/**
 * Thrown when field is required by was not in the request.
 */
public class FieldIsRequiredException extends InvalidRequestInputException {

    private static final String MESSAGE_PATTERN = "Field %s is required";
    public final String filedName;

    public FieldIsRequiredException(String filedName) {
        super(String.format(MESSAGE_PATTERN, filedName));
        this.filedName = filedName;
    }
}
