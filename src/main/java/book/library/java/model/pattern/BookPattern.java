package book.library.java.model.pattern;

/**
 * BookPattern is class with pattern for request from front-end.
 * BookPattern have fields: authorId(Integer), search(String), rating(Integer).
 */
public class BookPattern {
    private Integer authorId;
    private String search = "";
    private Integer rating;

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
