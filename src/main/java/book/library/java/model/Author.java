package book.library.java.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="author")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 256)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 256)
    private String secondName;

    // todo: I don't know if we need to @Temporal(TemporalType.TIMESTAMP), because TIMESTAMP in database auto generated
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "average_rating", nullable = false)
    private Float averageRating;

    @ManyToMany
    @JoinTable(name ="author_book",
            joinColumns = {@JoinColumn(name = "author_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "book_id", nullable = false)})
    private List<Book> books = new ArrayList<>();

    // todo: I don't know if we need to @PrePersist annotation with method onCreate()
    @PrePersist
    protected void onCreate() { createDate = new Date();}

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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
