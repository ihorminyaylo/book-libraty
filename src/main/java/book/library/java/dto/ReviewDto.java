package book.library.java.dto;

import book.library.java.model.Review;

public class ReviewDto {
    private Integer id;
    private String commenterName;
    private String comment;
    private Integer rating;

    public ReviewDto(Review review) {
        id = review.getId();
        commenterName = review.getCommenterName();
        comment = review.getComment();
        rating = review.getRating();
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
