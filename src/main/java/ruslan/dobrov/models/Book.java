package ruslan.dobrov.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty(message = "Book title must not be empty")
    @NotNull
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters long")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Author must not be empty")
    @NotNull
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters long")
    private String author;

    @Column(name = "year_published")
    @Min(value = 0, message = "Year of publication must be greater than 0")
    private Integer yearPublished;

    @ManyToMany(mappedBy = "books")
    private List<Person> owners;

    @Column(name = "total_quantity")
    @Min(value = 0, message = "Quantity must be greater than 0")
    private Integer totalQuantity;

    public Book() {}

    public Book(String title, String author, int year, int quantity) {
        this.title = title;
        this.author = author;
        this.yearPublished = year;
        this.totalQuantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int year) {
        this.yearPublished = year;
    }

    public List<Person> getOwners() {
        return owners;
    }

    public void setOwners(List<Person> owners) {
        this.owners = owners;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int quantity) {
        this.totalQuantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (id != book.id) return false;
        if (yearPublished != book.yearPublished) return false;
        if (totalQuantity != book.totalQuantity) return false;
        if (!Objects.equals(title, book.title)) return false;
        if (!Objects.equals(author, book.author)) return false;
        return Objects.equals(owners, book.owners);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + yearPublished;
        result = 31 * result + (owners != null ? owners.hashCode() : 0);
        result = 31 * result + totalQuantity;
        return result;
    }
}