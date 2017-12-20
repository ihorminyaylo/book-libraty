package book.library.java.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ApiError {
    private HttpStatus status; // todo: Field may be 'final'
    private String message; // todo: Field may be 'final'
    private List<String> errors; // todo: Field may be 'final'

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super(); // todo: for what?
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String message, String error) {
    	// todo: maybe invoke this(....) ?
        super(); // todo: for what?
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);  // todo: use Collections.singletonList()
    }

    public HttpStatus getStatus() {
        return status;
    }
    // todo: another getters?
}
