package book.library.java.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represent a book.
 * A book have id, name, yearPublished, published, createDate, averageRating.
 */
@Entity
@Table(name = "book")
public class Book extends AbstractEntity {
    private static final long serialVersionUID = 645377494231112540L;

    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Column(name = "year_published", length = 4)
    private Integer yearPublished;

    @Column(name = "publisher", length = 256)
    private String publisher;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false, insertable = false)
    private Date createDate;

    @Column(name = "average_rating", insertable = false, updatable = false)
    private BigDecimal averageRating;

    @ManyToMany
    @JoinTable(name = "author_book",
        joinColumns = {@JoinColumn(name = "book_id", nullable = false)},
        inverseJoinColumns = {@JoinColumn(name = "author_id", nullable = false)})
    private List<Author> authors = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(Integer yearPublished) {
        this.yearPublished = yearPublished;
    }


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
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

    @Override
    public String toString() {
        return "id - " + getId() + ", name - " + name;
    }
}
