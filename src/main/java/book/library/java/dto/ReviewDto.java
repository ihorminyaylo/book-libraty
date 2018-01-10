package book.library.java.dto;

import book.library.java.model.Review;

public class ReviewDto extends AbstractDto {
    private String commenterName;
    private String createDate;
    private String comment;
    private Integer rating;

    public ReviewDto(Review review) {
        super(review);
        commenterName = review.getCommenterName();
        comment = review.getComment();
        rating = review.getRating();
        createDate = review.getCreateDate().toString();
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
