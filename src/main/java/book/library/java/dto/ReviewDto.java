package book.library.java.dto;

public class ReviewDto {
    private Integer id;
    private String commenterName;
    private String comment;
    private Integer rating;

    public ReviewDto(Integer id, String commenterName, String comment, Integer rating) {
        this.id = id;
        this.commenterName = commenterName;
        this.comment = comment;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}
