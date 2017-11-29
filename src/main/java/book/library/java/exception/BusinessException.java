package book.library.java.exception;

public class BusinessException extends Exception {
    public BusinessException() {}
    public BusinessException(String message) {
        super(message);
    }
}
