package book.library.java.dto;

import book.library.java.model.Author;

import java.math.BigDecimal;

/**
 * Represent a AuthorDto. This class for convenient transfer of information between front-end and back-end.
 * AuthorDto have id, firstName, secondName, createDate, averageRating
 */
public class AuthorDto extends AbstractDto {
    private String firstName;
    private String secondName;
    private String averageRating;

    public AuthorDto(Author author) {
        super(author);
        firstName = author.getFirstName();
        secondName = author.getSecondName();
        if (author.getAverageRating() != null) {
            averageRating = author.getAverageRating().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
        } else {
            averageRating = "0";
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

}
