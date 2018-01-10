package book.library.java.exception;

public class DaoException extends Exception {
    private static final long serialVersionUID = 3412923984118418501L;

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Exception e) {
        super(e);
    }
}
