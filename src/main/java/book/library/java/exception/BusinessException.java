package book.library.java.exception;

public class BusinessException extends Exception {
    private static final long serialVersionUID = 283989440599278153L;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Exception e) {
        super(e);
    }
}
