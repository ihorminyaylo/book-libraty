package book.library.java.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="authors")
public class Author implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    @Column(name = "first_name", nullable = false, length = 36)
    private String firstName;

    @Column(name = "second_name", nullable = false, length = 36)
    private String secondName;

    @Column(name = "create_date")
    private LocalDate createDate;

    @ManyToMany
    @JoinTable(name ="author_book_keys",
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
}
