package book.library.java.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Represent a book.
 * A book have id, name, yearPublished, published, createDate, averageRating.
 */
@Entity
@Table(name = "book")
public class Book implements Serializable { // todo: 'Book' does not define a 'serialVersionUID' field

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // todo: maybe GenerationType.SEQUENCE ?
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "year_published", nullable = false, length = 256) // todo: why nullable = false ?
    private int yearPublished;

    @Column(name = "publisher", nullable = false, length = 256) // todo: why nullable = false ?
    private String publisher;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false, insertable = false)
    private Date createDate;

    @Column(name = "average_rating", insertable = false) // todo: why without updatable = false
    private BigDecimal averageRating;

    @ManyToMany
    @JoinTable(name = "author_book",
        joinColumns = {@JoinColumn(name = "book_id", nullable = false)},
        inverseJoinColumns = {@JoinColumn(name = "author_id", nullable = false)})
    private List<Author> authors;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews;

    public Book() {
    }

    @PrePersist
    protected void onCreate() {
        createDate = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
