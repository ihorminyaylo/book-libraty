package book.library.java.exception;

public class BusinessException extends Exception { // todo: 'BusinessException' does not define a 'serialVersionUID' field
	// todo: Please add constructor with argument (String message, Throwable cause) and use it in all places where you catch exceptions
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
