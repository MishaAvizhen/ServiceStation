package exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class, NullPointerException.class})
    public ResponseEntity<ExceptionResponse> resourceNotFound(RuntimeException ex) {
        ExceptionResponse response = buildExceptionResponse(ex, "NOT_FOUND");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ResourceAlreadyExists.class, IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> resourceAlreadyExists(Exception ex) {
        ExceptionResponse response = buildExceptionResponse(ex, "CONFLICT");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotContentException.class)
    public ResponseEntity<ExceptionResponse> customException(NotContentException ex) {
        ExceptionResponse response = buildExceptionResponse(ex, "NO_CONTENT");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ExceptionResponse buildExceptionResponse(Exception ex, String errorCode) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(errorCode);
        response.setErrorMessage(ex.getMessage());
        response.setTimestamp(new Date());
        return response;
    }
}
