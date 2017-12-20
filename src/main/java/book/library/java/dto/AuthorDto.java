package book.library.java.dto;

/**
 * Represent a AuthorDto. This class for convenient transfer of information between front-end and back-end.
 * AuthorDto have id, firstName, secondName, createDate, averageRating
 */
public class AuthorDto {
    private Integer id;
    private String firstName;
    private String secondName;
    private String averageRating;


    public AuthorDto(Integer id, String firstName, String secondName, String averageRating) { // todo: maybe better use one argument (Author author) ?
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.averageRating = averageRating;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
