package demo.exception;

/**
 * Thrown when request has invalid data.
 */
public class InvalidRequestInputException extends RuntimeException {

    public InvalidRequestInputException(String message){
        super(message);
    }
}
