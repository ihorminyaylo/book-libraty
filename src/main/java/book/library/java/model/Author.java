package book.library.java.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@EntityListeners(Review.class)
@Table(name="author")
public class Author implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "first_name", nullable = false, length = 256)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 256)
    private String secondName;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "average_rating", length = 256)
    private Double averageRating;

    @ManyToMany
    @JoinTable(name ="author_book",
            joinColumns = {@JoinColumn(name = "author_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "book_id", nullable = false)})
    private List<Book> books;


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
