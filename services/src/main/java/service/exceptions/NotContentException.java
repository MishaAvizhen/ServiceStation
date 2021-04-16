package service.exceptions;

public class NotContentException extends RuntimeException {

    public NotContentException(String message) {
        super("Error, not content:"+ message);
    }

}
