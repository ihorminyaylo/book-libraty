package book.library.java.controller;

import book.library.java.exception.ApiError;
import book.library.java.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex) {
        logger.info(ex.getClass().getName());
        ApiError apiError = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Object> handleBusinessException(Exception ex) {
        logger.info(ex.getClass().getName());
        ApiError apiError = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "business exception");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
