package book.library.java.dto;

import book.library.java.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookDto {
    private Integer id;
    private String name;
    private String publisher;
    private Integer yearPublished;
    private List<AuthorDto> authors;

    public BookDto(Book book) {
        id = book.getId();
        name = book.getName();
        publisher = book.getPublisher();
        yearPublished = book.getYearPublished();
        authors = book.getAuthors().stream().map(author -> new AuthorDto(author)).collect(Collectors.toList());
        averageRating = book.getAverageRating().toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(Integer yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    private String averageRating;

    public List<AuthorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDto> authors) {
        this.authors = authors;
    }
}
