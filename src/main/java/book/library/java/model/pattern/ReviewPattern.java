package book.library.java.model.pattern;

/**
 * ReviewPattern is class with pattern for request from front-end.
 * ReviewPattern have field bookId(Integer).
 */
public class ReviewPattern {
    private Integer bookId;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
