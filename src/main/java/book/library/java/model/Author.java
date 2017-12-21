package book.library.java.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Represent a author.
 * A author have id, firstName, secondName, createDate, averageRating
 */
@Entity
@Table(name = "author")
public class Author extends AbstractEntity {
    private static final long serialVersionUID = 7639326813425012421L;

    @Column(name = "first_name", nullable = false, length = 256)
    private String firstName;

    @Column(name = "second_name", length = 256)
    private String secondName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false, insertable = false)
    private Date createDate;

    @Column(name = "average_rating", updatable = false, insertable = false, nullable = false)
    private BigDecimal averageRating;


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


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getAverageRating() {
        if (averageRating == null) {
            return BigDecimal.valueOf(0);
        }
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }
}
