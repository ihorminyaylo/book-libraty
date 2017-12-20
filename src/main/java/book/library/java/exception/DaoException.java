package book.library.java.exception;

public class DaoException extends Exception { // todo: 'DaoException' does not define a 'serialVersionUID' field
	// todo: Please add constructor with argument (String message, Throwable cause) and use it in all places where you catch exceptions
    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }
}
