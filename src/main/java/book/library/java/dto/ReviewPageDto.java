package book.library.java.dto;

public class ReviewPageDto {
    private String rating;
    private String count;

    public ReviewPageDto() {
    }

    public ReviewPageDto(String rating, String count) {
        this.rating = rating;
        this.count = count;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
